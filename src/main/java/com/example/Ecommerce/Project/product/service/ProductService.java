package com.example.Ecommerce.Project.product.service;

import com.example.Ecommerce.Project.cart.dto.response.CartDTO;
import com.example.Ecommerce.Project.cart.model.Cart;
import com.example.Ecommerce.Project.cart.repo.CartRepo;
import com.example.Ecommerce.Project.cart.service.CartService;
import com.example.Ecommerce.Project.category.categoryrepo.CategoryRepo;
import com.example.Ecommerce.Project.category.model.Category;
import com.example.Ecommerce.Project.exeptionhandler.customexceptions.ProductAlreadyExist;
import com.example.Ecommerce.Project.product.dtos.request.ProductRequestDTO;
import com.example.Ecommerce.Project.product.dtos.response.GetAllProductsDTO;
import com.example.Ecommerce.Project.product.dtos.response.ProductResponseDTO;
import com.example.Ecommerce.Project.product.model.Product;
import com.example.Ecommerce.Project.product.repo.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService{

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final CartRepo cartRepo;
    private final CartService cartService;


    public ProductResponseDTO addProductToCategory(ProductRequestDTO dto, Long categoryId) {

           Category category = categoryRepo.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException("This Category Does not Exits"));

           if(productRepo.existsByProductNameIgnoreCaseAndCategory(dto.getProductName(),category))
              throw new ProductAlreadyExist("Product Already Exists");

//           One Approach
//           List<Product> productList = category.getProductList();
//           for(Product product: productList) {
//               if (product.getProductName().equalsIgnoreCase(dto.getProductName()))
//                   throw new ProductAlreadyExists("Product already exists!");
//           }

            Product newProduct = dto.toEntity();
            newProduct.setCategory(category);
            double specialPrice = dto.getPrice() - (dto.getPrice() * dto.getDiscount() / 100);
            newProduct.setSpecialPrice(specialPrice);
            newProduct.setImage("image.png");
            return ProductResponseDTO.toDTO(productRepo.save(newProduct));
    }

    public GetAllProductsDTO getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage =  productRepo.findAll(pageDetails);
        return GetAllProductsDTO.toDTO(productPage);

    }

    public GetAllProductsDTO getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(
                ()-> new EntityNotFoundException("Category not found!"));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> pageProducts = productRepo.findByCategory(category, pageDetails);
        return GetAllProductsDTO.toDTO(pageProducts);

    }

    public GetAllProductsDTO searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> pageProducts = productRepo.findByProductNameLikeIgnoreCase('%' +keyword+ '%', pageDetails);
        return GetAllProductsDTO.toDTO(pageProducts);
    }

    public ProductResponseDTO updateProduct(ProductRequestDTO dto, Long productId) {
        Product existingProduct = productRepo.findById(productId).orElseThrow(
                () -> new EntityNotFoundException("Product not found!"));
        boolean isPriceUpdated = false;
        boolean isDiscountUpdated = false;

        if (dto.getProductName() != null)
            existingProduct.setProductName(dto.getProductName());
        if (dto.getDescription() != null)
            existingProduct.setDescription(dto.getDescription());
        if (dto.getQuantity() != null)
            existingProduct.setQuantity(dto.getQuantity());
        if (dto.getPrice() != null) {
            existingProduct.setPrice(dto.getPrice());
            isPriceUpdated = true;
        }
        if (dto.getDiscount() != null) {
            existingProduct.setDiscount(dto.getDiscount());
            isDiscountUpdated = true;
        }
        if (isPriceUpdated || isDiscountUpdated) {
            double price = existingProduct.getPrice();
            double discount = existingProduct.getDiscount();
            double specialPrice = price - (price * discount / 100);
            existingProduct.setSpecialPrice(specialPrice);
        }
       Product savedProduct =  productRepo.save(existingProduct);

//          Getting Carts by Product Id
//        List<Cart> cartList = new ArrayList<>();
//        List<CartItem> items = savedProduct.getProducts();
//        for (CartItem item : items)
//           cartList.add(item.getCart());
        List<Cart> carts = cartRepo.findCartsByProductId(savedProduct.getProductId());
        List<CartDTO> cartDTOS = new ArrayList<>();
        for (Cart cart : carts) {
            cartDTOS.add(CartDTO.toDTO(cart, cart.getCartItemsList()));
        }
        cartDTOS.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(), savedProduct.getProductId()));



       return ProductResponseDTO.toDTO(savedProduct);
    }

    public void deleteProduct(long productId) {
        Product product = productRepo.findById(productId).orElseThrow(
                ()-> new EntityNotFoundException("Product not found"));

        List<Cart> carts = cartRepo.findCartsByProductId(product.getProductId());
        carts.forEach(cart -> cartService.deleteProductFromCart(cart.getCartId(),productId));
        productRepo.deleteById(productId);
    }

}
