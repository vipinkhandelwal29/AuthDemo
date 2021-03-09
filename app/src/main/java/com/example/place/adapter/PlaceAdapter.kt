package com.example.place.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.place.bean.PlaceBean
import com.example.place.databinding.RecyclerviewItemBinding

class PlaceAdapter(private val  dataList: ArrayList<PlaceBean?>):
RecyclerView.Adapter<MyHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: MyHolder, position: Int) {

    }

}
class MyHolder(val recyclerviewItemBinding: RecyclerviewItemBinding ):
    RecyclerView.ViewHolder(recyclerviewItemBinding.root)