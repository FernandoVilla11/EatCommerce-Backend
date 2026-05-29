package com.eatcommerce.eatcommerce.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.eatcommerce.eatcommerce.DTO.ProductDTO;
import com.eatcommerce.eatcommerce.DTO.ProductEdit;
import com.eatcommerce.eatcommerce.DTO.ProductRequest;
import com.eatcommerce.eatcommerce.DTO.ProductSaleTable;
import com.eatcommerce.eatcommerce.service.AuditService;
import com.eatcommerce.eatcommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AuditService auditService;

    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProduct(
            @RequestPart String request,
            @RequestPart MultipartFile image,
            Authentication auth) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest requestObj = mapper.readValue(request, ProductRequest.class);
        requestObj.setImage(image);
        ProductDTO created = productService.createProduct(requestObj);
        auditService.log(auth.getName(), "CREATE_PRODUCT", "Product",
                String.valueOf(created.getProductId()), "Creó producto: " + created.getProductName());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/edit-product")
    public ResponseEntity<ProductDTO> editProduct(
            @RequestParam Long productId,
            @RequestBody ProductEdit editRequest,
            Authentication auth) {
        ProductDTO updated = productService.editProduct(productId, editRequest);
        auditService.log(auth.getName(), "UPDATE_PRODUCT", "Product",
                String.valueOf(productId), "Editó producto: " + updated.getProductName());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get-product")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteProduct(
            @RequestParam Long productId,
            Authentication auth) {
        auditService.log(auth.getName(), "DELETE_PRODUCT", "Product",
                String.valueOf(productId), "Eliminó producto ID: " + productId);
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/get-products-for-sale-table")
    public ResponseEntity<List<ProductSaleTable>> getProductsForSaleTable() {
        return ResponseEntity.ok(productService.getProductsForSaleTable());
    }
}