package com.ceac.demo1.services;

import com.ceac.demo1.entities.ProductModel;
import com.ceac.demo1.repositories.IProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final IProductRepository repo;

    public ProductService(IProductRepository repo) { this.repo = repo; }

    public Page<ProductModel> getProducts(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p);
    }

    public Page<ProductModel> search(String q, int page, int size) {
        return repo.findByTitleContainingIgnoreCase(q, PageRequest.of(page, size));
    }

    public Page<ProductModel> byPlatform(String platform, int page, int size) {
        return repo.findByPlatformIgnoreCase(platform, PageRequest.of(page, size));
    }

    public Optional<ProductModel> getById(Long id){ return repo.findById(id); }

    // Bulk upsert muy simple para el seed
    public List<ProductModel> upsertAll(List<ProductModel> items){
        return repo.saveAll(items);
    }
}
