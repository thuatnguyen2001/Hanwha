package com.thuatnguyen.hanwhalife.model

import android.os.Parcel
import android.os.Parcelable

class DongPhi(
    val ngay: String?="",
    val phi: Long?=0,
    val hinhThuc: String?="",
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ngay)
        parcel.writeValue(phi)
        parcel.writeString(hinhThuc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DongPhi> {
        override fun createFromParcel(parcel: Parcel): DongPhi {
            return DongPhi(parcel)
        }

        override fun newArray(size: Int): Array<DongPhi?> {
            return arrayOfNulls(size)
        }
    }
}