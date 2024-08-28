package com.gary.SpringBootKotlin.respository

import com.gary.SpringBootKotlin.model.Article
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ArticleRepository {
    private var articles =
        mutableListOf<Article>(
            Article(id = UUID.randomUUID(), title = "title1", content = "content1"),
            Article(id = UUID.randomUUID(), title = "title2", content = "content2"),
            Article(id = UUID.randomUUID(), title = "title3", content = "content3"),
        )

    fun save(article: Article): Article {
        articles.add(article)
        return article
    }

    fun findAll(): List<Article> = articles

    fun findById(id: String): Article? = articles.find { it.id.toString() == id }

    fun delete(id: String): Boolean = articles.removeIf { it.id.toString() == id }

    fun update(
        id: String,
        article: Article,
    ): Article? {
        val index = articles.indexOfFirst { it.id.toString() == id }
        if (index == -1) {
            return null
        }
        articles[index] = article
        return article
    }
}
