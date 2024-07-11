package com.thuatnguyen.hanwhalife.model

import android.os.Parcel
import android.os.Parcelable

class HopDong(
    val hopDongID:String?="",
    val bmbhID:String?="",
    val ndbhID:String?="",
    val nthID:String?="",
    val ngayKy:String?="",
    val ngayDenHan:String?="",
    val phiBaoHiem:Long?=0,
    val sanPhamChinh: SanPhamChinh?=null,
    val listSPBS: ArrayList<SanPhamBoSung>?=null,
    val lichSuDongPhi: ArrayList<DongPhi>? =null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readParcelable(SanPhamChinh::class.java.classLoader),
        parcel.createTypedArrayList(SanPhamBoSung),
        parcel.createTypedArrayList(DongPhi)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hopDongID)
        parcel.writeString(bmbhID)
        parcel.writeString(ndbhID)
        parcel.writeString(nthID)
        parcel.writeString(ngayKy)
        parcel.writeString(ngayDenHan)
        parcel.writeValue(phiBaoHiem)
        parcel.writeParcelable(sanPhamChinh, flags)
        parcel.writeTypedList(listSPBS)
        parcel.writeTypedList(lichSuDongPhi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HopDong> {
        override fun createFromParcel(parcel: Parcel): HopDong {
            return HopDong(parcel)
        }

        override fun newArray(size: Int): Array<HopDong?> {
            return arrayOfNulls(size)
        }
    }
}