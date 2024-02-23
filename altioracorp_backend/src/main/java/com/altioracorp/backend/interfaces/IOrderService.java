package com.altioracorp.backend.interfaces;

import com.altioracorp.backend.dto.OrderDto;
import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Order;

public interface IOrderService {

	public ResponseDto<Order> save(OrderDto order);
	
	public ResponseListDto<Order> getOrderList();
	
	public ResponseListDto<OrderDto> getOrderListByClientId(Long clientId);
}
