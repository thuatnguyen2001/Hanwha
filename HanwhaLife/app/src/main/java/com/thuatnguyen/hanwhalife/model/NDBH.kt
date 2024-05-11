package com.thuatnguyen.hanwhalife.model

import android.os.Parcel
import android.os.Parcelable

class NDBH(
    val ndbhID: String?="",
    val accountID: String?="",
    val hoTen: String?="",
    val ngaySinh: String?="",
    val gioiTinh: String?="",
    val cccd: String?="",
    val ngayCap: String?="",
    val noiCap: String?="",
    val email: String?="",
    val sdt: String?=""
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ndbhID)
        parcel.writeString(accountID)
        parcel.writeString(hoTen)
        parcel.writeString(ngaySinh)
        parcel.writeString(gioiTinh)
        parcel.writeString(cccd)
        parcel.writeString(ngayCap)
        parcel.writeString(noiCap)
        parcel.writeString(email)
        parcel.writeString(sdt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NDBH> {
        override fun createFromParcel(parcel: Parcel): NDBH {
            return NDBH(parcel)
        }

        override fun newArray(size: Int): Array<NDBH?> {
            return arrayOfNulls(size)
        }
    }
}