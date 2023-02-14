package com.example.android.contact.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.android.contact.databinding.ListViewBinding
import com.example.android.contact.db.Contact

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    class MyViewHolder(private var binding: ListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Contact) {
            binding.txtTitle.text = currentItem.name.toString()
            binding.image.load(currentItem.image)
            binding.share.setOnClickListener {
                val sendIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(
                        Intent.EXTRA_TEXT,
                        "Name : ${currentItem.name.toString()} \nPhone No : ${currentItem.phoneNum1.toString()}"
                    )
                    this.type = "text/plain"
                }
                binding.root.context.startActivity(sendIntent)
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.contactId == newItem.contactId
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(currentItem) }
        }
    }

    private var onItemClickListener: ((Contact) -> Unit)? = null

    fun setOnItemClickListener(listener: (Contact) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}