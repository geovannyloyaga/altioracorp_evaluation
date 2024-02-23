package com.altioracorp.backend.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class OrderDto {

	private Long id;

	private Date date;

	private float subtotal;

	private Timestamp createdAt;

	private Timestamp updatedAt;

	private Long client_id;

	private List<ArticleDto> articles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public List<ArticleDto> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleDto> articles) {
		this.articles = articles;
	}
	
	
}
