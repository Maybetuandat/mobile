package com.example.bai_thuc_hanh_1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tourAdapter: TourAdapter
    private lateinit var edtSearch: EditText
    private lateinit var btnAdd: Button
    private lateinit var edtTourName: EditText
    private lateinit var edtTourDuration: EditText
    private lateinit var spinnerTransport: Spinner
    private lateinit var btnAddTour: Button
    private val tourList = mutableListOf<Tour>()

    private val transportIcons = listOf(
        R.drawable.ic_xemay,
        R.drawable.ic_car,
        R.drawable.ic_plane
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        edtSearch = findViewById(R.id.search)

        edtTourName = findViewById(R.id.tourName)
        edtTourDuration = findViewById(R.id.tourTime)
        spinnerTransport = findViewById(R.id.vehicle)
        btnAddTour = findViewById(R.id.btnAddTour)

        recyclerView.layoutManager = LinearLayoutManager(this)

        tourList.addAll(
            listOf(
                Tour(1, "Hà Nội - Sapa", "3 ngày 2 đêm", transportIcons),
                Tour(2, "HCM - Đà Lạt", "4 ngày 3 đêm", transportIcons)
            )
        )

        tourAdapter = TourAdapter(tourList,
            onEdit = { editTour(it) },
            onDelete = { deleteTour(it) })
        recyclerView.adapter = tourAdapter


        btnAddTour.setOnClickListener { addTourCustom() }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchTour(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        setupSpinner()
    }

    private fun setupSpinner() {
        val spinnerAdapter = object : BaseAdapter() {
            override fun getCount() = transportIcons.size
            override fun getItem(position: Int) = transportIcons[position]
            override fun getItemId(position: Int) = position.toLong()
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val imageView = ImageView(parent.context)
                imageView.setImageResource(transportIcons[position])
                imageView.layoutParams = ViewGroup.LayoutParams(80, 80)
                return imageView
            }
        }
        spinnerTransport.adapter = spinnerAdapter
    }


    private fun addTourCustom() {
        val name = edtTourName.text.toString().trim()
        val duration = edtTourDuration.text.toString().trim()
        val selectedTransportIndex = spinnerTransport.selectedItemPosition

        if (name.isEmpty() || duration.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            return
        }

        val newTour = Tour(
            id = tourList.size + 1,
            destination = name,
            duration = duration,
            transportImages = transportIcons,
            selectedTransportIndex = selectedTransportIndex
        )

        tourList.add(newTour)
        tourAdapter.updateList(tourList)

        edtTourName.text.clear()
        edtTourDuration.text.clear()
        spinnerTransport.setSelection(0)
    }


    private fun editTour(tour: Tour) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_tour, null)
        val edtName = dialogView.findViewById<EditText>(R.id.edtEditTourName)
        val edtDuration = dialogView.findViewById<EditText>(R.id.edtEditTourDuration)
        val spinnerTransport = dialogView.findViewById<Spinner>(R.id.spinnerEditTransport)

        Log.d("maybetuandat", tour.id.toString())
        edtName.setText(tour.destination)
        edtDuration.setText(tour.duration)
        spinnerTransport.setSelection(tour.selectedTransportIndex)

        AlertDialog.Builder(this)
            .setTitle("Chỉnh sửa Tour")
            .setView(dialogView)
            .setPositiveButton("Lưu") { _, _ ->
                val newName = edtName.text.toString().trim()
                val newDuration = edtDuration.text.toString().trim()
                val newTransportIndex = spinnerTransport.selectedItemPosition

                if (newName.isNotEmpty() && newDuration.isNotEmpty()) {
                    tour.destination = newName
                    tour.duration = newDuration
                    tour.selectedTransportIndex = newTransportIndex

                    tourAdapter.updateList(tourList)
                } else {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }


    private fun deleteTour(tour: Tour) {
        tourList.removeIf { it.id == tour.id }
        tourAdapter.updateList(tourList)
    }

    private fun searchTour(query: String) {
        val filteredList =
            tourList.filter { it.destination.contains(query, ignoreCase = true) }.toMutableList()
        tourAdapter.updateList(filteredList)
    }
}
