package com.altioracorp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Article;
import com.altioracorp.backend.interfaces.IArticleService;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

	@Autowired
	private IArticleService articleService;
	
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/getArticleList")
	public ResponseListDto<Article> findAll(){
		try {
			ResponseListDto<Article> articlesFound = this.articleService.getArticleList();
			return articlesFound;
		} catch (Exception e) {
			return new ResponseListDto<Article>(409, "Error para obtener lista de artículos", null, 0);
		}
	}

	@PostMapping("/save")
	public ResponseDto<Article> save(@RequestBody Article requestArticle){
		try {
			ResponseDto<Article> articleCreated = this.articleService.save(requestArticle);
			return articleCreated;
		} catch (Exception e) {
			return new ResponseDto<Article>(409, "Error al crear la artículo", null);
		}
	}
	
	@PostMapping("/update")
	public ResponseDto<Article> update(@RequestBody Article requestArticle){
		try {
			ResponseDto<Article> articleCreated = this.articleService.update(requestArticle);
			return articleCreated;
		} catch (Exception e) {
			return new ResponseDto<Article>(409, "Error al actualizar la artículo", null);
		}
	}
	
	@DeleteMapping("/delete/{articleId}")
	public ResponseDto<Article> delete(@PathVariable Long articleId){
		try {
			ResponseDto<Article> articleDeleted = this.articleService.delete(articleId);
			return articleDeleted;
		} catch (Exception e) {
			return new ResponseDto<Article>(409, "Error al eliminar artículo", null);
		}
	}
}
