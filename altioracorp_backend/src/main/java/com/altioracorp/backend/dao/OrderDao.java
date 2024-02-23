package com.altioracorp.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.altioracorp.backend.entities.ArticleOrder;
import com.altioracorp.backend.entities.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class OrderDao {

	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Order> getOrderList() {
        List<Order> orders = null;
		try {
	        orders = entityManager.createQuery("FROM Order", Order.class).getResultList();
	        return orders;
		} catch (Exception e) {
			return orders;
		}  finally {
			orders = null;
		}
	}

	public List<Order> getOrderListByClientId(Long clientId) {
        List<Order> orders = null;
		try {
			String hql = "SELECT o FROM Order o JOIN o.client c WHERE c.id = :clientId";
	        orders = entityManager.createQuery(hql, Order.class)
	            .setParameter("clientId", clientId)
	            .getResultList();
	        return orders;
		} catch (Exception e) {
			return orders;
		}  finally {
			orders = null;
		}
	}
	
	public Order findById(Long id) {
		Order orderFound = null;
        try {
			orderFound = entityManager.find(Order.class, id);
			return orderFound;
        } catch (Exception e) {
			return orderFound;
		} finally {
			orderFound = null;
		}
    }

    public Order save(Order order) {
		Order orderCreated = null;
        try {
        	entityManager.persist(order);
        	entityManager.flush();
			orderCreated = order;
			return orderCreated;
        } catch (Exception e) {
			return orderCreated;
		} finally {
			orderCreated = null;
		}
    }

    public ArticleOrder saveArticle(ArticleOrder articleOrder) {
    	ArticleOrder articleOrderCreated = null;
        try {
        	entityManager.persist(articleOrder);
        	entityManager.flush();
			articleOrderCreated = articleOrder;
			return articleOrderCreated;
        } catch (Exception e) {
			return articleOrderCreated;
		} finally {
			articleOrderCreated = null;
		}
    }

    public Order update(Order order) {
		Order orderUpdated = null;
        try {
        	entityManager.merge(order);
        	entityManager.flush();
			orderUpdated = order;
			return orderUpdated;
        } catch (Exception e) {
			return orderUpdated;
		} finally {
			orderUpdated = null;
		}
    }

    public boolean delete(Order order) {
    	boolean orderDeleted = false;
        try {
        	entityManager.remove(order);
        	entityManager.flush();
        	orderDeleted = true;
			return orderDeleted;
        } catch (Exception e) {
			return orderDeleted;
		}
    }
}
