package com.example.kotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_recipe_details.*

class RecipeDetails : AppCompatActivity() {

    private var mDatabaseRef:DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Recipe_Uploads")

        fun OnDataChange(snapshot: DataSnapshot){

        }
        val intent = intent
        var nameR = intent.getStringExtra("name")
        var imgR = intent.getStringExtra("imageUrl")
        var ingredientR = intent.getStringExtra("ingredient")
        var preparationR = intent.getStringExtra("preparation")

        /** Details of the recipe**/
        recipeNameTextView.text = nameR
        ingredientsTextView.text = ingredientR
        preparationsTextView.text = preparationR
        recipeImageView.loadImage(imgR)

    }
}