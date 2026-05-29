package com.eatcommerce.eatcommerce.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.eatcommerce.eatcommerce.DTO.SaleDTO;
import com.eatcommerce.eatcommerce.DTO.SaleRequest;
import com.eatcommerce.eatcommerce.service.AuditService;
import com.eatcommerce.eatcommerce.service.SalesService;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private AuditService auditService;

    @PostMapping("/register-sale")
    public ResponseEntity<SaleDTO> registerSale(
            @RequestBody SaleRequest request,
            Authentication auth) {
        SaleDTO created = salesService.registerSale(request);
        auditService.log(auth.getName(), "CREATE_SALE", "Sale",
                String.valueOf(created.getSaleId()),
                "Registró venta por $" + created.getTotalPrice());
        return ResponseEntity.ok(created);
    }

    @GetMapping("/get-sale")
    public ResponseEntity<SaleDTO> getSaleById(@RequestParam Long saleId) {
        return ResponseEntity.ok(salesService.getSaleById(saleId));
    }

    @GetMapping("/get-all-sales")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return ResponseEntity.ok(salesService.getAllSales());
    }

    @GetMapping("/get-sales-by-day")
    public ResponseEntity<List<SaleDTO>> getSalesByDay(@RequestParam LocalDate date) {
        return ResponseEntity.ok(salesService.getSalesByDay(date));
    }

    @GetMapping("/get-sales-by-date-range")
    public ResponseEntity<List<SaleDTO>> getSalesByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(salesService.getSalesByDateRange(startDate, endDate));
    }

    @DeleteMapping("/delete-sale")
    public ResponseEntity<String> deleteSale(
            @RequestParam Long saleId,
            Authentication auth) {
        auditService.log(auth.getName(), "DELETE_SALE", "Sale",
                String.valueOf(saleId),
                "Eliminó venta ID: " + saleId);
        salesService.deleteSale(saleId);
        return ResponseEntity.ok("Sale deleted successfully");
    }
}