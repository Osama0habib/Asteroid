package com.udacity.asteroidradar.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AsteroidsAdapter(val clickListener: AsteroidListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(AsteroidDiffCallback()) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val asteroidItem = getItem(position) as DataItem.AsteroidItem
                holder.binding( clickListener,asteroidItem.asteroid)
            }
        }
    }



    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun addItemsAndSubmit(list: List<Asteroid>?) {
        adapterScope.launch {
            val items = list?.map {DataItem.AsteroidItem(it)

            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  ViewHolder.from(parent)
        }
    }

    class ViewHolder private constructor(val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun binding(
            clickListener: AsteroidListener,
            item: Asteroid,
        ) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding : AsteroidItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.asteroid_item,parent,false)
                return ViewHolder(binding)
            }
        }
    }



class AsteroidDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}

sealed class DataItem {
    data class AsteroidItem(val asteroid: Asteroid): DataItem() {
        override val id = asteroid.id
    }


    abstract val id: Long
}




