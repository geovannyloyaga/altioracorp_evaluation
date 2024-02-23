package com.altioracorp.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.altioracorp.backend.dao.ArticleDao;
import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Article;
import com.altioracorp.backend.interfaces.IArticleService;

@Service
public class ArticleService implements IArticleService {
	
	@Autowired
	private ArticleDao articleDao;

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public ResponseDto<Article> save(Article article) {
		Article articleCreated = null;
		ResponseDto<Article> responseArticle = new ResponseDto<Article>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			articleCreated = this.articleDao.save(article);
			if (articleCreated != null && articleCreated.getId() != null) {
				responseArticle.setResponseObject(articleCreated);
				this.transactionManager.commit(statusTransaction);
				responseArticle.setCode(200);
			} else {					
				this.transactionManager.rollback(statusTransaction);
				responseArticle.setCode(409);
				responseArticle.setError("Error al momento de crear la artículo");
			}
			return responseArticle;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responseArticle.setCode(409);
			responseArticle.setError("Error al momento de crear la artículo");
			return responseArticle;
		} finally {
			articleCreated = null;
			responseArticle = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}
	
	public ResponseDto<Article> update(Article article) {
		Article articleUpdated = null;
		ResponseDto<Article> responseArticle = new ResponseDto<Article>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			articleUpdated = this.articleDao.update(article);
			if (articleUpdated != null && articleUpdated.getId() != null) {
				responseArticle.setResponseObject(articleUpdated);
				this.transactionManager.commit(statusTransaction);
				responseArticle.setCode(200);	
			} else {
				this.transactionManager.rollback(statusTransaction);
				responseArticle.setCode(409);
				responseArticle.setError("Error al momento de crear la artículo");
			}
			return responseArticle;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responseArticle.setCode(409);
			responseArticle.setError("Error al momento de crear la artículo");
			return responseArticle;
		} finally {
			articleUpdated = null;
			responseArticle = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}
	
	@Transactional(readOnly=true)
	public ResponseListDto<Article> getArticleList() {
		ResponseListDto<Article> responseArticleList = new ResponseListDto<>(200, null, new ArrayList<>(), 0);
		List<Article> foundArticleList = null;
		try {
			foundArticleList = this.articleDao.getArticleList();
			responseArticleList.setResponseList(foundArticleList);
			if (foundArticleList.isEmpty()) {
				responseArticleList.setCode(200);
			} else {
				responseArticleList.setCode(409);
				responseArticleList.setError("Error al momento de obtener la lista de artículos");
			}
			return responseArticleList;
		} catch (Exception e) {
			responseArticleList.setCode(409);
			responseArticleList.setError("Error al momento de obtener la lista de artículos");
			return responseArticleList;
		} finally {
			responseArticleList = null;
			foundArticleList = null;
		}
	}
}
