package com.example.teran.data.helper

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

class SvgImageLoader(private val context: Context) {

    private val imageLoader: ImageLoader by lazy {
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    fun loadSvgImage(imageUrl: String, targetImageView: ImageView) {
        val imageRequest = ImageRequest.Builder(context)
            .data(imageUrl)
            .target(targetImageView)
            .build()
        imageLoader.enqueue(imageRequest)
    }
}
