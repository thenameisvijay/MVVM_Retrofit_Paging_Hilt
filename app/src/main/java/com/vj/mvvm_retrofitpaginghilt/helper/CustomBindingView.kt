package com.vj.mvvm_retrofitpaginghilt.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("android:loadImage")
fun loadImage(profilePic: ImageView?, imageUrl: String?) =
    Picasso.get().load(imageUrl)
        .resize(90, 90)
        .centerCrop()
        .into(profilePic)