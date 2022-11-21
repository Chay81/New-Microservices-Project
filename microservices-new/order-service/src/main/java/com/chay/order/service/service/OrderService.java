package com.chay.order.service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.chay.order.service.Dto.InventoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chay.order.service.Dto.OrderLineItemsDto;
import com.chay.order.service.Dto.OrderRequest;
import com.chay.order.service.model.Order;
import com.chay.order.service.model.OrderLineItems;
import com.chay.order.service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository ORepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest ORequest) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderList = ORequest.getOrderDtoList().stream().map(this::mapToDto).toList();
        order.setOrderList(orderList);


        List<String> skuCodes = order.getOrderList().stream()
                .map(OrderLineItems::getSkuCode).toList();

//        Call InventoryService, and place order if place order if product is in stock
        InventoryResponse[] IRArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(IRArray).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock){
            ORepository.save(order);
        }else{
            throw new IllegalArgumentException("Product is not in Stock, Please try again later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto OrderDto) {
        OrderLineItems OItems = new OrderLineItems();
        OItems.setPrice(OrderDto.getPrice());
        OItems.setQuantity(OrderDto.getQuantity());
        OItems.setSkuCode(OrderDto.getSkuCode());
        return OItems;
    }
}