package com.example.teran.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.teran.R
import com.google.android.material.slider.Slider

class SliderAdapter(
    val context: Context,
    val sliderList: ArrayList<SliderData>
) : PagerAdapter() {
    override fun getCount(): Int = sliderList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = layoutInflater.inflate(R.layout.slider_intro, container, false)

        val image: ImageView = view.findViewById(R.id.imageSlider)
        val title: TextView = view.findViewById(R.id.titleSlider)
        val desc: TextView = view.findViewById(R.id.descSlider)

        val sliderData: SliderData = sliderList[position]
        image.setImageResource(sliderData.image)
        title.text = sliderData.title
        desc.text = sliderData.desc

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}