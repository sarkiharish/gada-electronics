package com.hari.electronic.store.services.impl;

import com.hari.electronic.store.dtos.PageableResponse;
import com.hari.electronic.store.dtos.ProductDto;
import com.hari.electronic.store.entities.Category;
import com.hari.electronic.store.entities.Product;
import com.hari.electronic.store.exceptions.ResourceNotFoundException;
import com.hari.electronic.store.helper.Helper;
import com.hari.electronic.store.repositories.CategoryRepository;
import com.hari.electronic.store.repositories.ProductRepository;
import com.hari.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${product.image.path}")
    private String imagePath;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);

        //setting id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        //setting added date
        product.setAddedDate(new Date());

        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        //fetch the product of given id:
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with given id doesn't exist."));

        //update the product
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        //save the updated product
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        //fetch the product of given id:
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with given id doesn't exist."));

        //delete user profile image
        String fullPath = imagePath + product.getProductImageName();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        productRepository.delete(product);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        //fetch the product of given id:
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with given id doesn't exist."));
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
//        List<Product> productList = productRepository.findAll();
//        List<ProductDto> productDtoList = productList.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
//        return productDtoList;

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);

        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchProducts(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByTitle(subTitle, pageable);

        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        //fetch the category from db:
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with given id doesn't exist!"));

        Product product = mapper.map(productDto, Product.class);

        //setting id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        //setting added date
        product.setAddedDate(new Date());

        //setting category
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct, ProductDto.class);
    }


    @Override
    public ProductDto assignProductToCategory(String productId, String categoryId) {
        //fetch product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with given id doesn't exist!"));

        //fetch category
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with given id doesn't exist!"));

        //assigning product to category
        product.setCategory(category);
        Product save = productRepository.save(product);
        return mapper.map(save, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProductsOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with given id doesn't exist!"));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> byCategory = productRepository.findByCategory(category,pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byCategory, ProductDto.class);
        return pageableResponse;
    }
}
