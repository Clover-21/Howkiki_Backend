package clovar.howkiki.domain.order.service;

import clovar.howkiki.domain.menu.entity.Menu;
import clovar.howkiki.domain.menu.repository.MenuRepository;
import clovar.howkiki.domain.order.dto.requestDto.FinalOrderDetailDto;
import clovar.howkiki.domain.order.dto.requestDto.OrderCreateRequestDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderDetailDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderResponseDto;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderDetail;
import clovar.howkiki.domain.order.entity.OrderStatus;
import clovar.howkiki.domain.order.repository.OrderDetailRepository;
import clovar.howkiki.domain.order.repository.OrderRepository;
import clovar.howkiki.domain.store.entity.Store;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static clovar.howkiki.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    /* 주문 생성 */
    public OrderResponseDto createNewOrder(Long storeId, OrderCreateRequestDto requestDto) {

        // 주문 요청 검증
        validateOrderRequest(requestDto, storeId);

        // 해당 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, "/stores/"+storeId+"/orders"));

        // requestDto에서 주문한 메뉴 이름과 수량이 담긴 finalOrderDetails
        List<FinalOrderDetailDto> orderDetails = requestDto.getFinalOrderDetails();

        // 주문 총액 계산
        Long orderPrice = calculateOrderPrice(orderDetails, storeId);

        // Order 객체 생성
        Order order = Order.builder()
                .store(store)
                .sessionToken("1")  // 추후 수정
                .isTakeOut(requestDto.getIsTakeOut())
                .tableNumber(requestDto.getTableNumber())
                .orderPrice(orderPrice)
                .status(OrderStatus.NOT_YET_SENT)  // 기본상태 : NOT_YET_SENT // 추후 30초 후 AWAITING_ACCEPTANCE 상태로 바뀌도록 설정
                .build();
        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // *Order Detail 객체 생성
        List<OrderDetailDto> savedOrderDetail = createOrderDetail(savedOrder, orderDetails, storeId);

        // 응답 dto 생성 및 반환
        return OrderResponseDto.from(savedOrder, savedOrderDetail);

    }

    // 주문 요청 검증 메서드
    private void validateOrderRequest(OrderCreateRequestDto orderRequestDto, Long storeId) {
        if (orderRequestDto.getFinalOrderDetails() == null || orderRequestDto.getFinalOrderDetails().isEmpty()) {
            throw new CustomException(ORDER_DETAIL_EMPTY, "/stores/"+storeId+"/orders");
        }
    }

    // 주문 총액 계산 메서드
    private Long calculateOrderPrice(List<FinalOrderDetailDto> orderList, Long storeId) {
        return orderList.stream()
                .mapToLong(detail -> {
                    Menu menu = menuRepository.findMenuByMenuName(detail.getMenuName())
                            .orElseThrow(() -> new CustomException(MENU_NOT_FOUND, "/stores/"+storeId+"/orders"));
                    return (menu.getCost() * detail.getQuantity());
                })
                .sum();
    }

    // 주문 상세 생성 메서드
    private List<OrderDetailDto> createOrderDetail(Order order, List<FinalOrderDetailDto> orderList, Long storeId) {
        List<OrderDetailDto> result = new ArrayList<>();  // 변환된 DTO를 저장할 리스트

        for (FinalOrderDetailDto detail : orderList) {
            Menu menu = menuRepository.findMenuByMenuName(detail.getMenuName())
                    .orElseThrow(() -> new CustomException(MENU_NOT_FOUND, "/stores/"+storeId+"/orders"));
            // 검증 - 메뉴가 해당 가게의 메뉴인지 확인
            if (!storeId.equals(menuRepository.findStoreByMenuId(menu.getMenuId()))){
                throw new CustomException(MENU_NOT_FOR_THIS_STORE, "/stores/"+storeId+"/orders");
            }

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(detail.getQuantity())
                    .totalPrice(menu.getCost() * detail.getQuantity())
                    .build();
            // 저장
            orderDetailRepository.save(orderDetail);
            // 변환된 DTO를 리스트에 추가
            result.add(OrderDetailDto.from(orderDetail));
        }

        return result;  // 변환된 DTO 리스트 반환
    }

}
