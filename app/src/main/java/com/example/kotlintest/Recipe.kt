package com.example.kotlintest

import com.google.firebase.database.Exclude

data class Recipe(
    var name:String? = null,
    var imageUrl:String? = null,
    @get:Exclude
    @set:Exclude
    var key:String? = null,
    var ingredient:String? = null,
    var preparation:String? = null,
)
