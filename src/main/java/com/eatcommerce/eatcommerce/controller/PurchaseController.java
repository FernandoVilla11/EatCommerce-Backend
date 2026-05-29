package com.eatcommerce.eatcommerce.controller;

import com.eatcommerce.eatcommerce.DTO.PurchaseDTO;
import com.eatcommerce.eatcommerce.DTO.PurchaseReportDTO;
import com.eatcommerce.eatcommerce.DTO.PurchaseRequest;
import com.eatcommerce.eatcommerce.service.AuditService;
import com.eatcommerce.eatcommerce.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private AuditService auditService;

    @PostMapping("/create-purchase")
    public ResponseEntity<PurchaseDTO> createPurchase(
            @RequestBody PurchaseRequest request,
            Authentication auth) {
        PurchaseDTO created = purchaseService.createPurchase(request);
        auditService.log(auth.getName(), "CREATE_PURCHASE", "Purchase",
                String.valueOf(created.getId()),
                "Registró compra a proveedor ID: " + created.getSupplierId() +
                " por $" + created.getTotalAmount());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/edit-purchase")
    public ResponseEntity<PurchaseDTO> editPurchase(
            @RequestParam Long purchaseId,
            @RequestBody PurchaseRequest request,
            Authentication auth) {
        PurchaseDTO updated = purchaseService.editPurchase(purchaseId, request);
        auditService.log(auth.getName(), "UPDATE_PURCHASE", "Purchase",
                String.valueOf(purchaseId),
                "Editó compra ID: " + purchaseId);
        return ResponseEntity.ok(updated);
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
    public ResponseEntity<String> deletePurchase(
            @RequestParam Long purchaseId,
            Authentication auth) {
        auditService.log(auth.getName(), "DELETE_PURCHASE", "Purchase",
                String.valueOf(purchaseId),
                "Eliminó compra ID: " + purchaseId);
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