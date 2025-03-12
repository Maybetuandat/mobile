package com.example.bai_thuc_hanh_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TourAdapter(private val tourList: List<Tour>) : RecyclerView.Adapter<TourAdapter.TourViewHolder>() {

    inner class TourViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgTransport: ImageView = view.findViewById(R.id.imgTransport)
        val tvDestination: TextView = view.findViewById(R.id.tvDestination)
        val tvDuration: TextView = view.findViewById(R.id.tvDuration)
        val spinnerTransport: Spinner = view.findViewById(R.id.spinnerTransport)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tour, parent, false)
        return TourViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        val tour = tourList[position]

        // Gán dữ liệu
        holder.tvDestination.text = tour.destination
        holder.tvDuration.text = tour.duration

        // Danh sách ảnh
        val adapter = object : BaseAdapter() {
            override fun getCount() = tour.transportImages.size
            override fun getItem(position: Int) = tour.transportImages[position]
            override fun getItemId(position: Int) = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val imageView = ImageView(parent.context)
                imageView.setImageResource(tour.transportImages[position])
                imageView.layoutParams = ViewGroup.LayoutParams(80, 80)
                return imageView
            }
        }

        holder.spinnerTransport.adapter = adapter
        holder.spinnerTransport.setSelection(tour.selectedTransportIndex)

        // Xử lý khi chọn phương tiện
        holder.spinnerTransport.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                tour.selectedTransportIndex = position
                holder.imgTransport.setImageResource(tour.transportImages[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun getItemCount(): Int = tourList.size
}
