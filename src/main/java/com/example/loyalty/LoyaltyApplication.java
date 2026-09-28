package com.example.loyalty;

import com.example.loyalty.model.Tier;
import com.example.loyalty.repository.TierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoyaltyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoyaltyApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(TierRepository tierRepository) {
        return args -> {
            if (tierRepository.count() == 0) {
                tierRepository.save(new Tier(null, "Bronze", 0, 0.0));
                tierRepository.save(new Tier(null, "Silver", 100, 5.0));
                tierRepository.save(new Tier(null, "Gold", 500, 10.0));
            }
        };
    }
}
