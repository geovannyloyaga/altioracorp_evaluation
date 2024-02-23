package com.altioracorp.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.altioracorp.backend.entities.Article;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ArticleDao {

	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Article> getArticleList() {
        List<Article> articles = null;
		try {
	        articles = entityManager.createQuery("FROM Article", Article.class).getResultList();
	        return articles;
		} catch (Exception e) {
			return articles;
		}  finally {
			articles = null;
		}
	}

	public List<Article> getArticleListByOrderId(Long orderId) {
        List<Article> articles = null;
		try {
			String hql = "SELECT a FROM Article a JOIN a.order o WHERE o.id = :orderId";
			articles = entityManager.createQuery(hql, Article.class)
	            .setParameter("orderId", orderId)
	            .getResultList();
	        return articles;
		} catch (Exception e) {
			return articles;
		}  finally {
			articles = null;
		}
	}
	
	public Article findById(Long id) {
		Article articleFound = null;
        try {
			articleFound = entityManager.find(Article.class, id);
			return articleFound;
        } catch (Exception e) {
			return articleFound;
		} finally {
			articleFound = null;
		}
    }

    public Article save(Article article) {
		Article articleCreated = null;
        try {
        	entityManager.persist(article);
        	entityManager.flush();
			articleCreated = article;
			return articleCreated;
        } catch (Exception e) {
			return articleCreated;
		} finally {
			articleCreated = null;
		}
    }

    public Article update(Article article) {
		Article articleUpdated = null;
        try {
        	entityManager.merge(article);
        	entityManager.flush();
			articleUpdated = article;
			return articleUpdated;
        } catch (Exception e) {
			return articleUpdated;
		} finally {
			articleUpdated = null;
		}
    }

    public boolean delete(Article article) {
    	boolean articleDeleted = false;
        try {
        	entityManager.remove(article);
        	entityManager.flush();
        	articleDeleted = true;
			return articleDeleted;
        } catch (Exception e) {
			return articleDeleted;
		}
    }
}
