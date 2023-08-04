package com.hari.electronic.store.services;

import com.hari.electronic.store.dtos.CategoryDto;
import com.hari.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //get all
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single
    CategoryDto get(String categoryId);

    //search:
    List<CategoryDto> search(String keyword);

}
