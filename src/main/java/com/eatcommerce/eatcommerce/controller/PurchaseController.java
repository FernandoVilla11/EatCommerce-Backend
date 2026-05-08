package com.eatcommerce.eatcommerce.controller;

import com.eatcommerce.eatcommerce.DTO.PurchaseDTO;
import com.eatcommerce.eatcommerce.DTO.PurchaseReportDTO;
import com.eatcommerce.eatcommerce.DTO.PurchaseRequest;
import com.eatcommerce.eatcommerce.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/create-purchase")
    public ResponseEntity<PurchaseDTO> createPurchase(@RequestBody PurchaseRequest request) {
        return ResponseEntity.ok(purchaseService.createPurchase(request));
    }

    @GetMapping("/get-all-purchases")
    public ResponseEntity<List<PurchaseDTO>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/get-purchase")
    public ResponseEntity<PurchaseDTO> getPurchaseById(@RequestParam Long purchaseId) {
        return ResponseEntity.ok(purchaseService.getPurchaseById(purchaseId));
    }

    @GetMapping("/get-purchases-by-supplier")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesBySupplier(@RequestParam Long supplierId) {
        return ResponseEntity.ok(purchaseService.getPurchasesBySupplier(supplierId));
    }

    @GetMapping("/get-purchases-by-date-range")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(purchaseService.getPurchasesByDateRange(startDate, endDate));
    }

    @DeleteMapping("/delete-purchase")
    public ResponseEntity<String> deletePurchase(@RequestParam Long purchaseId) {
        purchaseService.deletePurchase(purchaseId);
        return ResponseEntity.ok("Compra eliminada correctamente");
    }

    @GetMapping("/report")
    public ResponseEntity<PurchaseReportDTO> getPurchasesReport(
            @RequestParam Long supplierId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(purchaseService.getPurchasesReport(supplierId, startDate, endDate));
    }
}