package com.project.delivery.services;

import com.project.delivery.dto.OrderDTO;
import com.project.delivery.entities.Order;
import com.project.delivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        List<Order> ListOrder = repository.findOrdersWithProducts();
        return ListOrder.stream().map(item -> new OrderDTO(item)).collect(Collectors.toList());
    }
}
