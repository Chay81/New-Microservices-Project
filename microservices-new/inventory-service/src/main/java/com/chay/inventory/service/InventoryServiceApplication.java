package com.chay.inventory.service;

import com.chay.inventory.service.model.Inventory;
import com.chay.inventory.service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }


    @Bean
    public CommandLineRunner loadData(InventoryRepository IRepository) {
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("Nokia");
            inventory.setQuantity(100);

            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("Nokia_Red");
            inventory1.setQuantity(0);

            IRepository.save(inventory);
            IRepository.save(inventory1);
        };
    }
}
