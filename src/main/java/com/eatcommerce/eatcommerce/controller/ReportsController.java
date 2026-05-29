package com.eatcommerce.eatcommerce.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatcommerce.eatcommerce.DTO.reportDTO.GrowMonthlyRatio;
import com.eatcommerce.eatcommerce.DTO.reportDTO.MonthlySales;
import com.eatcommerce.eatcommerce.DTO.reportDTO.ProductSalesCompareResponse;
import com.eatcommerce.eatcommerce.DTO.reportDTO.SalesCompare;
import com.eatcommerce.eatcommerce.DTO.reportDTO.SalesSummaryProjection;
import com.eatcommerce.eatcommerce.DTO.reportDTO.TopProduct;
import com.eatcommerce.eatcommerce.service.ReportsService;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private ReportsService reportsService;

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProduct>> getTopProducts(@RequestParam LocalDate startDate, 
                                                            @RequestParam LocalDate endDate, 
                                                            @RequestParam int topNumber) {
        return ResponseEntity.ok(reportsService.getTopBestSellingProducts(startDate, endDate, topNumber));
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<List<SalesSummaryProjection>> getSalesSummary(@RequestParam LocalDate startDate,
                                                            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(reportsService.getSalesSummary(startDate, endDate));
    }

    @GetMapping("/monthly-sales")
    public ResponseEntity<List<MonthlySales>> getMonthlySales(
            @RequestParam LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(reportsService.getMonthlySales(startDate, endDate));
    }

    @GetMapping("/sales-growth")
    public ResponseEntity<List<GrowMonthlyRatio>> getSalesGrowth(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reportsService.getMonthlyGrowthRatios(startDate, endDate));
    }

    @GetMapping("/compare-sales")
    public ResponseEntity<SalesCompare> compareSales(
            @RequestParam LocalDate startDate1,
            @RequestParam LocalDate endDate1,
            @RequestParam LocalDate startDate2,
            @RequestParam LocalDate endDate2) {
        return ResponseEntity.ok(reportsService.compareSalesBetweenRanges(startDate1, endDate1, startDate2, endDate2));
    }

    @GetMapping("/product-sales-compare")
    public ResponseEntity<List<ProductSalesCompareResponse>> compareProductSales(
            @RequestParam Long productId1,
            @RequestParam Long productId2) {
        return ResponseEntity.ok(reportsService.compareTwoProducts(productId1, productId2));
    }
}
