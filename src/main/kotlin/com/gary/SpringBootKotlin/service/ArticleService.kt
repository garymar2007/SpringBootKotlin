package com.gary.SpringBootKotlin.service

import com.gary.SpringBootKotlin.model.Article
import com.gary.SpringBootKotlin.respository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
) {
    fun findAll(): List<Article> = articleRepository.findAll()

    fun findById(id: String): Article? = articleRepository.findById(id)

    fun save(article: Article): Article = articleRepository.save(article)

    fun delete(id: String): Boolean = articleRepository.delete(id)

    fun update(
        id: String,
        article: Article,
    ): Article? = articleRepository.update(id, article)
}
