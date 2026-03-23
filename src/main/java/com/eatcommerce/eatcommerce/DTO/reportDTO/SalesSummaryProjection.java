package com.eatcommerce.eatcommerce.DTO.reportDTO;

import java.time.LocalDate;

public interface SalesSummaryProjection {
    LocalDate getDate();
    Double getTotalSales();
    Long getTotalOrders();
}
