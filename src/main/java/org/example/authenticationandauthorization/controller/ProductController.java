package org.example.authenticationandauthorization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.ProductRequestDto;
import org.example.authenticationandauthorization.dto.response.ApiResponse;
import org.example.authenticationandauthorization.dto.response.ProductResponseDto;
import org.example.authenticationandauthorization.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductResponseDto save(
            @Valid @RequestBody ProductRequestDto dto) {

        return productService.save(dto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(
            @PathVariable Long id) {

        return productService.getById(id);
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {

        return productService.getAll();
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto dto) {

        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        productService.delete(id);
    }}
