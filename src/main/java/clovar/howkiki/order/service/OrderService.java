package clovar.howkiki.order.service;

import clovar.howkiki.menu.entity.Menu;
import clovar.howkiki.menu.repository.MenuRepository;
import clovar.howkiki.order.dto.OrderDetailDto;
import clovar.howkiki.order.dto.OrderMenuListDetailDto;
import clovar.howkiki.order.dto.OrderResponseDto;
import clovar.howkiki.order.dto.OrderRequestDto;
import clovar.howkiki.order.entity.Order;
import clovar.howkiki.order.entity.OrderDetail;
import clovar.howkiki.order.entity.OrderStatus;
import clovar.howkiki.order.repository.OrderDetailRepository;
import clovar.howkiki.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MenuRepository menuRepository;

    /* 주문 생성 */
    public OrderResponseDto createNewOrder(Long storeId, OrderRequestDto requestDto) {

        // 주문 요청 검증
        validateOrderRequest(requestDto);

        // 가게 객체 찾기 (추후)

        // requestDto에서 주문한 메뉴이름과 수량이 담긴 OrderDetails
        List<OrderDetailDto> orderDetails = requestDto.getOrderDetails();

        // 주문 총액 계산
        Long orderPrice = calculateOrderPrice(orderDetails);

        // Order 객체 생성
        Order order = Order.builder()
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
                    return menu.getCost() * detail.getQuantity();
                })
                .sum();
    }

    /* 주문 상세 생성 */
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


}
