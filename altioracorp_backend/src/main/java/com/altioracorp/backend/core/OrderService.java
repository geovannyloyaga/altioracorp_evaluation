package com.altioracorp.backend.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.altioracorp.backend.dao.ArticleDao;
import com.altioracorp.backend.dao.ClientDao;
import com.altioracorp.backend.dao.OrderDao;
import com.altioracorp.backend.dto.ArticleDto;
import com.altioracorp.backend.dto.OrderDto;
import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Article;
import com.altioracorp.backend.entities.ArticleOrder;
import com.altioracorp.backend.entities.Order;
import com.altioracorp.backend.interfaces.IOrderService;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	@Autowired
	private ClientDao clientDao;

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

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

	public ResponseDto<Order> save(OrderDto order) {
		Order orderCreated = null;
		ResponseDto<Order> responseOrder = new ResponseDto<Order>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			Order newOrder = new Order();
			newOrder.setCode(uuid.substring(0, 10));
			newOrder.setDate(order.getDate());
			newOrder.setSubtotal(order.getSubtotal());
			newOrder.setClient(this.clientDao.findById(order.getClient_id()));
			orderCreated = this.orderDao.save(newOrder);
			if (orderCreated != null && orderCreated.getId() != null) {
				List<ArticleDto> articles = order.getArticles();
				if (articles != null && articles.size() > 0) {
					for (ArticleDto articleDto : articles) {
						Article articleCreated = this.articleDao.findById(articleDto.getId());
						
						ArticleOrder articleOrder = new ArticleOrder();
						articleOrder.setArticle(articleCreated);
						articleOrder.setOrder(newOrder);
						articleOrder.setQuantity(articleDto.getQuantity());
						this.orderDao.saveArticle(articleOrder);
					}
				}
				responseOrder.setResponseObject(orderCreated);
				this.transactionManager.commit(statusTransaction);
				responseOrder.setCode(200);
			} else {					
				this.transactionManager.rollback(statusTransaction);
				responseOrder.setCode(409);
				responseOrder.setError("Error al momento de crear la orden");
			}
			return responseOrder;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responseOrder.setCode(409);
			responseOrder.setError("Error al momento de crear la orden");
			return responseOrder;
		} finally {
			orderCreated = null;
			responseOrder = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}
	
	public ResponseDto<Order> update(Order order) {
		Order orderUpdated = null;
		ResponseDto<Order> responseOrder = new ResponseDto<Order>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			orderUpdated = this.orderDao.update(order);
			if (orderUpdated != null && orderUpdated.getId() != null) {
				responseOrder.setResponseObject(orderUpdated);
				this.transactionManager.commit(statusTransaction);
				responseOrder.setCode(200);	
			} else {
				this.transactionManager.rollback(statusTransaction);
				responseOrder.setCode(409);
				responseOrder.setError("Error al momento de crear la orden");
			}
			return responseOrder;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responseOrder.setCode(409);
			responseOrder.setError("Error al momento de crear la orden");
			return responseOrder;
		} finally {
			orderUpdated = null;
			responseOrder = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}
	
	@Transactional(readOnly=true)
	public ResponseListDto<Order> getOrderList() {
		ResponseListDto<Order> responseOrderList = new ResponseListDto<>(200, null, new ArrayList<>(), 0);
		List<Order> foundOrderList = null;
		try {
			foundOrderList = this.orderDao.getOrderList();
			responseOrderList.setResponseList(foundOrderList);
			return responseOrderList;
		} catch (Exception e) {
			responseOrderList.setCode(409);
			responseOrderList.setError("Error al momento de obtener la lista de ordenes");
			return responseOrderList;
		} finally {
			responseOrderList = null;
			foundOrderList = null;
		}
	}
	
	@Transactional(readOnly=true)
	public ResponseListDto<OrderDto> getOrderListByClientId(Long clientId) {
		ResponseListDto<OrderDto> responseOrderList = new ResponseListDto<>(200, null, new ArrayList<>(), 0);
		List<Order> foundOrderList = null;
		List<OrderDto> responseOrders = null;
		OrderDto responseOrder = null;
		try {
			foundOrderList = this.orderDao.getOrderListByClientId(clientId);
			responseOrders = new ArrayList<>();
			for (Order order : foundOrderList) {
				responseOrder = new OrderDto();
				responseOrder.setId(order.getId());
				responseOrder.setDate(order.getDate());
				responseOrder.setSubtotal(order.getSubtotal());
				responseOrder.setCreatedAt(order.getCreatedAt());
				responseOrders.add(responseOrder);
			}
			responseOrderList.setResponseList(responseOrders);
			return responseOrderList;
		} catch (Exception e) {
			responseOrderList.setCode(409);
			responseOrderList.setError("Error al momento de obtener la lista de ordenes");
			return responseOrderList;
		} finally {
			responseOrderList = null;
			responseOrders = null;
			responseOrder = null;
			foundOrderList = null;
		}
	}
}
