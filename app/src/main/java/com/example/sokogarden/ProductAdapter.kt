package com.example.sokogarden

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        val btnPurchase: Button = itemView.findViewById(R.id.btnPurchase)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.txtName.text = product.product_name
        holder.txtDesc.text = product.product_description ?: "No description"
        holder.txtPrice.text = "Ksh ${product.product_cost}"
        
        val imageUrl = "https://kbenkamotho.alwaysdata.net/api/static/images/${product.product_photo}"

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imgProduct)

        // Navigate to PaymentActivity
        holder.btnPurchase.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra("product_name", product.product_name)
                putExtra("product_cost", product.product_cost)
                putExtra("product_photo", product.product_photo) // Passing the photo filename
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

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
