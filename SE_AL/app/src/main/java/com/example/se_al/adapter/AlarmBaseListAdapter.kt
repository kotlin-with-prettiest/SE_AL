package com.example.se_al.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.se_al.R
import com.example.se_al.SampleData


class AlarmBaseListAdapter(
    private val context: Context,
    private val dataList: ArrayList<SampleData>,
) :
    RecyclerView.Adapter<AlarmBaseListAdapter.ItemViewHolder>() {

        inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            private val itemName = itemView.findViewById<TextView>(R.id.alarm_item_name)
            private val itemDate = itemView.findViewById<TextView>(R.id.alarm_item_date)
            private val itemTitle = itemView.findViewById<TextView>(R.id.alarm_item_title)
            private val itemMemo = itemView.findViewById<TextView>(R.id.alarm_item_content)

            fun bind(sampleData: SampleData, context: Context){
                itemName.text = sampleData.name
                itemDate.text = sampleData.date
                itemTitle.text = sampleData.title
                itemMemo.text = sampleData.memo
            }



        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_alarm_base_item,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}


//class AlarmBaseListAdapter :
//    ListAdapter<SampleData, AlarmBaseListAdapter.AlarmBaseListViewHolder>(DiffCallback) {
//
//    class AlarmBaseListViewHolder(private var binding: FragmentAlarmBaseItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(sampleData: SampleData) {
//            Log.d("adapter","1111")
//            binding.sampleData = sampleData
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int,
//    ): AlarmBaseListAdapter.AlarmBaseListViewHolder {
//        return AlarmBaseListViewHolder(
//            FragmentAlarmBaseItemBinding.inflate(
//                LayoutInflater.from(parent.context)
//            )
//        )
//    }
//
//    override fun onBindViewHolder(
//        holder: AlarmBaseListAdapter.AlarmBaseListViewHolder,
//        position: Int,
//    ) {
//        val sampleData = getItem(position)
//        holder.bind(sampleData)
//    }
//
//
//    companion object DiffCallback : DiffUtil.ItemCallback<SampleData>() {
//        override fun areItemsTheSame(oldItem: SampleData, newItem: SampleData): Boolean {
//            return oldItem.title == newItem.title
//        }
//
//        override fun areContentsTheSame(oldItem: SampleData, newItem: SampleData): Boolean {
//            return oldItem.memo == newItem.memo
//        }
//
//    }
//
//}