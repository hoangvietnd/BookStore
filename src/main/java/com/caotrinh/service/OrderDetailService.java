package com.caotrinh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.caotrinh.entities.OrderDetail;
import com.caotrinh.repository.OrderDetailRepository;

@Service
public class OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	public void saveOrder(OrderDetail orderDetail) {
		orderDetailRepository.save(orderDetail);
	}
	
}
