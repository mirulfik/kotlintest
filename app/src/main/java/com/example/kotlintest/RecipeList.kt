package com.example.kotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_recipe_list.*

class RecipeList : AppCompatActivity() {

    private var mStorage:FirebaseStorage? = null
    private var mDatabaseRef:DatabaseReference? = null
    private var mDBListener:ValueEventListener? = null
    private lateinit var mRecipes:MutableList<Recipe>
    private lateinit var listAdapter:ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this@RecipeList)
        mRecipes = ArrayList()
        listAdapter = ListAdapter(this@RecipeList,mRecipes)
        mRecyclerView.adapter = listAdapter

        /** Retrieve recipe name and image from database **/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Recipe_Uploads")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RecipeList, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mRecipes.clear()
                for (recipeSnapshot in snapshot.children){
                    val upload = recipeSnapshot.getValue(Recipe::class.java)
                    upload!!.key = recipeSnapshot.key
                    mRecipes.add(upload)
                }

                listAdapter.notifyDataSetChanged()
                myDataLoaderProgressBar.visibility = View.GONE
            }

        })
    }
    override fun onDestroy() {
        super.onDestroy()
        mDatabaseRef!!.removeEventListener(mDBListener!!)
    }
}