package com.example.Ecommerce.Project.product.dtos.response;

import com.example.Ecommerce.Project.product.dtos.request.ProductDTO;
import com.example.Ecommerce.Project.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllProductsDTO {

    private List<ProductDTO> productList;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean lastPage;

    public static GetAllProductsDTO toDTO(Page<Product> productPage) {
        List<ProductDTO> productsDTO = new ArrayList<>();

        for (Product product : productPage.getContent()) {
            productsDTO.add(
                    ProductDTO.builder()
                            .productId(product.getProductId())
                            .productName(product.getProductName())
                            .description(product.getDescription())
                            .discount(product.getDiscount())
                            .image(product.getImage())
                            .price(product.getPrice())
                            .specialPrice(product.getSpecialPrice())
                            .build()
            );
        }
        GetAllProductsDTO response = new GetAllProductsDTO();
        response.setProductList(productsDTO);
        response.setPageNumber(productPage.getNumber());
        response.setPageSize(productPage.getSize());
        response.setTotalElements(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());
        response.setLastPage(productPage.isLast());
        return response;

    }
}