package com.example.kotlintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)
        /** Button to add recipe **/
        var btnAddRecipe: Button = findViewById(R.id.btnAddRecipe)
        btnAddRecipe.setOnClickListener{
            startActivity(Intent(this,AddRecipe::class.java))
        }
        /** Button to view recipe list **/
        var btnViewRecipe: Button = findViewById(R.id.btnViewRecipe)
        btnViewRecipe.setOnClickListener{
            startActivity(Intent(this, RecipeList::class.java))
        }

    }

}