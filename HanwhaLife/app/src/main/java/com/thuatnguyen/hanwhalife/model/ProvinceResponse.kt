package com.thuatnguyen.hanwhalife.model

data class ProvinceResponse(
    val error: Int,
    val error_text: String,
    val data_name: String,
    val data: List<Province>
)