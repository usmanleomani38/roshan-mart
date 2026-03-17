package com.example.Ecommerce.Project.order.service;

import com.example.Ecommerce.Project.address.model.Address;
import com.example.Ecommerce.Project.address.repo.AddressRepo;
import com.example.Ecommerce.Project.cart.model.Cart;
import com.example.Ecommerce.Project.cart.service.CartService;
import com.example.Ecommerce.Project.cartitem.model.CartItem;
import com.example.Ecommerce.Project.cartitem.repo.CartItemRepo;
import com.example.Ecommerce.Project.exeptionhandler.customexceptions.ResourceNotFoundException;
import com.example.Ecommerce.Project.order.dtos.OrderDTO;
import com.example.Ecommerce.Project.order.model.Order;
import com.example.Ecommerce.Project.order.repo.OrderRepo;
import com.example.Ecommerce.Project.orderitem.model.OrderItem;
import com.example.Ecommerce.Project.orderitem.repo.OrderItemRepo;
import com.example.Ecommerce.Project.payment.model.Payment;
import com.example.Ecommerce.Project.payment.repo.PaymentRepo;
import com.example.Ecommerce.Project.product.model.Product;
import com.example.Ecommerce.Project.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.Ecommerce.Project.appcontants.commonutils.Methods.getCurrentUser;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final AddressRepo addressRepo;
    private final PaymentRepo paymentRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final CartService cartService;
    private final CartItemRepo cartItemRepo;

    public OrderDTO placeOrder(String currentUserEmail,
                               Long addressId,
                               String paymentMethod,
                               String paymentGatewayName,
                               String paymentGatewayId,
                               String paymentGatewaystatus,
                               String paymentResponseMessage) {

        Cart cart = getCurrentUser().getCart();
        if(cart == null) {
            throw new ResourceNotFoundException("Cart does not exist!");
       }


        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found!"));

        Order order = new Order();
        order.setEmail(currentUserEmail);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setStatus("Order Accepted!");
        order.setAddress(address);

        Payment payment = new Payment();
        payment.setPaymentGatewayId(paymentGatewayId);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentGatewayName(paymentGatewayName);
        payment.setPaymentGatewayId(paymentGatewayId);
        payment.setPaymentGatewayStatus(paymentGatewaystatus);
        payment.setPaymentGatewayResponseMessage(paymentResponseMessage);
        payment.setOrder(order);
        payment = paymentRepo.save(payment);

        order.setPayment(payment);

        List<CartItem> cartItemList = cart.getCartItemsList();
        if(cartItemList.isEmpty())
            throw new ResourceNotFoundException("No cart items exist!");

        for (CartItem cartItem: cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
            orderItem.setOrder(order);
            order.getOrderItemsList().add(orderItem);
        }
        Order savedOrder = orderRepo.save(order);
  ;

//        One approach but multiple db calls

//        for(CartItem item : cart.getCartItemsList()) {
//            item.getProduct().setQuantity(item.getProduct().getQuantity()
//                    -item.getQuantity());
//            productRepo.save(item.getProduct());
//       }

        List<Product> products = new ArrayList<>();
        for(CartItem item : cart.getCartItemsList()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            products.add(product);
        }
        cartItemRepo.deleteByCartId(cart.getCartId());
        productRepo.saveAll(products);

        return OrderDTO.toDTO(savedOrder);

   }


}
