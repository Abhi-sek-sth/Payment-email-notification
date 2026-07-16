package org.example.authenticationandauthorization.service;

import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.ProductRequestDto;
import org.example.authenticationandauthorization.dto.response.ProductResponseDto;
import org.example.authenticationandauthorization.entity.Product;
import org.example.authenticationandauthorization.mapper.ProductMapper;
import org.example.authenticationandauthorization.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper;
    private final ProductRepository repository;
    @Override
    public ProductResponseDto save(ProductRequestDto dto) {
        Product product = mapper.toEntity(dto);

        return mapper.toResponse(
                repository.save(product));
    }

    @Override
    @Cacheable(value = "product", key = "#id")

    public ProductResponseDto getById(Long id) {
        System.out.println("Reading from Database...");

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        return mapper.toResponse(product);
    }

    @Override
    @Cacheable(value = "products")

    public List<ProductResponseDto> getAll() {
        System.out.println("Reading all products from Database...");

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

    }

    @Override
    @CachePut(value = "products", key = "#id")

    public ProductResponseDto update(Long id, ProductRequestDto dto) {
        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

       product.setDescription(dto.description());
       product.setName(dto.name());
       product.setPrice(dto.price());
       product.setQuantity(dto.quantity());

        Product updated = repository.save(product);

        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = "products", key = "#id")

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
