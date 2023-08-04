package com.hari.electronic.store.repositories;

import com.hari.electronic.store.dtos.ProductDto;
import com.hari.electronic.store.entities.Category;
import com.hari.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    //search
    Page<Product> findByTitle(String subTitle, Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);


    //other methods if req. :
    //i.> Custom finder methods
    //ii.> query methods
}
