package com.example.kotlintest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintest.Recipe


class ListAdapter (var mContext: Context, var recipeList: List<Recipe>):
    RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
    inner class ListViewHolder(var v: View): RecyclerView.ViewHolder(v){
        var imgR = v.findViewById<ImageView>(R.id.recipeItemImageView)
        var nameR = v.findViewById<TextView>(R.id.recipeItemTextView)

    }
    /** Display recipe name in CardView **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.item_recipe,parent,false)
        return ListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var newList = recipeList[position]
        holder.nameR.text = newList.name
        holder.imgR.loadImage(newList.imageUrl)
        holder.v.setOnClickListener{
            mContext.startActivity(Intent(mContext, RecipeDetails::class.java))
        }

    }

    override fun getItemCount(): Int = recipeList.size
}