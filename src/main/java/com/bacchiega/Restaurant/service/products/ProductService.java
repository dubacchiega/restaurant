package com.bacchiega.Restaurant.service.products;

import com.bacchiega.Restaurant.dto.product.ProductDto;
import com.bacchiega.Restaurant.entity.Product;
import com.bacchiega.Restaurant.exception.ProductDuplicateException;
import com.bacchiega.Restaurant.mapper.product.ProductMapper;
import com.bacchiega.Restaurant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto createProduct(ProductDto productRequestDto){
        productRepository.findByName(productRequestDto.name()).ifPresent(
                product -> {throw new ProductDuplicateException("Product already exists");}
        );
        return productMapper.toProductDto(productRepository.save(productMapper.toProduct(productRequestDto)));
    }

    public List<ProductDto> listAllProduct(){
        List<Product> product = productRepository.findAll();
        return product.stream().map(productMapper::toProductDto).toList();
    }
}
