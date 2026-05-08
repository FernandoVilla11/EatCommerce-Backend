package com.eatcommerce.eatcommerce.controller;

import com.eatcommerce.eatcommerce.DTO.SupplierDTO;
import com.eatcommerce.eatcommerce.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/create-supplier")
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO request) {
        return ResponseEntity.ok(supplierService.createSupplier(request));
    }

    @PutMapping("/edit-supplier")
    public ResponseEntity<SupplierDTO> editSupplier(@RequestParam Long supplierId,
                                                     @RequestBody SupplierDTO request) {
        return ResponseEntity.ok(supplierService.editSupplier(supplierId, request));
    }

    @GetMapping("/get-all-suppliers")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/get-supplier")
    public ResponseEntity<SupplierDTO> getSupplierById(@RequestParam Long supplierId) {
        return ResponseEntity.ok(supplierService.getSupplierById(supplierId));
    }

    @DeleteMapping("/delete-supplier")
    public ResponseEntity<String> deleteSupplier(@RequestParam Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok("Proveedor eliminado correctamente");
    }
}