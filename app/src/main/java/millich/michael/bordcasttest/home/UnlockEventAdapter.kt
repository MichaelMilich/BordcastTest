package millich.michael.bordcasttest.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import millich.michael.bordcasttest.R
import millich.michael.bordcasttest.background.TextItemViewHolder
import millich.michael.bordcasttest.background.formatDateFromMillisecondsLong
import millich.michael.bordcasttest.databse.UnlockEvent

class UnlockEventAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<UnlockEvent>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_unlock_event_view,parent,false) as TextView
        return  TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        "Unlock ${item.eventId} at ${formatDateFromMillisecondsLong(item.eventTime)}".also { holder.textView.text = it }
    }

    override fun getItemCount(): Int {
         return data.size
    }


}