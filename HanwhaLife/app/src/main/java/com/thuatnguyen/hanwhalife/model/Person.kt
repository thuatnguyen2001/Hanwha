package com.thuatnguyen.hanwhalife.model

import android.os.Parcel
import android.os.Parcelable

open class Person(
    val ID: String?="",
    val hoTen: String?="",
    val ngaySinh: String?="",
    val gioiTinh: String?="",
    val cccd: String?="",
    val ngayCap: String?="",
    val noiCap: String?="",
    val sdt: String?="",
):Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(ID)
        parcel.writeString(hoTen)
        parcel.writeString(ngaySinh)
        parcel.writeString(gioiTinh)
        parcel.writeString(cccd)
        parcel.writeString(ngayCap)
        parcel.writeString(noiCap)
        parcel.writeString(sdt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}