package com.example.firebasetest2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductAdapter(private val ds:ArrayList<ProductModel>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    // click listener
    private  lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItenClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                clickListener.onItenClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            product_name.text = ds[position].productName
            product_price.text = ds[position].productPrice
            product_type.text = ds[position].productType
            Glide.with(context).load(ds[position].productImage).placeholder(R.drawable.ic_android_black).into(tour_image_view)
        }


    }

    override fun getItemCount(): Int {
        return ds.size
    }

}