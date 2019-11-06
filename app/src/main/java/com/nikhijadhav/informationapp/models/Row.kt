package com.nikhijadhav.informationapp.models

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikhijadhav.informationapp.R

data class Row(var title: String? , var description: String? , var imageHref: String?) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(description)
        writeString(imageHref)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Row> = object : Parcelable.Creator<Row> {
            override fun createFromParcel(source: Parcel): Row = Row(source)
            override fun newArray(size: Int): Array<Row?> = arrayOfNulls(size)
        }

        @BindingAdapter("logo")
        @JvmStatic
        fun loadLogo(ivLogo: ImageView, imageRef :String){
            Glide.with(ivLogo.context).load(imageRef).thumbnail(0.5f).placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(ivLogo)
        }
    }



}

