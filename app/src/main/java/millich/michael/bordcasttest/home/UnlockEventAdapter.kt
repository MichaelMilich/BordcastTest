package millich.michael.bordcasttest.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.TextItemViewHolder
import millich.michael.bordcasttest.background.formatDateFromMillisecondsLong
import millich.michael.bordcasttest.databinding.ListItemUnlockEventBinding
import millich.michael.bordcasttest.databse.UnlockEvent

class UnlockEventAdapter : ListAdapter<UnlockEvent,UnlockEventAdapter.ViewHolder>(UnlockEventDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }



    class ViewHolder(val binding: ListItemUnlockEventBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (item: UnlockEvent){
            binding.unlockEvent =item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemUnlockEventBinding.inflate(layoutInflater,parent,false)
                return  ViewHolder(binding)
            }
        }
    }

}

class UnlockEventDiffCallback:DiffUtil.ItemCallback<UnlockEvent>(){
    override fun areItemsTheSame(oldItem: UnlockEvent, newItem: UnlockEvent): Boolean {
        return oldItem.eventId == newItem.eventId
    }

    override fun areContentsTheSame(oldItem: UnlockEvent, newItem: UnlockEvent): Boolean {
        return oldItem == newItem
    }

}