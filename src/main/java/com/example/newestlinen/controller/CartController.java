package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.exception.UnauthorizationException;
import com.example.newestlinen.form.cart.AddToCartForm;
import com.example.newestlinen.mapper.product.VariantMapper;
import com.example.newestlinen.storage.model.CartModel.Cart;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import com.example.newestlinen.storage.model.CartModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.utils.projection.repository.Cart.CartRepository;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class CartController extends ABasicController {

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final VariantMapper variantMapper;

    @PostMapping("/add_to_cart")
    public ApiMessageDto<String> addToCart(@Valid @RequestBody AddToCartForm addToCartForm) {
        if(getCurrentUserId()==-1L){
            throw new UnauthorizationException("not a user");
        }

        Cart cart = cartRepository.findByAccountId(getCurrentUserId());
        if (cart == null) {
            cart = new Cart();
        }

        Product p = productRepository.findProductById(addToCartForm.getProductId());

        List<Variant> variants = variantMapper.fromVariantDTOListToDataList(addToCartForm.getVariants());

        Item i = new Item();

        CartItem cartItem = new CartItem();

        // set properties for item
        i.setName(p.getName());
        i.setItemProduct(p);
        i.setVariants(variants);

        // set properties for cart item
        cartItem.setItem(i);
        cartItem.setQuantity(addToCartForm.getQuantity());
        cartItem.setPrice(addToCartForm.getPrice());

        // set properties for cart and map with cartItems
        cart.getCartItems().add(cartItem);
        cartItem.setCart(cart);

        cartRepository.save(cart);

        return new ApiMessageDto<>("Add to cart success", HttpStatus.OK);
    }


}
