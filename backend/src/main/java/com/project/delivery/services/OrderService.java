package com.project.delivery.services;

import com.project.delivery.config.OrderStatus;
import com.project.delivery.dto.OrderDTO;
import com.project.delivery.dto.ProductDTO;
import com.project.delivery.entities.Order;
import com.project.delivery.entities.Product;
import com.project.delivery.repository.OrderRepository;
import com.project.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        List<Order> ListOrder = repository.findOrdersWithProducts();
        return ListOrder.stream().map(item -> new OrderDTO(item)).collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(),
                OrderStatus.PENDING);

        for (ProductDTO p : dto.getListProduct()) {
            Product product = productRepository.getOne(p.getId());
            order.getProducts().add(product);
        }
        order = repository.save(order);
        return new OrderDTO(order);
    }


    @Transactional(readOnly = false)
    public OrderDTO setDelivered(Long id) {
        Order order = repository.getOne(id);
        order.setStatus(OrderStatus.DELIVERED);

        order = repository.save(order);
        return new OrderDTO(order);
    }
}
