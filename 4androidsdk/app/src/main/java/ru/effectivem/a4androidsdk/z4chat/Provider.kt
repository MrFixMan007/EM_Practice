package ru.effectivem.a4androidsdk.z4chat

import android.os.Parcel
import android.os.Parcelable
import androidx.core.content.FileProvider

class Provider() : Parcelable {

    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Provider> {
        override fun createFromParcel(parcel: Parcel): Provider {
            return Provider(parcel)
        }

        override fun newArray(size: Int): Array<Provider?> {
            return arrayOfNulls(size)
        }
    }
}