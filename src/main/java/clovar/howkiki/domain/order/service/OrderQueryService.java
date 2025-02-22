package clovar.howkiki.domain.order.service;

import clovar.howkiki.domain.order.dto.responseDto.*;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import clovar.howkiki.domain.order.repository.OrderRepository;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static clovar.howkiki.domain.order.entity.OrderStatus.*;
import static clovar.howkiki.global.exception.ErrorCode.STORE_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    /* 주문 목록 전체 조회 */
    public List<OrderResponseDto<OrderDetailBriefDto>> getOrderList(Long storeId) {

        String methodUrl = "/stores/"+storeId+"/orders";
        findStore(storeId, methodUrl);

        // 해당 가게의 모든 주문 조회
        List<Order> orders = orderRepository.findByStoreId(storeId);

        return getOrderResponseDtos(orders);
    }

    /* 포장 주문 전체 조회 */
    public List<OrderResponseDto<OrderDetailBriefWithPriceDto>> getTakeOutOrder(Long storeId) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+storeId+"/orders/take-out";
        findStore(storeId, methodUrl);

        // 해당 가게의 포장 주문 조회
        List<Order> orders = orderRepository.findTakeOutOrderByStoreId(storeId);

        return getTakeOutOrderResponseDtos(orders);
    }

    /* 테이블 주문 전체 조회 */
    public List<OrderResponseDto<OrderDetailBriefDto>> getTableOrderList(Long storeId) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+storeId+"/orders/tables/all";
        findStore(storeId, methodUrl);

        // 해당 가게의 테이블 주문 조회
        List<Order> orders = orderRepository.findTableOrderByStoreId(storeId);

        return getOrderResponseDtos(orders);
    }

    /* 특정 상태의 주문 목록 조회 */
    public List<OrderResponseDto<OrderDetailBriefDto>> getOrderByStatus(Long storeId, OrderStatus status) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders?status="+status;
        findStore(storeId, methodUrl);

        // 해당 가게의 특정 상태의 주문 조회
        List<Order> orders = orderRepository.findOrderByStoreIdAndStatus(storeId, status);

        return getOrderResponseDtos(orders);
    }


    /* 해당 테이블 주문 목록 조회 */
    public OrderBriefResponseDto<OrderDetailDto> getTableOrder(Long storeId, Long tableNumber) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/tables/" +tableNumber;
        findStore(storeId, methodUrl);

        // 해당 테이블의 주문 조회
        List<Order> orders = orderRepository.findOrderByTableNumber(storeId, tableNumber);

        // orderList 생성
        Long totalPrice = 0L;
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            // orderDetails 생성 및 매핑
            List<OrderDetailDto> mappedDetails = order.getOrderDetails()
                    .stream()
                    .map(OrderDetailDto::from)
                    .toList();
            orderDetails.addAll(mappedDetails);
            totalPrice += order.getOrderPrice();
        }

        return OrderBriefResponseDto.from(tableNumber, totalPrice, orderDetails);
    }



    /* 주문 상세 조회 */
    public OrderResponseDto<OrderDetailDto> getOrderDetail(Long storeId, Long orderId) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" +orderId;
        findStore(storeId, methodUrl);

        Order order = orderRepository.findOrderByOrderId(orderId);
        List<OrderDetailDto> orderDetail = order.getOrderDetails()
                .stream()
                .map(OrderDetailDto::from)
                .toList();

        return OrderResponseDto.fromWithOrderDetail(order, orderDetail);
    }


    /* 주문 예상 시간 조회 */
    public OrderExpectedPrepTimeResponseDto getOrderExpectedPrepTime(Long storeId, Long orderId) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" +orderId;
        findStore(storeId, methodUrl);

        Order order = orderRepository.findOrderByOrderId(orderId);

        return OrderExpectedPrepTimeResponseDto.from(order);
    }


    /* 주문자의 주문 목록 조회 */
    public OrderBriefResponseDto<UserOrderDto<OrderDetailBriefWithPriceDto>> getUserAllOrder(Long storeId, String sessionToken) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/user";
        findStore(storeId, methodUrl);

        // 해당 세션토큰의 주문 조회
        List<Order> orders = orderRepository.findOrderBySessionToken(storeId, sessionToken);

        // 주문이 없을 경우 안전한 값 반환
        if (orders == null || orders.isEmpty()) {
            return OrderBriefResponseDto.from(null, 0L, Collections.emptyList());
        }

        Long tableNumber = orderRepository.findRecentOrderBySessionToken(sessionToken).getTableNumber();

        // 총액 계산
        Long totalPrice = orders.stream()
                .filter(order -> order.getStatus() != USER_CANCELLED && order.getStatus() != ADMIN_CANCELLED)
                .mapToLong(Order::getOrderPrice)
                .sum();

        // orderList 생성
        List<UserOrderDto<OrderDetailBriefWithPriceDto>> orderList = getUserOrderResponseDtos(orders);

        return OrderBriefResponseDto.from(tableNumber, totalPrice, orderList);
    }

    /*-----------------------------------------------------------*/

    // 가게 존재 검증
    private void findStore(Long storeId, String methodUrl) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, methodUrl));
    }

    // 주문 목록 dto 반환
    private static List<OrderResponseDto<OrderDetailBriefDto>> getOrderResponseDtos(List<Order> orders) {
        List<OrderResponseDto<OrderDetailBriefDto>> orderResponseDtos = new ArrayList<>();  // 주문 목록 리스트

        // 각 주문에 대한 orderDetail 가져와서 orderDetailBriefDto 형식인 orderDetail 생성한 후, 리스트화
        for (Order order : orders) {
            // orderDetails 생성
            List<OrderDetailBriefDto> orderDetails = order.getOrderDetails().stream()
                    .map(OrderDetailBriefDto::from)
                    .toList();

            // 주문 1개
            OrderResponseDto<OrderDetailBriefDto> orderResponseDto = OrderResponseDto.fromWithOrderDetail(order, orderDetails);
            // 주문 1개 리스트에 추가
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }

    // 포장 주문 목록 dto 반환
    private static List<OrderResponseDto<OrderDetailBriefWithPriceDto>> getTakeOutOrderResponseDtos(List<Order> orders) {
        List<OrderResponseDto<OrderDetailBriefWithPriceDto>> orderResponseDtos = new ArrayList<>();  // 주문 목록 리스트

        // 각 주문에 대한 orderDetail 가져와서 orderDetailBriefDto 형식인 orderDetail 생성한 후, 리스트화
        for (Order order : orders) {
            // orderDetails 생성
            List<OrderDetailBriefWithPriceDto> orderDetails = order.getOrderDetails().stream()
                    .map(OrderDetailBriefWithPriceDto::from)
                    .toList();

            // 주문 1개
            OrderResponseDto<OrderDetailBriefWithPriceDto> orderResponseDto = OrderResponseDto.fromWithOrderDetail(order, orderDetails);
            // 주문 1개 리스트에 추가
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }

    // 주문자의 주문 목록 dto 반환
    private static List<UserOrderDto<OrderDetailBriefWithPriceDto>> getUserOrderResponseDtos(List<Order> orders) {
        List<UserOrderDto<OrderDetailBriefWithPriceDto>> orderResponseDtos = new ArrayList<>();  // 주문 목록 리스트

        // 각 주문에 대한 orderDetail 가져와서 orderDetailBriefDto 형식인 orderDetail 생성한 후, 리스트화
        for (Order order : orders) {
            // orderDetails 생성
            List<OrderDetailBriefWithPriceDto> orderDetails = order.getOrderDetails().stream()
                    .map(OrderDetailBriefWithPriceDto::from)
                    .toList();

            // 주문 1개
            UserOrderDto<OrderDetailBriefWithPriceDto> orderResponseDto = UserOrderDto.from(order, orderDetails);
            // 주문 1개 리스트에 추가
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }

}
