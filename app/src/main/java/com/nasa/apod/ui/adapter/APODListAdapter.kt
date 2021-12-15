package com.nasa.apod.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nasa.apod.data.entities.APOD
import com.nasa.apod.databinding.ItemApodBinding
import com.nasa.apod.utils.ImageUtils

class APODListAdapter : RecyclerView.Adapter<APODListAdapter.APODViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<APOD>() {
        override fun areItemsTheSame(oldItem: APOD, newItem: APOD): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: APOD, newItem: APOD): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): APODViewHolder {
        val binding = ItemApodBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return APODViewHolder(binding)
    }

    override fun onBindViewHolder(holder: APODViewHolder, position: Int) {
        val user = differ.currentList[position]
        if (user != null) {
            holder.bind(user)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class APODViewHolder(
        private val binding: ItemApodBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(apod: APOD) {
            binding.date.text = apod.date
            binding.title.text = apod.title

            var url = apod.url

            when(apod.media_type) {
                ImageUtils.VIDEO_MEDIA_TYPE -> {
                    url = ImageUtils.getYoutubeThumbnail(url)
                }
            }
            Glide.with(binding.ivApodPic.context)
                .load(url)
                .circleCrop()
                .into(binding.ivApodPic)
            binding.root.setOnClickListener {
                /*onItemClickListener?.let {
                    it(data)
                }*/
            }
        }
    }

    /*private var onItemClickListener: ((APOD) -> Unit)? = null

    fun setOnItemClickListener(listener: (APOD) -> Unit) {
        onItemClickListener = listener
    }*/
}