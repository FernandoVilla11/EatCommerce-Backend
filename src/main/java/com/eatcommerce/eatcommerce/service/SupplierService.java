package com.eatcommerce.eatcommerce.service;

import com.eatcommerce.eatcommerce.DTO.SupplierDTO;
import com.eatcommerce.eatcommerce.entity.Supplier;
import com.eatcommerce.eatcommerce.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierDTO createSupplier(SupplierDTO request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier = supplierRepository.save(supplier);
        return convertToDTO(supplier);
    }

    public SupplierDTO editSupplier(Long supplierId, SupplierDTO request) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        if (request.getName() != null && !request.getName().isEmpty()) {
            supplier.setName(request.getName());
        }
        if (request.getEmail() != null) {
            supplier.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            supplier.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            supplier.setAddress(request.getAddress());
        }

        supplier = supplierRepository.save(supplier);
        return convertToDTO(supplier);
    }

    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public SupplierDTO getSupplierById(Long supplierId) {
        return convertToDTO(supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado")));
    }

    public void deleteSupplier(Long supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new RuntimeException("Proveedor no encontrado");
        }
        supplierRepository.deleteById(supplierId);
    }

    private SupplierDTO convertToDTO(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getSupplierId());
        dto.setName(supplier.getName());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        dto.setAddress(supplier.getAddress());
        return dto;
    }
}