package com.example.kakaosearch.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaosearch.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main_image.view.*

class MainSearchRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    data class ImageItem(var imageUrl:String, var documentUrl:String)
    private val imageItemList = ArrayList<ImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ImageHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ImageHolder)?.bind(imageItemList[position])
    }

    override fun getItemCount(): Int = imageItemList.size

    fun addImageItem(imageUrl: String, documentUrl: String) {
        imageItemList.add(ImageItem(imageUrl, documentUrl))
    }

    class ImageHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_main_image, parent, false)
    ) {
        fun bind(item: ImageItem) {
            itemView.run {
                Picasso.with(context).load(item.imageUrl).placeholder(R.drawable.ic_search_basic).into(item_main_iv)
                item_main_iv.setOnClickListener {
                    ContextCompat.startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(item.documentUrl)), null)
                }
            }
        }
    }

}