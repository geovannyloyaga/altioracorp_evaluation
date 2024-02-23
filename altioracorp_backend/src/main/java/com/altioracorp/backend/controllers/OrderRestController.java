package com.altioracorp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altioracorp.backend.dto.OrderDto;
import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Order;
import com.altioracorp.backend.interfaces.IOrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

	@Autowired
	private IOrderService orderService;
	
	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/save")
	public ResponseDto<Order> save(@RequestBody OrderDto requestOrder){
		try {
			ResponseDto<Order> orderCreated = this.orderService.save(requestOrder);
			return orderCreated;
		} catch (Exception e) {
			return new ResponseDto<Order>(409, "Error al crear la orden", null);
		}
	}
	
	@GetMapping("/getOrderList")
	public ResponseListDto<Order> getOrderList(){
		try {
			ResponseListDto<Order> ordersFound = this.orderService.getOrderList();
			return ordersFound;
		} catch (Exception e) {
			return new ResponseListDto<Order>(409, "Error para obtener lista de ordenes", null, 0);
		}
	}

	@GetMapping("/{clientId}/getOrderListByClientId")
	public ResponseListDto<OrderDto> getOrderListByClientId(@PathVariable Long clientId){
		try {
			ResponseListDto<OrderDto> ordersFound = this.orderService.getOrderListByClientId(clientId);
			return ordersFound;
		} catch (Exception e) {
			return new ResponseListDto<OrderDto>(409, "Error para obtener lista de ordenes del cliente", null, 0);
		}
	}
}