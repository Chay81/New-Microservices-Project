package com.chay.order.service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chay.order.service.Dto.OrderLineItemsDto;
import com.chay.order.service.Dto.OrderRequest;
import com.chay.order.service.model.Order;
import com.chay.order.service.model.OrderLineItems;
import com.chay.order.service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository ORepository;

    public void placeOrder(OrderRequest ORequest) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderList = ORequest.getOrderDtoList().stream().map(this::mapToDto).toList();
        order.setOrderList(orderList);
        ORepository.save(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto OrderDto) {
        OrderLineItems OItems = new OrderLineItems();
        OItems.setPrice(OrderDto.getPrice());
        OItems.setQuantity(OrderDto.getQuantity());
        OItems.setSkuCode(OrderDto.getSkuCode());
        return OItems;
    }
}