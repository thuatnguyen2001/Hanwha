package com.thuatnguyen.hanwhalife.model

import android.os.Parcel
import android.os.Parcelable

class SanPhamBoSung(
    val tenSP:String?="",
    val soTienBH:Long?=0,
    val phiDinhKy:Long?=0,
    val status:String?=""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tenSP)
        parcel.writeValue(soTienBH)
        parcel.writeValue(phiDinhKy)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SanPhamBoSung> {
        override fun createFromParcel(parcel: Parcel): SanPhamBoSung {
            return SanPhamBoSung(parcel)
        }

        override fun newArray(size: Int): Array<SanPhamBoSung?> {
            return arrayOfNulls(size)
        }
    }

}