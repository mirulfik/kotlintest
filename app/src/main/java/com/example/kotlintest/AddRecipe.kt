package com.example.kotlintest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import kotlinx.android.synthetic.main.activity_add_recipe.*

class AddRecipe : AppCompatActivity() {

    private var mImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 100
    lateinit var button_choose_image: Button
    lateinit var imageView:ImageView
    private var mStorageRef:StorageReference? = null
    private var mDatabaseRef:DatabaseReference? = null
    private var mUploadTask:StorageTask<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        mStorageRef = FirebaseStorage.getInstance().getReference("Recipe_Uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Recipe_Uploads")

        imageView = findViewById(R.id.recipeAddImageView)
        button_choose_image = findViewById(R.id.button_choose_image)
        button_choose_image.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE_REQUEST)
        }
        /** Checking whether recipe image has been selected **/
        addBtn.setOnClickListener{
            if(mUploadTask != null && mUploadTask!!.isInProgress){
                Toast.makeText(this@AddRecipe,"An upload is still in progress",Toast.LENGTH_SHORT).show()
            }
            else {
                uploadFile()
            }
        }
    }
    /** Uploading recipe to database **/
    private fun uploadFile() {
        if (mImageUri != null ){
            val fileReference = mStorageRef!!.child(System.currentTimeMillis().toString() + "." + getFileExtension(mImageUri!!))
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
            mUploadTask = fileReference.putFile(mImageUri!!).addOnSuccessListener {
                    taskSnapshot ->
                val handler = Handler()
                handler.postDelayed({
                    progressBar.visibility = View.VISIBLE
                    progressBar.isIndeterminate = false
                    progressBar.progress = 0
                }, 500)
                Toast.makeText(this@AddRecipe, "Recipe upload successfully",Toast.LENGTH_LONG).show()
                val upload = Recipe(
                    name = recipeEditText!!.text.toString().trim{ it <= ' '},
                    ingredient = ingredientsEditText!!.text.toString().trim { it <= ' ' },
                    preparation = preparationsEditText!!.text.toString().trim { it <= ' ' },
                    imageUrl = mImageUri.toString()
                )
                val uploadId = mDatabaseRef!!.push().key
                mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                progressBar.visibility = View.INVISIBLE
                openImageActivity()
            }
                .addOnFailureListener{ e ->
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@AddRecipe, e.message, Toast.LENGTH_SHORT).show()
                    Log.e("data","${e.message}")
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressBar.progress = progress.toInt()
                }
        }
        else{
            Toast.makeText(this, "No iamge selected",Toast.LENGTH_SHORT).show()
        }
    }

    /** Return to main menu when finish upload **/
    private fun openImageActivity() {
        startActivity(Intent(this@AddRecipe, MainMenu::class.java))
    }

    //this might be change from mImageUri to uri
    private fun getFileExtension(mImageUri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(mImageUri))
//this might be change from mImageUri to uri

    }

    /** Replace imageview after select image from gallery **/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            mImageUri = data?.data
            imageView.setImageURI(mImageUri)
        }
    }
}