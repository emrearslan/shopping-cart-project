package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>,
        CategoryRepositoryCustom, JpaSpecificationExecutor<Category> {
}
