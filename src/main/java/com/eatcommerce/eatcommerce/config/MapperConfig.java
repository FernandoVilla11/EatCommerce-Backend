package com.eatcommerce.eatcommerce.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eatcommerce.eatcommerce.DTO.ProductQuantity;
import com.eatcommerce.eatcommerce.DTO.SaleDTO;
import com.eatcommerce.eatcommerce.entity.Sale;
import com.eatcommerce.eatcommerce.entity.SaleProduct;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<SaleProduct, ProductQuantity> saleProductToProductQuantity = context -> {
            SaleProduct saleProduct = context.getSource();

            if (saleProduct == null) return null;

            ProductQuantity productQuantity = new ProductQuantity();
            productQuantity.setProductId(saleProduct.getProduct().getProductId());
            productQuantity.setQuantity(saleProduct.getQuantity());
            return productQuantity;
        };

        mapper.addConverter(saleProductToProductQuantity, SaleProduct.class, ProductQuantity.class);

        mapper.typeMap(Sale.class, SaleDTO.class)
            .addMappings(m -> m.map(Sale::getSaleProducts, SaleDTO::setItems));

        return mapper;
    }
}
