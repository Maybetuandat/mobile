package com.example.bai_thuc_hanh_1

data class Tour(
    val id: Int,
    var destination: String,
    var duration: String,
    val transportImages: List<Int>, // Lưu danh sách ID ảnh
    var selectedTransportIndex: Int = 0 // Chỉ mục phương tiện được chọn
)
