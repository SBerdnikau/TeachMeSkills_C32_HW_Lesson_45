package com.tms.controller;

import com.tms.model.Product;
import com.tms.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiResponses(value = {
            @ApiResponse(description = "Product created successfully", responseCode = "201"),
            @ApiResponse(description = "Conflict during product creation", responseCode = "409")
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product)  {
        logger.info("Received request to create a product: {}", product);
        Optional<Product> createdProduct = productService.createProduct(product);
        if(createdProduct.isEmpty()){
            logger.error("Failed to create product: {}", product);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Product created successfully: {}", createdProduct.get());
        return new ResponseEntity<>(createdProduct.get(), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "Product fetched successfully", responseCode = "200"),
            @ApiResponse(description = "Product not found", responseCode = "404")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productId){
        logger.info("Received request to fetch product by ID: {}", productId);
        Optional<Product> product = productService.getProductById(productId);
        if(product.isEmpty()){
            logger.warn("Product with ID {} not found", productId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Product with ID {} fetched successfully: {}", productId, product.get());
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "Product updated successfully", responseCode = "200"),
            @ApiResponse(description = "Conflict during product update", responseCode = "409")
    })
    @PutMapping("/id")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody Product product) {
        logger.info("Received request to update product with ID {}: {}", productId, product);
        Optional<Product> updatedProduct = productService.updateProduct(product);
        logger.error("Failed to update product with ID {}: {}", productId, product);
        if(updatedProduct.isEmpty()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Product with ID {} updated successfully: {}", productId, updatedProduct.get());
        return new ResponseEntity<>(updatedProduct.get(), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "Product deleted successfully", responseCode = "204"),
            @ApiResponse(description = "Conflict during product delete", responseCode = "409")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") @Parameter(description = "Product ID") Long productId)  {
        logger.info("Received request to delete product with ID: {}", productId);
        Optional<Product> deletedProduct = productService.deleteProduct(productId);
        if(deletedProduct.isPresent()){
            logger.warn("Product with ID {} not found for deletion", productId);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Product with ID {} deleted successfully", productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "All products fetched successfully", responseCode = "200"),
            @ApiResponse(description = "Product is not found in list", responseCode = "404")
    })
    @GetMapping("/all-products")
    public ResponseEntity<List<Product>> getUserListPage(HttpServletResponse response){
        logger.info("Received request to fetch all products");

        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("All products fetched successfully. Total products: {}", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
