package com.hari.electronic.store.services;

import com.hari.electronic.store.dtos.PageableResponse;
import com.hari.electronic.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    //create
    ProductDto createProduct(ProductDto productDto);

    //update
    ProductDto updateProduct(ProductDto productDto, String productId);

    //delete
    void deleteProduct(String productId);

    //get single
    ProductDto getSingleProduct(String productId);

    //get all
    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all : live
    PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search
    PageableResponse<ProductDto> searchProducts(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);

    //create product with category
    ProductDto createProductWithCategory(ProductDto productDto, String categoryId);

    //assign product to category
    ProductDto assignProductToCategory(String productId, String categoryId);

    //get all products of category
    PageableResponse<ProductDto> getAllProductsOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);

    //other methods..
}
