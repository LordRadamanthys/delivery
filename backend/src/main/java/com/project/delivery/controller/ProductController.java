package com.project.delivery.controller;

import com.project.delivery.Errors;
import com.project.delivery.dto.ProductDTO;
import com.project.delivery.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> listProduct = service.findAll();
        return ResponseEntity.ok().body(listProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable("id") long id) {
        ProductDTO product = service.findOne(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody ProductDTO productDTO) {
        ProductDTO dto = null;
        try {
            dto = service.insertOne(productDTO);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
            return ResponseEntity.created(uri).body(dto);
        } catch (Exception e) {
            Errors err = new Errors("", e.getMessage(), 400);
            return ResponseEntity.badRequest().body(err);
        }

    }
}
