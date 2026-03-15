package com.example.Ecommerce.Project.product.contoller;

import com.example.Ecommerce.Project.appcontants.appconstants.AppContants;
import com.example.Ecommerce.Project.exeptionhandler.ApiResponse;
import com.example.Ecommerce.Project.exeptionhandler.Status;
import com.example.Ecommerce.Project.product.dtos.request.ProductDTO;
import com.example.Ecommerce.Project.product.dtos.request.ProductRequestDTO;
import com.example.Ecommerce.Project.product.dtos.response.GetAllProductsDTO;
import com.example.Ecommerce.Project.product.dtos.response.ProductResponseDTO;
import com.example.Ecommerce.Project.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

     @PostMapping("/admin/categories/{categoryId}/product")
     public ResponseEntity<ApiResponse<ProductResponseDTO>> addProductToCategory(
             @RequestBody ProductRequestDTO dto,
             @PathVariable  Long categoryId) {
        ApiResponse<ProductResponseDTO> response = ApiResponse.<ProductResponseDTO>builder()
               .status(Status.CREATED)
               .message("Product Added Successfully")
               .data(productService.addProductToCategory(dto,categoryId))
               .build();
       return ResponseEntity.ok(response);

     }

     @GetMapping("/public/products")
     public ResponseEntity<ApiResponse<?>> getAllProducts(
             @RequestParam(name = "pageNumber",defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber,
             @RequestParam(name = "pageSize",defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize,
             @RequestParam(name = "sortBy", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortBy,
             @RequestParam(name = "sortOrder", defaultValue = AppContants.SORT_DIR, required = false) String sortOrder
     ) {
         GetAllProductsDTO  responseData = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
         boolean isEmpty = responseData.getProductList().isEmpty();
         Status status = isEmpty ? Status.NO_CONTENT : Status.SUCCESS;
         String message = responseData.getProductList().isEmpty() ?
                 "No products found!" : "Products fetched Successfully!";

         ApiResponse<GetAllProductsDTO> response = ApiResponse.<GetAllProductsDTO>builder()
                 .status(status)
                 .message(message)
                 .data(responseData)
                 .build();

         return ResponseEntity.ok(response);
     }

     @GetMapping("/categories/{categoryId}/products")
     ResponseEntity<ApiResponse<GetAllProductsDTO>>getProductsByCategory(
             @PathVariable Long categoryId,
             @RequestParam(name = "pageNumber",defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber,
             @RequestParam(name = "pageSize",defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize,
             @RequestParam(name = "sortBy", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortBy,
             @RequestParam(name = "sortOrder", defaultValue = AppContants.SORT_DIR, required = false) String sortOrder
     ) {
         ApiResponse<GetAllProductsDTO> response;
         GetAllProductsDTO products = productService.getProductsByCategory(categoryId,
                 pageNumber,
                 pageSize,
                 sortBy,
                 sortOrder);
         boolean isEmpty = products.getProductList().isEmpty();
         Status status = isEmpty ? Status.NO_CONTENT : Status.SUCCESS;
         String message = products.getProductList().isEmpty() ?
                 "No products found!" : "Products fetched Successfully!";

         response = ApiResponse.<GetAllProductsDTO>builder()
                 .status(status)
                 .message(message)
                 .data(products)
                 .build();

         return ResponseEntity.ok(response);
     }
     @GetMapping("/public/product/keyword/{keyword}")
     public ResponseEntity<ApiResponse<GetAllProductsDTO>> getProductsByKeyword(
             @PathVariable String keyword,
             @RequestParam(name = "pageNumber",defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber,
             @RequestParam(name = "pageSize",defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize,
             @RequestParam(name = "sortBy", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortBy,
             @RequestParam(name = "sortOrder", defaultValue = AppContants.SORT_DIR, required = false) String sortOrder
     ) {
         ApiResponse<GetAllProductsDTO> response;
         GetAllProductsDTO products = productService.searchProductByKeyword(keyword,
                 pageNumber,
                 pageSize,
                 sortBy,
                 sortOrder);

         boolean isEmpty = products.getProductList().isEmpty();
         Status status = isEmpty ? Status.NO_CONTENT : Status.SUCCESS;
         String message = products.getProductList().isEmpty() ?
                 "No products found!" : "Products fetched Successfully!";

         response = ApiResponse.<GetAllProductsDTO>builder()
                 .status(status)
                 .message(message)
                 .data(products)
                 .build();

         return ResponseEntity.ok(response);
     }

     @PutMapping("/admin/product/{productId}")
     public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
             @RequestBody ProductRequestDTO dto,
             @PathVariable Long productId) {

         ApiResponse<ProductResponseDTO> response = ApiResponse.<ProductResponseDTO>builder()
                 .status(Status.SUCCESS)
                 .message("Product Updated Successfully")
                 .data(productService.updateProduct(dto,productId))
                 .build();
         return ResponseEntity.ok(response);
     }


     @DeleteMapping("/admin/product/{productId}")
     ResponseEntity<ApiResponse<String>> deleteProduct (@PathVariable long productId) {
         productService.deleteProduct(productId);
         ApiResponse<String> response = ApiResponse.<String>builder()
                 .status(Status.SUCCESS)
                 .message("Product deleted Successfully!")
                 .build();
         return ResponseEntity.ok(response);
     }


}
