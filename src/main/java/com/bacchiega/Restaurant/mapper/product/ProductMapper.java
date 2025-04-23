package com.bacchiega.Restaurant.mapper.product;

import com.bacchiega.Restaurant.dto.product.ProductDto;
import com.bacchiega.Restaurant.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto productDto);
    ProductDto toProductDto(Product product);
}
