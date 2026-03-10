package com.example.Ecommerce.Project.cart.service;

import com.example.Ecommerce.Project.cart.dto.response.CartDTO;
import com.example.Ecommerce.Project.cart.model.Cart;
import com.example.Ecommerce.Project.cart.repo.CartRepo;
import com.example.Ecommerce.Project.cartitem.model.CartItem;
import com.example.Ecommerce.Project.cartitem.repo.CartItemRepo;
import com.example.Ecommerce.Project.exeptionhandler.customexceptions.InsufficientStockException;
import com.example.Ecommerce.Project.exeptionhandler.customexceptions.ResourceNotFoundException;
import com.example.Ecommerce.Project.product.model.Product;
import com.example.Ecommerce.Project.product.repo.ProductRepo;
import com.example.Ecommerce.Project.user.model.User;
import com.example.Ecommerce.Project.user.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;

import static com.example.Ecommerce.Project.appcontants.commonutils.Methods.getCurrentUser;

@Service
@RequiredArgsConstructor
public class CartService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;
    private final UserRepo userRepo;

    @Transactional
    public CartDTO addProductToCart(Long productId, Integer quantity) throws JsonProcessingException {

        // Find Existing cart or Create new one
        // Retrieve Product Details
        // Perform Validation
        // Create Cart Items
        // Save Cart

        Cart cart = createCart();
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not Exist"));

        CartItem cartItem = cartItemRepo.findCartItemByCartIdAndProductId(cart.getCartId(), productId)
                .orElse(null);
        if(quantity == null)
            throw new RuntimeException("Please select quantity");
        if (product.getQuantity() == 0)
            throw new ResourceNotFoundException("Product out of Stock");
        if (quantity <= 0)
            throw new RuntimeException("Please select quantity");
        if (quantity > product.getQuantity())
            throw new InsufficientStockException("Requested quantity exceeds available stock!");

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
            cartItemRepo.save(cartItem);
        }

        else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setCart(cart);
            newCartItem.setQuantity(quantity);
            newCartItem.setProductPrice(product.getPrice());
            newCartItem.setDiscount(product.getDiscount());
            cartItemRepo.save(newCartItem);

            product.setQuantity(product.getQuantity());
        }
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice()) * quantity);
        cartRepo.save(cart);
        entityManager.flush();
        entityManager.refresh(cart);

        return CartDTO.toDTO(cart, cart.getCartItemsList());

    }

    public Cart createCart() {

        User user = getCurrentUser();
        Cart cart =  user.getCart();
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setTotalPrice(0.00);
            newCart.setUser(user);
            return cartRepo.save(newCart);
        }
        else
            return cart;
    }

                // Delete Product

    public String deleteProductFromCart(Long cartId, Long productId) {

//        Cart cart = cartRepo.findById(cartId)
//                .orElseThrow(()-> new ResourceNotFoundException("Cart does not exist"));
//        List<CartItem> items = cart.getCartItemsList();
//        boolean isDeleted = false;
//        for (CartItem item : items) {
//            boolean isEqual = Objects.equals(item.getProduct().getProductId(), productId);
//            if (isEqual) {
//                cartItemRepo.deleteCartItemByCartIdAndProductId(cartId, productId);
//                isDeleted = true;
//                break;
//            }
//        }
//        if(!isDeleted)
//                throw new ResourceNotFoundException("Product does not exist");
//        else
//            return "Product Deleted Successfully!";

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart does not exist"));
        CartItem cartItem = cartItemRepo.findCartItemByCartIdAndProductId(cartId, productId)
                .orElse(null);
        if(cartItem == null)
            throw new ResourceNotFoundException("Product does not exist");

        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProductPrice() * cartItem.getQuantity());
        cartItemRepo.deleteCartItemByCartIdAndProductId(cartId, productId);
        return "Product removed from the cart successfully!";
    }

                    //   Update Quantity

    public void updateProductQuantity(Long productId, Integer quantity) {

        Cart cart = getCurrentUser().getCart();
        if(cart == null)
                throw new ResourceNotFoundException("Cart does not exist");

        CartItem cartItem = cartItemRepo.findCartItemByCartIdAndProductId(cart.getCartId(), productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product does not exists"));

        if(quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero");

        Product product = cartItem.getProduct();
        if(product.getQuantity() < quantity)
            throw new InsufficientStockException("Requested quantity exceeds current stock!");

        cartItem.setQuantity(quantity);
        cartItem.setProductPrice(product.getPrice());
        cartItem.setDiscount(product.getDiscount());

        System.out.println(cartItemRepo.save(cartItem));
        Double oldTotalPrice = cart.getTotalPrice() * cartItem.getQuantity();
        cart.setTotalPrice((product.getSpecialPrice() * quantity));
        System.out.println(cartRepo.save(cart));

    }
}
