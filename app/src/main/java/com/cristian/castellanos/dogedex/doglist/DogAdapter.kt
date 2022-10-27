package com.cristian.castellanos.dogedex.doglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.databinding.DogListItemBinding
import com.cristian.castellanos.dogedex.model.Dog

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        holder.bind(dog)
    }

    inner class DogViewHolder(private val binding: DogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog) {
            with(binding) {
                if (dog.inCollection) {
                    dogListItemLayout.background = ContextCompat.getDrawable(
                        dogImage.context, R.drawable.dog_list_item_background
                    )
                    dogImage.visibility = View.VISIBLE
                    dogIndex.visibility = View.GONE
                    dogListItemLayout.setOnClickListener { onItemClickListener?.invoke(dog) }
                    dogImage.load(dog.imageUrl)
                } else {
                    dogImage.visibility = View.GONE
                    dogIndex.visibility = View.VISIBLE
                    dogIndex.text = dog.index.toString()
                    dogListItemLayout.background = ContextCompat.getDrawable(
                        dogImage.context, R.drawable.dog_list_item_null_background
                    )
                    dogListItemLayout.setOnLongClickListener {
                        onLongItemClickListener?.invoke(dog)
                        true
                    }
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private var onItemClickListener: ((Dog) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: (Dog) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    private var onLongItemClickListener: ((Dog) -> Unit)? = null
    fun setLongOnItemClickListener(onLongItemClickListener: (Dog) -> Unit) {
        this.onLongItemClickListener = onLongItemClickListener
    }
}