package com.example.aulaandroid.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaandroid.R

class Produto(private val imageList2: List<Int>) : RecyclerView.Adapter<Produto.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView2: ImageView = itemView.findViewById(R.id.imgProduto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.produto, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView2.setImageResource(imageList2[position])
    }

    override fun getItemCount(): Int {
        return imageList2.size
    }
}