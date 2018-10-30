package com.hewking.third

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hewking.custom.R
import kotlinx.android.synthetic.main.fragment_image_ex_layout.*
import pl.droidsonroids.gif.GifDrawable

class ImageExFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_ex_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    /*    Glide.with(this)
//                .asBitmap()
//                .asGif()
                .load(R.drawable.d03)
                .apply(RequestOptions().apply {
                    placeholder(R.drawable.d03)
                })
                .into(iv_gif)*/

   /*     Glide.with(this)
                .asGif()
                .load(R.drawable.d03)
                .apply(RequestOptions().apply {
                    dontAnimate()

                })
                .listener(object : RequestListener<GifDrawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                })
                .into(object : SimpleTarget<GifDrawable>(){
                    override fun onResourceReady(resource: GifDrawable, transition: Transition<in GifDrawable>?) {

                    }
                })*/

    /*    Glide.with(this)
                .load(R.drawable.d03)
                .apply(RequestOptions().apply {
                    diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                })
                .into(iv_gif)*/

//        val request = Glide.with(this)
//                .downloadOnly()
//                .load(R.drawable.d03)
//
//        request.into(object : SimpleTarget<File>(){
//            override fun onResourceReady(resource: File, transition: Transition<in File>?) {
//                val drawable = GifDrawableBuilder().from(resource).build()
//                iv_gif.setImageDrawable(drawable)
//            }
//        })

        val gifDrawable = GifDrawable(resources,R.drawable.d03)
        iv_gif.setImageDrawable(gifDrawable)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Glide.with(this).pauseAllRequests()
    }
}