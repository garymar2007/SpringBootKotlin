package com.gary.SpringBootKotlin.controller

import com.gary.SpringBootKotlin.model.Article
import com.gary.SpringBootKotlin.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/article")
class ArticleController(
    private val articleService: ArticleService,
) {
    @GetMapping
    fun listAll(): List<ArticleResponse> = articleService.findAll().map { it.toResponse() }

    private fun Article.toResponse() =
        ArticleResponse(
            id = id.toString(),
            title = title,
            content = content,
        )
}
