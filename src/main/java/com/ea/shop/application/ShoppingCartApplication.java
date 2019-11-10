package com.ea.shop.application;

import com.ea.shop.config.ApplicationConfiguration;
import com.ea.shop.config.JpaConfiguration;
import com.ea.shop.config.RestConfiguration;
import com.ea.shop.config.SwaggerConfiguration;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.builder.CategoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ ApplicationConfiguration.class, JpaConfiguration.class, RestConfiguration.class, SwaggerConfiguration.class })
public class ShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

}
