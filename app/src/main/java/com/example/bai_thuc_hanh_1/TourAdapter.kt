package com.example.bai_thuc_hanh_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TourAdapter(
    private var tourList: MutableList<Tour>,
    private val onEdit: (Tour) -> Unit,
    private val onDelete: (Tour) -> Unit
) : RecyclerView.Adapter<TourAdapter.TourViewHolder>() {

    inner class TourViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgTransport: ImageView = view.findViewById(R.id.imgTransport)
        val tvDestination: TextView = view.findViewById(R.id.tvDestination)
        val tvDuration: TextView = view.findViewById(R.id.tvDuration)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tour, parent, false)
        return TourViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        val tour = tourList[position]


        holder.tvDestination.text = tour.destination
        holder.tvDuration.text = tour.duration
        holder.imgTransport.setImageResource(tour.transportImages[tour.selectedTransportIndex])
        holder.btnEdit.setOnClickListener { onEdit(tour) }


        holder.btnDelete.setOnClickListener { onDelete(tour) }
    }

    override fun getItemCount(): Int = tourList.size


    fun updateList(newList: MutableList<Tour>) {
        tourList = newList
        notifyDataSetChanged()
    }
}
