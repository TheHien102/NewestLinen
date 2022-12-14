package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.order.OrderDetailDTO;
import com.example.newestlinen.exception.UnauthorizationException;
import com.example.newestlinen.form.order.CreateOrderForm;
import com.example.newestlinen.mapper.order.OrderMapper;
import com.example.newestlinen.mapper.product.VariantMapper;
import com.example.newestlinen.storage.criteria.OrderDetailCriteria;
import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.CartModel.Item;
import com.example.newestlinen.storage.model.OrderModel.Order;
import com.example.newestlinen.storage.model.OrderModel.OrderDetail;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.utils.projection.repository.AccountRepository;
import com.example.newestlinen.utils.projection.repository.Cart.CartItemRepository;
import com.example.newestlinen.utils.projection.repository.Order.OrderDetailRepository;
import com.example.newestlinen.utils.projection.repository.Order.OrderRepository;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class OrderController extends ABasicController {

    private final CartItemRepository cartItemRepository;

    private final OrderMapper orderMapper;

    private final AccountRepository accountRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final ProductRepository productRepository;

    private final VariantMapper variantMapper;

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<OrderDetailDTO>> listOrder(OrderDetailCriteria orderDetailCriteria, Pageable pageable) {
        if (!isCustomer() || !isAdmin() || isSuperAdmin()) {
            throw new UnauthorizationException("not a user");
        }
        Page<OrderDetail> orderDetailPage;
        if (isCustomer()) {
            orderDetailPage = orderDetailRepository.findAll(orderDetailCriteria.getSpecification(getCurrentUserId()), pageable);
        } else {
            orderDetailPage = orderDetailRepository.findAll(orderDetailCriteria.getSpecification(), pageable);
        }

        ApiMessageDto<ResponseListObj<OrderDetailDTO>> apiMessageDto = new ApiMessageDto<>();

        ResponseListObj<OrderDetailDTO> responseListObj = new ResponseListObj<>();

        responseListObj.setData(orderMapper.fromOrderDetailDataListToObjectList(orderDetailPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(orderDetailPage.getTotalPages());
        responseListObj.setTotalElements(orderDetailPage.getTotalElements());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List product success");
        return apiMessageDto;
    }

    @PostMapping("/create")
    public ApiMessageDto<String> createOrder(@Valid @RequestBody CreateOrderForm createOrderForm) {

        AtomicReference<Long> totalPrice = new AtomicReference<>(0L);

        Long shippingFee = 0L;

        Order order = new Order();

        if (getCurrentUserId() != -1L) {
            Account account = accountRepository.findFirstById(getCurrentUserId());
            order.setAccount(account);
            List<OrderDetail> orderDetailList = orderMapper.fromCartItemListToOrderDetailList(cartItemRepository.findAllById(createOrderForm.getCartItemIdsList()));
            orderDetailList.forEach(orderDetail -> {
                orderDetail.setOrder(order);
                totalPrice.updateAndGet(v -> v + (long) orderDetail.getPrice() * orderDetail.getQuantity());
            });
            order.setOrderDetails(orderDetailList);
            order.setPhoneNumber(account.getPhone());
            cartItemRepository.deleteAllById(createOrderForm.getCartItemIdsList());
        } else {
            List<OrderDetail> orderDetailList =
                    createOrderForm.getCartItemsList().stream().map(item -> {
                        OrderDetail orderDetail = new OrderDetail();
                        Product p = productRepository.findProductById(item.getProductId());
                        Item newItem = new Item();
                        String itemName = p.getName() + item.getVariants().stream().map(v -> " " + v.getName() + " " + v.getProperty()).collect(Collectors.joining());
                        newItem.setName(itemName);
                        newItem.setItemProduct(p);
                        List<Variant> OrderVariants = variantMapper.fromVariantDTOListToDataList(item.getVariants());
                        // map from product
                        List<Variant> variants = p.getVariants().stream().filter(OrderVariants::contains).collect(Collectors.toList());
                        variants.forEach(v -> {
                            v.getVariantItem().add(newItem);
                            v.setVariantProduct(p);
                        });
                        newItem.setVariants(new ArrayList<>());
                        newItem.getVariants().addAll(variants);
                        orderDetail.setItem(newItem);
                        orderDetail.setQuantity(item.getQuantity());
                        orderDetail.setPrice(p.getPrice());
                        totalPrice.updateAndGet(v -> v + (long) orderDetail.getPrice() * orderDetail.getQuantity());
                        orderDetail.setOrder(order);
                        return orderDetail;
                    }).collect(Collectors.toList());
            order.setOrderDetails(orderDetailList);
            order.setPhoneNumber(createOrderForm.getPhoneNumber());
        }
        order.setTotalPrice(totalPrice.get());
        order.setShippingFee(shippingFee);
        order.setAddress(createOrderForm.getAddress());
        order.setNote(createOrderForm.getNote());
        order.setPaymentType(createOrderForm.getPaymentType());
        orderRepository.save(order);

        return new ApiMessageDto<>("created order successfully", HttpStatus.OK);
    }
}
