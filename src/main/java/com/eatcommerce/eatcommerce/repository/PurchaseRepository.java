package com.eatcommerce.eatcommerce.repository;

import com.eatcommerce.eatcommerce.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT p FROM Purchase p WHERE p.supplier.supplierId = :supplierId")
    List<Purchase> findBySupplierId(@Param("supplierId") Long supplierId);

    List<Purchase> findAllByOrderByPurchaseDateDesc();

    List<Purchase> findByPurchaseDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = """
            SELECT
                s.supplier_id AS supplierId,
                s.name AS supplierName,
                SUM(p.total_price) AS totalSpent,
                COUNT(p.purchase_id) AS totalPurchases
            FROM purchases p
            JOIN suppliers s ON p.supplier_id = s.supplier_id
            WHERE p.purchase_date BETWEEN :startDate AND :endDate
            GROUP BY s.supplier_id, s.name
            ORDER BY totalSpent DESC
            """, nativeQuery = true)
    List<java.util.Map<String, Object>> getPurchasesReport(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}