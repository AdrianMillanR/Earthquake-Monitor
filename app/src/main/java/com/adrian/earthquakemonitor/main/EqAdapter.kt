package com.adrian.earthquakemonitor.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adrian.earthquakemonitor.Earthquake
import com.adrian.earthquakemonitor.R
import com.adrian.earthquakemonitor.databinding.EqListItemBinding

class  EqAdapter(private val context:Context) : ListAdapter<Earthquake, EqAdapter.EqViewHolder>(
    DiffCallback
)  {

    companion object DiffCallback: DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqViewHolder {
        val binding= EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqViewHolder, position: Int) {
        val earthquake= getItem(position)
        holder.bind(earthquake)
    }

    inner class EqViewHolder(private val binding: EqListItemBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(earthquake: Earthquake){
            binding.magnitudeText.text=context.getString(R.string.magnitude_format, earthquake.magnitude)
            binding.placeText.text=earthquake.place
            binding.root.setOnClickListener{
                onItemClickListener(earthquake)
            }

        }

    }



}