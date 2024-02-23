package com.altioracorp.backend.interfaces;

import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Article;

public interface IArticleService {

	public ResponseDto<Article> save(Article article);
	
	public ResponseDto<Article> update(Article article);
	
	public ResponseListDto<Article> getArticleList();
}
