package com.example.bai_thuc_hanh_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tourAdapter: TourAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Danh sách tour
        val tourList = listOf(
            Tour(1, "Hà Nội - Sapa", "3 ngày 2 đêm", listOf(R.drawable.ic_xemay, R.drawable.ic_car, R.drawable.ic_plane)),
            Tour(2, "HCM - Đà Lạt", "4 ngày 3 đêm", listOf(R.drawable.ic_xemay, R.drawable.ic_car, R.drawable.ic_plane)),
            Tour(3, "Đà Nẵng - Huế", "2 ngày 1 đêm", listOf(R.drawable.ic_xemay, R.drawable.ic_car, R.drawable.ic_plane))
        )

        tourAdapter = TourAdapter(tourList)
        recyclerView.adapter = tourAdapter
    }
}
