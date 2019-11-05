package com.nikhijadhav.informationapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikhijadhav.informationapp.databinding.InformationRowBinding
import com.nikhijadhav.informationapp.models.Row

class InformationRecyclerViewAdapter:RecyclerView.Adapter<InformationRecyclerViewAdapter.InformationViewHolder>() {

    var informationList: List<Row> = ArrayList<Row>()

     fun updateInformationList(newInformationList: List<Row>){
         informationList = newInformationList
         notifyDataSetChanged()
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InformationRowBinding.inflate(inflater)
        //val binding = InformationRowBinding.bind(inflater.inflate(R.layout.information_row,parent,false))
        return InformationViewHolder(binding)
        //return InformationViewHolder(inflater.inflate(R.layout.information_row,parent,false))
    }

    override fun getItemCount(): Int {
        return informationList.size
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        //holder.tvInformation.text = informationList.get(position).title
        val informationRowItem = informationList[position]
        if(informationRowItem.imageHref.isNullOrEmpty()){
            informationRowItem.imageHref = ""
        }

        if(informationRowItem.title.isNullOrEmpty()){
            informationRowItem.title = ""
        }

        if(informationRowItem.description.isNullOrEmpty()){
            informationRowItem.description = ""
        }

        holder.bind(informationRowItem)
    }


   inner class InformationViewHolder(val binding: InformationRowBinding) : RecyclerView.ViewHolder(binding.root) {
     //var tvInformation:TextView = itemView.findViewById(R.id.tvInformation)

      fun bind(bindingItem: Row){
        binding.information = bindingItem
          binding.executePendingBindings()
      }
   }

     /*inner class InformationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     var tvInformation:TextView = itemView.findViewById(R.id.tvInformation)
   }*/
}