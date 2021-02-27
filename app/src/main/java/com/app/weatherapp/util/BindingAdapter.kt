package com.app.weatherapp.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.BuildConfig
import com.app.weatherapp.home.ui.home.adapter.CityAdapter
import com.app.weatherapp.responseobject.City
import com.bumptech.glide.Glide


@BindingAdapter("cityList")
fun bindRecommendationRecyclerView(
    recyclerView: RecyclerView,
    cityList: List<City?>?
) {

    val adapter = recyclerView.adapter as CityAdapter
    adapter.submitList(cityList)

}




@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        imgUrl.plus(".png")
        val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
        Glide.with(imgView.context)
            .load(imgUri)
            /*.transform(RoundedCornersTransformation(5, 0,
                RoundedCornersTransformation.CornerType.TOP))*/
            .into(imgView)
    }
}

@BindingAdapter("iconPath")
fun bindWeatherIcon(imgView: ImageView, iconPath: String?) {
    if (!iconPath.isNullOrEmpty()) {
        iconPath.let {
            var imgUrl = BuildConfig.ICON_URL
            imgUrl = imgUrl.plus(iconPath)
            imgUrl = imgUrl.plus("@2x.png")
            val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
            Glide.with(imgView.context)
                .load(imgUri)
                /*.transform(RoundedCornersTransformation(5, 0,
                RoundedCornersTransformation.CornerType.TOP))*/
                .into(imgView)
        }
    }
}