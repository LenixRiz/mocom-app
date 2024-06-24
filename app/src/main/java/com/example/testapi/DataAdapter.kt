package com.example.testapi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.databinding.ItemDataBinding
import com.squareup.picasso.Picasso

class DataAdapter(val data: List<DataItem>?, private val click:onClickItem) :
    RecyclerView.Adapter<DataAdapter.MyHolder>() {
        private lateinit var binding: ItemDataBinding

        inner class MyHolder(binding: ItemDataBinding, private val data: List<DataItem>?) :
            RecyclerView.ViewHolder(binding.root) {

                init {
                    binding.btnUpdate.setOnClickListener {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            val item = data?.get(position)
                            val intent = Intent(itemView.context, UpdateAddActivity::class.java)
                            intent.putExtra("dataItem", item)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                fun onBind(get: DataItem?) {
                    binding.tvTitle.text = get?.comicTitle
                    binding.tvAuthor.text = get?.comicAuthor
//                    binding.tvImage.text = get?.comicImage
                    binding.tvDescription.text = get?.comicDescription

                    Picasso.get()
                        .load(get?.comicImage)
                        .placeholder(R.drawable.placeholder_image) // Optional placeholder
                        .error(R.drawable.error_image) // Optional error image
                        .into(binding.ivComicImage) // Load image into ImageView

                    binding.btnDelete.setOnClickListener {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            // Show confirmation dialog
                            AlertDialog.Builder(itemView.context)
                                .setTitle("Confirm Delete")
                                .setMessage("Are you sure you want to delete this item?")
                                .setPositiveButton("Delete") { dialog, _ ->
                                    click.delete(data?.get(position))
                                    dialog.dismiss()
                                }
                                .setNegativeButton("Cancel"){ dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }

                }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return MyHolder(binding, data)
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.onBind(data?.get(position))
        binding.tvTitle.setOnClickListener() {
            click.clicked(data?.get(position))
        }
    }

    interface onClickItem {
        fun clicked (item: DataItem?)
        fun delete(item: DataItem?)
    }

}