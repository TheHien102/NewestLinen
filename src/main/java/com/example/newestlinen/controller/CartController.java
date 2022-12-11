package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.cart.CartItemDTO;
import com.example.newestlinen.exception.UnauthorizationException;
import com.example.newestlinen.form.cart.AddToCartForm;
import com.example.newestlinen.mapper.cart.CartItemMapper;
import com.example.newestlinen.mapper.product.VariantMapper;
import com.example.newestlinen.storage.criteria.CartItemCriteria;
import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.CartModel.Cart;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import com.example.newestlinen.storage.model.CartModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.utils.projection.repository.AccountRepository;
import com.example.newestlinen.utils.projection.repository.Cart.CartItemRepository;
import com.example.newestlinen.utils.projection.repository.Cart.CartRepository;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class CartController extends ABasicController {

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    private final VariantMapper variantMapper;

    private final AccountRepository accountRepository;

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<CartItemDTO>> listCartItem(CartItemCriteria cartItemCriteria, Pageable pageable) {
        if (getCurrentUserId() == -1L) {
            throw new UnauthorizationException("not a user");
        }

        ApiMessageDto<ResponseListObj<CartItemDTO>> apiMessageDto = new ApiMessageDto<>();

        ResponseListObj<CartItemDTO> responseListObj = new ResponseListObj<>();

        Page<CartItem> cartItemPage = cartItemRepository.findAll(cartItemCriteria.getSpecification(), pageable);

        responseListObj.setData(cartItemMapper.fromCartItemDataListToDtoList(cartItemPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(cartItemPage.getTotalPages());
        responseListObj.setTotalElements(cartItemPage.getTotalElements());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List cart Items success");
        return apiMessageDto;
    }

    @PostMapping("/create")
    public ApiMessageDto<String> addToCart(@Valid @RequestBody AddToCartForm addToCartForm) {
        if (!isCustomer()) {
            throw new UnauthorizationException("not a user");
        }

        Cart cart = cartRepository.findByAccountId(getCurrentUserId());

        if (cart == null) {
            Account account = accountRepository.findById(getCurrentUserId()).orElse(null);
            cart = new Cart();
            cart.setCartItems(new ArrayList<>());
            cart.setAccount(account);
        }

        Product p = productRepository.findProductById(addToCartForm.getProductId());

        int price = p.getPrice() * (100 - p.getDiscount()) / 100;

        List<Variant> variants = variantMapper.fromVariantDTOListToDataList(addToCartForm.getVariants());

        Item i = new Item();

        CartItem cartItem = new CartItem();

        String itemName = p.getName() + addToCartForm.getVariants().stream().map(v -> " " + v.getName() + " " + v.getProperty()).collect(Collectors.joining());

        // set properties for item
        i.setName(itemName);
        i.setItemProduct(p);
        i.setVariants(new ArrayList<>());
        i.getVariants().addAll(variants);

        variants.forEach(v -> {
            v.setVariantItem(List.of(i));
            v.setVariantProduct(p);
        });

        // set properties for cart item
        cartItem.setItem(i);
        cartItem.setQuantity(addToCartForm.getQuantity());
        cartItem.setPrice(price);

        // set properties for cart and map with cartItems
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);

        cartRepository.save(cart);

        return new ApiMessageDto<>("Add to cart success", HttpStatus.OK);
    }
}
