package org.example.authenticationandauthorization.service;

import org.example.authenticationandauthorization.dto.request.ProductRequestDto;
import org.example.authenticationandauthorization.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto save(ProductRequestDto dto);

    ProductResponseDto getById(Long id);

    List<ProductResponseDto> getAll();

    ProductResponseDto update(Long id, ProductRequestDto dto);

    void delete(Long id);
}
