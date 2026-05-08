package com.eatcommerce.eatcommerce.service;

import com.eatcommerce.eatcommerce.DTO.PurchaseDTO;
import com.eatcommerce.eatcommerce.DTO.PurchaseProductDTO;
import com.eatcommerce.eatcommerce.DTO.PurchaseReportDTO;
import com.eatcommerce.eatcommerce.DTO.PurchaseRequest;
import com.eatcommerce.eatcommerce.entity.*;
import com.eatcommerce.eatcommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PurchaseDTO createPurchase(PurchaseRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(LocalDateTime.parse(request.getPurchaseDate()));
        purchase.setConcept(request.getConcept());
        purchase.setStatus("COMPLETED");

        double totalAmount = 0.0;
        List<PurchaseProduct> purchaseProducts = new ArrayList<>();

        for (PurchaseProductDTO item : request.getItems()) {
            PurchaseProduct purchaseProduct = new PurchaseProduct();
            purchaseProduct.setPurchase(purchase);
            purchaseProduct.setItemName(item.getItemName());
            purchaseProduct.setQuantity(item.getQuantity());
            purchaseProduct.setUnitPrice(item.getUnitPrice());
            purchaseProducts.add(purchaseProduct);
            totalAmount += item.getUnitPrice() * item.getQuantity();
        }

        purchase.setPurchaseProducts(purchaseProducts);
        purchase.setTotalPrice(totalAmount);
        purchase = purchaseRepository.save(purchase);

        return convertToDTO(purchase);
    }

    public PurchaseDTO editPurchase(Long purchaseId, PurchaseRequest request) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        if (request.getConcept() != null && !request.getConcept().isEmpty()) {
            purchase.setConcept(request.getConcept());
        }
        if (request.getPurchaseDate() != null && !request.getPurchaseDate().isEmpty()) {
            purchase.setPurchaseDate(LocalDateTime.parse(request.getPurchaseDate()));
        }

        if (request.getItems() != null && !request.getItems().isEmpty()) {
            purchase.getPurchaseProducts().clear();

            double totalAmount = 0.0;
            for (PurchaseProductDTO item : request.getItems()) {
                PurchaseProduct purchaseProduct = new PurchaseProduct();
                purchaseProduct.setPurchase(purchase);
                purchaseProduct.setItemName(item.getItemName());
                purchaseProduct.setQuantity(item.getQuantity());
                purchaseProduct.setUnitPrice(item.getUnitPrice());
                purchase.getPurchaseProducts().add(purchaseProduct);
                totalAmount += item.getUnitPrice() * item.getQuantity();
            }
            purchase.setTotalPrice(totalAmount);
        }

        purchase = purchaseRepository.save(purchase);
        return convertToDTO(purchase);
    }

    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAllByOrderByPurchaseDateDesc()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public PurchaseDTO getPurchaseById(Long purchaseId) {
        return convertToDTO(purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada")));
    }

    public List<PurchaseDTO> getPurchasesBySupplier(Long supplierId) {
        return purchaseRepository.findBySupplierId(supplierId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<PurchaseDTO> getPurchasesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return purchaseRepository.findByPurchaseDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public void deletePurchase(Long purchaseId) {
        if (!purchaseRepository.existsById(purchaseId)) {
            throw new RuntimeException("Compra no encontrada");
        }
        purchaseRepository.deleteById(purchaseId);
    }

    public PurchaseReportDTO getPurchasesReport(Long supplierId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        List<Map<String, Object>> results = purchaseRepository.getPurchasesReport(start, end);

        return results.stream()
                .filter(row -> ((Number) row.get("supplierId")).longValue() == supplierId)
                .findFirst()
                .map(row -> new PurchaseReportDTO(
                        ((Number) row.get("supplierId")).longValue(),
                        (String) row.get("supplierName"),
                        ((Number) row.get("totalPurchases")).intValue(),
                        ((Number) row.get("totalSpent")).doubleValue(),
                        startDate.format(DATE_FORMATTER),
                        endDate.format(DATE_FORMATTER)
                ))
                .orElse(new PurchaseReportDTO(
                        supplierId, null, 0, 0.0,
                        startDate.format(DATE_FORMATTER),
                        endDate.format(DATE_FORMATTER)
                ));
    }

    private PurchaseDTO convertToDTO(Purchase purchase) {
        PurchaseDTO dto = new PurchaseDTO();
        dto.setId(purchase.getPurchaseId());
        dto.setSupplierId(purchase.getSupplier().getSupplierId());
        dto.setSupplierName(purchase.getSupplier().getName());
        dto.setDate(purchase.getPurchaseDate().format(DATE_FORMATTER));
        dto.setConcept(purchase.getConcept());
        dto.setTotalAmount(purchase.getTotalPrice());
        dto.setStatus(purchase.getStatus());

        List<PurchaseProductDTO> items = purchase.getPurchaseProducts()
                .stream()
                .map(pp -> new PurchaseProductDTO(
                        pp.getItemName(),
                        pp.getQuantity(),
                        pp.getUnitPrice()
                ))
                .toList();

        dto.setItems(items);
        return dto;
    }
}