package com.example.pcgames.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImage")
fun bindImage(view: ImageView,url: String?){
    Glide.with(view.context)
        .load(url)
        .into(view)
}