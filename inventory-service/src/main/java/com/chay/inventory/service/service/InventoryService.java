package com.chay.inventory.service.service;

import com.chay.inventory.service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository IRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return IRepository.findBySkuCode(skuCode).isPresent();

    }
}