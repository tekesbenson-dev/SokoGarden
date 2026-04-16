package com.example.sokogarden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray

class ProductAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.product_name)
        val txtDesc: TextView = itemView.findViewById(R.id.product_description)
        val txtPrice: TextView = itemView.findViewById(R.id.product_cost)
        val imgProduct: ImageView = itemView.findViewById(R.id.product_photo)
    }

    //Access the Layout - Single Item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)
        return ProductViewHolder(view)
    }

    //Access Views in Single Item XML and Bind Data
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.txtName.text = product.product_name
        holder.txtDesc.text = product.product_description ?: "No description"
        holder.txtPrice.text = "Ksh ${product.product_cost}"
        
        // Updated Base URL for images to match your new server
        val imageUrl = "https://bensontekes.alwaysdata.net/api/static/images/${product.product_photo}"

        //Load image using Glide
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground) // Show something if load fails
            .into(holder.imgProduct)
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    //Return all products Details as a LIST
    companion object {
        fun fromJsonArray(jsonArray: JSONArray): List<Product> {
            val list = mutableListOf<Product>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    Product(
                        product_id = obj.getInt("product_id"),
                        product_name = obj.getString("product_name"),
                        product_description = obj.optString("product_description", ""),
                        product_cost = obj.getInt("product_cost"),
                        product_photo = obj.optString("product_photo", "")
                    )
                )
            }
            return list
        }
    }
}
