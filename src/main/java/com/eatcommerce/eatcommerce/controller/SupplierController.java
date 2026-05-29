package com.eatcommerce.eatcommerce.controller;

import com.eatcommerce.eatcommerce.DTO.SupplierDTO;
import com.eatcommerce.eatcommerce.service.AuditService;
import com.eatcommerce.eatcommerce.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private AuditService auditService;

    @PostMapping("/create-supplier")
    public ResponseEntity<SupplierDTO> createSupplier(
            @RequestBody SupplierDTO request,
            Authentication auth) {
        SupplierDTO created = supplierService.createSupplier(request);
        auditService.log(auth.getName(), "CREATE_SUPPLIER", "Supplier",
                String.valueOf(created.getId()), "Creó proveedor: " + created.getName());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/edit-supplier")
    public ResponseEntity<SupplierDTO> editSupplier(
            @RequestParam Long supplierId,
            @RequestBody SupplierDTO request,
            Authentication auth) {
        SupplierDTO updated = supplierService.editSupplier(supplierId, request);
        auditService.log(auth.getName(), "UPDATE_SUPPLIER", "Supplier",
                String.valueOf(supplierId), "Editó proveedor: " + updated.getName());
        return ResponseEntity.ok(updated);
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
    public ResponseEntity<String> deleteSupplier(
            @RequestParam Long supplierId,
            Authentication auth) {
        auditService.log(auth.getName(), "DELETE_SUPPLIER", "Supplier",
                String.valueOf(supplierId), "Eliminó proveedor ID: " + supplierId);
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok("Proveedor eliminado correctamente");
    }
}