package com.example.davegilbier.spotify

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Dave Gilbier on 1/28/18.
 */

data class Songs(
        val song: String = " ",
        val singer: String = " ",
        val album: String = " ",
        val song_path: String = " ",
        var stat: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(song)
        parcel.writeString(singer)
        parcel.writeString(album)
        parcel.writeString(song_path)
        parcel.writeInt(stat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Songs> {
        override fun createFromParcel(parcel: Parcel): Songs {
            return Songs(parcel)
        }

        override fun newArray(size: Int): Array<Songs?> {
            return arrayOfNulls(size)
        }
    }

}