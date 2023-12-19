package com.heaven.temantb.features.article

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.heaven.temantb.R

class DetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_ARTICLE = "key_article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataArticle =  if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Article>(KEY_ARTICLE, Article::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Article>(KEY_ARTICLE)
        }

        if (dataArticle != null) {
            val tvDetailName: TextView = findViewById(R.id.tv_detail_name)
            val tvDetailDescription: TextView = findViewById(R.id.tv_detail_description)
            val ivDetailPhoto: ImageView = findViewById(R.id.iv_detail_photo)

            tvDetailName.text = dataArticle.name
            tvDetailDescription.text = dataArticle.description
            ivDetailPhoto.setImageResource(dataArticle.photo)
        }
    }
}
