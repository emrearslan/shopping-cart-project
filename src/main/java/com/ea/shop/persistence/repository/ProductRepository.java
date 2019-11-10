package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>,
        ProductRepositoryCustom, JpaSpecificationExecutor<Product> {

}
