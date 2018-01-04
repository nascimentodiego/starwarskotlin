package br.com.dfn.starwarskotlin.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

/**
 * Created by diegonascimento on 19/12/17.
 */
fun ImageView.loadImage(context: Context, url: String) {
    Glide.with(context)
            .load(url)
            .into(this)
}

fun String.formatDate(pattern: String) {
    this.replace(this, "Date: " + this)
}