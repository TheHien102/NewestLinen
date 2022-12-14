package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.cart.CartItemDTO;
import com.example.newestlinen.exception.NotFoundException;
import com.example.newestlinen.exception.UnauthorizationException;
import com.example.newestlinen.form.cart.AddToCartForm;
import com.example.newestlinen.form.cart.UpdateCartForm;
import com.example.newestlinen.mapper.cart.CartItemMapper;
import com.example.newestlinen.mapper.product.VariantMapper;
import com.example.newestlinen.storage.criteria.CartItemCriteria;
import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import com.example.newestlinen.storage.model.CartModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.utils.projection.repository.AccountRepository;
import com.example.newestlinen.utils.projection.repository.Cart.CartItemRepository;
import com.example.newestlinen.utils.projection.repository.Cart.ItemRepository;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class CartController extends ABasicController {

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    private final VariantMapper variantMapper;

    private final AccountRepository accountRepository;

    private final ItemRepository itemRepository;

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<CartItemDTO>> listCartItem(CartItemCriteria cartItemCriteria, Pageable pageable) {
        if (getCurrentUserId() == -1L) {
            throw new UnauthorizationException("not a user");
        }
        ApiMessageDto<ResponseListObj<CartItemDTO>> apiMessageDto = new ApiMessageDto<>();
        ResponseListObj<CartItemDTO> responseListObj = new ResponseListObj<>();
        Page<CartItem> cartItemPage;

        if (isCustomer()) {
            cartItemPage = cartItemRepository.findAll(cartItemCriteria.getSpecification(getCurrentUserId()), pageable);
        } else {
            cartItemPage = cartItemRepository.findAll(cartItemCriteria.getSpecification(), pageable);
        }

        responseListObj.setData(cartItemMapper.fromCartItemDataListToObjectList(cartItemPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(cartItemPage.getTotalPages());
        responseListObj.setTotalElements(cartItemPage.getTotalElements());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List user's cart Items success");
        return apiMessageDto;
    }

    @PostMapping("/create")
    public ApiMessageDto<String> addToCart(@Valid @RequestBody AddToCartForm addToCartForm) {
        if (!isCustomer()) {
            throw new UnauthorizationException("not a user");
        }
        Account account = accountRepository.findById(getCurrentUserId()).orElse(null);

        if (account == null) {
            throw new NotFoundException("account not found");
        }

        Product p = productRepository.findProductById(addToCartForm.getProductId());

        AtomicInteger price = new AtomicInteger(p.getPrice());

        List<Variant> CartVariants = variantMapper.fromVariantDTOListToDataList(addToCartForm.getVariants());

        List<Variant> variants = p.getVariants().stream().filter(CartVariants::contains).collect(Collectors.toList());

        variants.forEach(v -> {
            price.set(price.get() + v.getAddPrice());
        });

        price.set(price.get() * (100 - p.getDiscount()) / 100);

        Item item = new Item();

        CartItem cartItem = new CartItem();

        // set properties for item
        item.setName(p.getName());
        item.setItemProduct(p);
        item.setCartItem(cartItem);

        variants.forEach(v -> {
            v.getVariantItem().add(item);
        });

        item.getVariants().addAll(variants);

        // set properties for cart item
        cartItem.setItem(item);
        cartItem.setQuantity(addToCartForm.getQuantity());
        cartItem.setPrice(price.get());
        cartItem.setDiscount(p.getDiscount());

        // set properties for cart and map with cartItems
        cartItem.setAccount(account);
        account.getCartItems().add(cartItem);

        cartItemRepository.save(cartItem);

        return new ApiMessageDto<>("Add to cart success", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ApiMessageDto<String> updateCart(@Valid @RequestBody UpdateCartForm updateCartForm) {
        if (!isCustomer()) {
            throw new UnauthorizationException("not a user");
        }
        CartItem cartItem = cartItemRepository.findById(updateCartForm.getCartItemId()).orElse(null);
        if (cartItem == null) {
            throw new NotFoundException("cartItem not found");
        }
        cartItem.setQuantity(updateCartForm.getQuantity());
        cartItemRepository.save(cartItem);
        return new ApiMessageDto<>("Update cart success", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Transactional
    public ApiMessageDto<String> deleteCartItem(Long id) {
        if (!isCustomer() && !isAdmin()) {
            throw new UnauthorizationException("not a user");
        }
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        if (cartItem == null) {
            throw new NotFoundException("cartItem not found");
        }

        Item item = cartItem.getItem();

        item.setCartItem(null);
        cartItem.setItem(null);

        itemRepository.deleteById(item.getId());

        return new ApiMessageDto<>("deleted cartItem id: " + id, HttpStatus.OK);
    }
}
