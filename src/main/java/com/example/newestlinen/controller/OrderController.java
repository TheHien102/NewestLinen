package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.order.OrderDetailDTO;
import com.example.newestlinen.dto.product.ProductAdminDTO;
import com.example.newestlinen.form.Order.CreateOrderForm;
import com.example.newestlinen.mapper.order.OrderMapper;
import com.example.newestlinen.storage.criteria.OrderDetailCriteria;
import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.OrderModel.Order;
import com.example.newestlinen.storage.model.OrderModel.OrderDetail;
import com.example.newestlinen.utils.projection.repository.AccountRepository;
import com.example.newestlinen.utils.projection.repository.Cart.CartItemRepository;
import com.example.newestlinen.utils.projection.repository.Order.OrderDetailRepository;
import com.example.newestlinen.utils.projection.repository.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<OrderDetailDTO>> listOrder(OrderDetailCriteria orderDetailCriteria, Pageable pageable) {
        if (isCustomer()) {
            orderDetailCriteria.setAccountId(getCurrentUserId());
        }

        Page<OrderDetail> orderDetailPage = orderDetailRepository.findAll(orderDetailCriteria.getSpecification(), pageable);

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
        List<OrderDetail> orderDetailList = orderMapper.fromCartItemListToOrderDetailList(cartItemRepository.findAllById(createOrderForm.getCartItemIdsList()));

        AtomicReference<Long> totalPrice = new AtomicReference<>(0L);

        Long shippingFee = 0L;

        Order order = new Order();

        orderDetailList.forEach(orderDetail -> {
            orderDetail.setOrder(order);
            totalPrice.updateAndGet(v -> v + (long) orderDetail.getPrice() * orderDetail.getQuantity());
        });

        order.setOrderDetails(orderDetailList);

        order.setTotalPrice(totalPrice.get());
        order.setShippingFee(shippingFee);
        order.setAddress(createOrderForm.getAddress());

        if (getCurrentUserId() != -1L) {
            Account account = accountRepository.findFirstById(getCurrentUserId());
            order.setAccount(account);
            order.setPhoneNumber(account.getPhone());
            cartItemRepository.deleteAllById(createOrderForm.getCartItemIdsList());
        } else {
            order.setPhoneNumber(createOrderForm.getPhoneNumber());
        }

        order.setNote(createOrderForm.getNote());

        order.setPaymentType(createOrderForm.getPaymentType());

        orderRepository.save(order);

        return new ApiMessageDto<>("created order successfully", HttpStatus.OK);
    }
}
