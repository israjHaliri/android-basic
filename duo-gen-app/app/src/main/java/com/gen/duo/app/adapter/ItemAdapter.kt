package com.gen.duo.app.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.gen.duo.app.R
import com.gen.duo.app.model.Item
import com.gen.duo.app.util.Common

/**
 * Created by israjhaliri on 3/14/18.
 */

class ItemAdapter(val items: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.id = items[position].id!!

        val imageItemDecoded = Base64.decode(items[position].image, Base64.DEFAULT)
        val imageItemByte = BitmapFactory.decodeByteArray(imageItemDecoded, 0, imageItemDecoded.size)

        holder?.image?.setImageBitmap(imageItemByte)
        holder?.image?.getLayoutParams()?.height = 250;
        holder?.image?.getLayoutParams()?.width = 250;

        holder?.title?.text = items[position].title
        holder?.description?.text = items[position].description

        if (items[position].viewed!! > 5) {
            holder?.rating?.setRating(5F);
        } else {
            holder?.rating?.setRating(3F);
        }

        holder?.price?.text = items[position].price.toString()

        holder?.itemView?.setOnClickListener {
            Common.showMessage(holder.itemView.context, "Ops sorry this feature under development")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.fragment_item_recycler, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var id: Long
        var image: ImageView
        var title: TextView
        var description: TextView
        var rating: RatingBar
        var price: TextView

        init {
            id = 0
            image = itemView.findViewById(R.id.image_item)
            title = itemView.findViewById(R.id.text_view_title)
            description = itemView.findViewById(R.id.text_view_description)
            rating = itemView.findViewById(R.id.rating_bar_rating)
            price = itemView.findViewById(R.id.text_view_price)
        }
    }
}
