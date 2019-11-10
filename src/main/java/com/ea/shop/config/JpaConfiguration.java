package com.ea.shop.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.ea.shop.persistence.entity" })
@EnableJpaRepositories(basePackages = {"com.ea.shop.persistence.repository" })
public class JpaConfiguration {
}
