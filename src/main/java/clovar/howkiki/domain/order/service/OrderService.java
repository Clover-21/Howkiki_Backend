package clovar.howkiki.domain.order.service;

import clovar.howkiki.domain.menu.entity.Menu;
import clovar.howkiki.domain.menu.repository.MenuRepository;
import clovar.howkiki.domain.order.dto.*;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderDetail;
import clovar.howkiki.domain.order.entity.OrderStatus;
import clovar.howkiki.domain.order.repository.OrderDetailRepository;
import clovar.howkiki.domain.order.repository.OrderRepository;
import clovar.howkiki.order.dto.*;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    /* 주문 생성 */
    public OrderResponseDto createNewOrder(Long storeId, OrderRequestDto requestDto) {

        // 주문 요청 검증
        validateOrderRequest(requestDto);

        // 가게 객체 찾기 (추후)
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게 없음"));

        // requestDto에서 주문한 메뉴이름과 수량이 담긴 OrderDetails
        List<OrderDetailDto> orderDetails = requestDto.getOrderDetails();

        // 주문 총액 계산
        Long orderPrice = calculateOrderPrice(orderDetails);

        // Order 객체 생성
        Order order = Order.builder()
                .storeId(storeId)
                .tableNumber(requestDto.getTableNumber())
                .orderPrice(orderPrice)
                .status(OrderStatus.PENDING)  // 기본상태 : PENDING
                .build();
        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // *Order Detail 객체 생성
        List<OrderMenuListDetailDto> savedOrderDetail = createOrderDetail(savedOrder, orderDetails);

        // 응답 dto 생성 및 반환
        return OrderResponseDto.from(savedOrder, savedOrderDetail);

    }

    // 주문 요청 검증 메서드
    private void validateOrderRequest(OrderRequestDto orderRequestDto) {
        if (orderRequestDto.getOrderDetails() == null || orderRequestDto.getOrderDetails().isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 비어있습니다.");
        }
    }

    // 주문 총액 계산 메서드
    private Long calculateOrderPrice(List<OrderDetailDto> orderList) {
        return orderList.stream()
                .mapToLong(detail -> {
                    Menu menu = menuRepository.findMenuByName(detail.getMenuName())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다: " + detail.getMenuName()));
                    return (menu.getCost() * detail.getQuantity());
                })
                .sum();
    }

    // 주문 상세 생성
    private List<OrderMenuListDetailDto> createOrderDetail(Order order, List<OrderDetailDto> orderList) {
        List<OrderMenuListDetailDto> result = new ArrayList<>();  // 변환된 DTO를 저장할 리스트

        for (OrderDetailDto detail : orderList) {
            Menu menu = menuRepository.findMenuByName(detail.getMenuName())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다: " + detail.getMenuName()));

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(detail.getQuantity())
                    .totalPrice(menu.getCost() * detail.getQuantity())
                    .build();

            // 저장
            orderDetailRepository.save(orderDetail);

            // 변환된 DTO를 리스트에 추가
            result.add(OrderMenuListDetailDto.from(orderDetail));
        }

        return result;  // 변환된 DTO 리스트 반환
    }


    /* 모든 주문 목록 조회 */
    public OrderListResponseDto getOrderList(Long storeId) {

        // 해당 가게의 모든 주문 목록 조회
        List<Order> orders = orderRepository.findOrderByStoreId(storeId);

        // 각 주문에 대해 orderDetail을 가져와서 menuSummary 생성
        List<OrderListDetailResponseDto> orderListDetailResponseDtos = new ArrayList<>();

        for (Order order : orders) {
            // 주문의 orderDetail들을 OrderDetailDto로 변환
            List<OrderDetailDto> menuSummary = order.getOrderDetails().stream()
                    .map(orderDetail -> OrderDetailDto.builder()
                            .menuName(orderDetail.getMenu().getName()) // 메뉴 이름
                            .quantity(orderDetail.getQuantity()) // 수량
                            .build())
                    .collect(Collectors.toList());

            // 주문의 상세 정보 생성
            OrderListDetailResponseDto responseDto = OrderListDetailResponseDto.builder()
                    .orderId(order.getId())
                    .tableNumber(order.getTableNumber())
                    .orderPrice(order.getOrderPrice())
                    .status(order.getStatus())
                    .createdAt(order.getCreatedAt())
                    .menuSummary(menuSummary) // 메뉴 요약
                    .build();

            // 리스트에 추가
            orderListDetailResponseDtos.add(responseDto);
        }

        return new OrderListResponseDto(orderListDetailResponseDtos);

    }


    /* 주문 상세 조회 */
    public OrderResponseDto getOrder(Long storeId, Long orderId) {

        // order 객체 찾기
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 주문이 존재하지 않습니다."));

        // orderDetail 객체 찾기
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailByOrderId(orderId);

        // OrderDetail 객체를 DTO로 변환하여 리스트 생성
        List<OrderMenuListDetailDto> menuList = orderDetails.stream()
                .map(OrderMenuListDetailDto::from)
                .collect(Collectors.toList());

        // 응답 dto 생성
        OrderResponseDto responseDto = OrderResponseDto.builder()
                .orderId(order.getId())
                .tableNumber(order.getTableNumber())
                .orderPrice(order.getOrderPrice())
                .status(order.getStatus())
                .expectedPrepTime(order.getExpectedPrepTime())
                .createdAt(order.getCreatedAt())
                .modifiedAt(order.getModifiedAt())
                .menuList(menuList)
                .build();

        return responseDto;

    }

}
