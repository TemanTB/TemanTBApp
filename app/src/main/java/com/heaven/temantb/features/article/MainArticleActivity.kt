package com.heaven.temantb.features.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heaven.temantb.R

class MainArticleActivity : AppCompatActivity() {
    private lateinit var rvArticles: RecyclerView
    private val list = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articlemain)

        rvArticles = findViewById(R.id.rv_articles)
        rvArticles.setHasFixedSize(true)

        list.addAll(getListArticles())
        showRecyclerList()
    }

    private fun getListArticles(): ArrayList<Article> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listArticle = ArrayList<Article>()
        for (i in dataName.indices) {
            val article = Article(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listArticle.add(article)
        }
        return listArticle
    }

    private fun showRecyclerList() {
        rvArticles.layoutManager = LinearLayoutManager(this)
        val listArticleAdapter = ListArticleAdapter(list)
        rvArticles.adapter = listArticleAdapter
    }
}