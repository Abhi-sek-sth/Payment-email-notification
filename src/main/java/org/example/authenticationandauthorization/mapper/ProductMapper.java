package org.example.authenticationandauthorization.mapper;

import ch.qos.logback.core.model.ComponentModel;
import org.example.authenticationandauthorization.dto.request.ProductRequestDto;
import org.example.authenticationandauthorization.dto.response.ProductResponseDto;
import org.example.authenticationandauthorization.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDto dto);

    ProductResponseDto toResponse(Product product);
}
