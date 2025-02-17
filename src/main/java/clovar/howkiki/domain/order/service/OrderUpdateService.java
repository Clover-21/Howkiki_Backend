package clovar.howkiki.domain.order.service;

import clovar.howkiki.domain.notification.service.NotificationService;
import clovar.howkiki.domain.order.dto.requestDto.OrderAcceptedRequestDto;
import clovar.howkiki.domain.order.dto.requestDto.OrderCancelRequestDto;
import clovar.howkiki.domain.order.dto.responseDto.*;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import clovar.howkiki.domain.order.repository.OrderRepository;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static clovar.howkiki.domain.order.entity.OrderStatus.*;
import static clovar.howkiki.global.exception.ErrorCode.*;

@Transactional // 모든 작업이 DB 쓰기 연산을 포함하므로 클래스 단위에 적용
@Service
@RequiredArgsConstructor
public class OrderUpdateService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final NotificationService notificationService;

    /* 주문자의 주문 취소 */
    public OrderResponseDto<Void> canceledByUser(Long storeId, Long orderId) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" +orderId + "/user";
        findStore(storeId, methodUrl);

        Order order = orderRepository.findOrderByOrderId(orderId);

        // 검증 - 가게Id가 해당 주문의 가게Id가 맞는지
        checkStoreId(storeId, order, methodUrl);

        // 검증 - 주문 상태가 NOT_YET_SENT 인지 확인
        if(!order.getStatus().equals(NOT_YET_SENT)){
            throw new CustomException(ORDER_CANNOT_BE_CANCELLED, methodUrl);
        }

        order.updateStatus(USER_CANCELLED);

        // @Transactional로 영속성 컨택스트로 관리되므로 save()메서드 생략 가능

        return OrderResponseDto.fromWithoutOrderDetail(order);
    }

    /* 운영자의 주문 취소 */
    public OrderCancelResponseDto canceledByAdmin(Long storeId, Long orderId, OrderCancelRequestDto requestDto) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" +orderId + "/admin";
        findStore(storeId, methodUrl);

        Order order = orderRepository.findOrderByOrderId(orderId);

        // 검증 - 가게Id가 해당 주문의 가게Id가 맞는지
        checkStoreId(storeId, order, methodUrl);

        // 검증 - 주문 상태가 AWAITING_ACCEPTANCE 또는 IN_PROGRESS 인지 확인
        OrderStatus status = order.getStatus();
        if(!(status.equals(AWAITING_ACCEPTANCE) || status.equals(IN_PROGRESS))){
            throw new CustomException(ORDER_CANNOT_BE_CANCELLED, methodUrl);
        }

        order.updateOrderByAdmin(requestDto.getCancelReason(), requestDto.getSoldOutMenu());

        // @Transactional로 영속성 컨택스트로 관리되므로 save()메서드 생략 가능

        // 주문 취소 알림
        String userSessionToken = order.getSessionToken();
        notificationService.sendOrderCanceledByAdmin(order, userSessionToken);

        return OrderCancelResponseDto.from(order);
    }

    /* 주문 상태 변경 */
    public OrderResponseDto<Void> updateOrderStatus(Long storeId, Long orderId, OrderStatus orderStatus) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" +orderId + "/status";
        findStore(storeId, methodUrl);

        Order order = orderRepository.findOrderByOrderId(orderId);

        // 검증 - 가게Id가 해당 주문의 가게Id가 맞는지
        checkStoreId(storeId, order, methodUrl);

        order.updateStatus(orderStatus);

        // @Transactional로 영속성 컨택스트로 관리되므로 save()메서드 생략 가능

        return OrderResponseDto.fromWithoutOrderDetail(order);

    }

    /* 해당 테이블 주문 결제 완료 */
    public TableOrderResponseDto<PaidOrderDetailBriefDto> updateTableOrderStatusPaid(Long storeId, Long tableNumber) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/tables/" +tableNumber + "/status-paid";
        findStore(storeId, methodUrl);

        // 해당 테이블의 주문 조회
        List<Order> orders = orderRepository.findOrderByTableNumber(storeId, tableNumber);

        // orderList 생성
        Long totalPrice = 0L;
        List<PaidOrderDetailBriefDto> orderList = new ArrayList<>();
        for (Order order : orders) {
            order.updateStatus(PAID);
            orderList.add(PaidOrderDetailBriefDto.from(order));
            totalPrice += order.getOrderPrice();
        }

        return TableOrderResponseDto.from(tableNumber, totalPrice, orderList);

    }

    /* 포장 주문 결제 완료 */
    public PaidOrderDetailBriefDto updateTakeOutOrderStatusPaid(Long storeId, Long orderId) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" + orderId + "/take-out/status-paid";
        findStore(storeId, methodUrl);

        // 해당 포장 주문 조회
        Order order = orderRepository.findOrderByOrderId(orderId);
        order.updateStatus(PAID);

        return PaidOrderDetailBriefDto.from(order);
    }

    /* 주문 수락 */
    public OrderExpectedPrepTimeResponseDto acceptOrder(Long storeId, Long orderId, OrderAcceptedRequestDto requestDto) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" +orderId + "/order-acceptance";
        findStore(storeId, methodUrl);

        Order order = orderRepository.findOrderByOrderId(orderId);

        checkStoreId(storeId, order, methodUrl);

        // 검증 - 주문 상태가 AWAITING_ACCEPTANCE 인지 확인
        OrderStatus status = order.getStatus();
        if(!(status.equals(AWAITING_ACCEPTANCE))){
            throw new CustomException(ORDER_STATUS_CANNOT_BE_ACCEPTED, methodUrl);
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Long expectedPrepMin = requestDto.getExpectedPrepMin();
        // 검증 - 잘못된 준비시간 입력
        if(expectedPrepMin <= 0 || expectedPrepMin >= 200){
            throw new CustomException(INVALID_EXPECTED_PREP_MIN, methodUrl);
        }

        LocalDateTime expectedPrepTime = currentTime.plusMinutes(expectedPrepMin);  // 예상 완료 시점 계산

        order.updateStatus(IN_PROGRESS);
        order.updateExpectedPrepTime(expectedPrepTime);

        // @Transactional로 영속성 컨택스트로 관리되므로 save()메서드 생략 가능

        return OrderExpectedPrepTimeResponseDto.from(order);
    }


    /*-----------------------------------------------------------*/

    // 가게 존재 검증
    private void findStore(Long storeId, String methodUrl) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, methodUrl));
    }

    // 가게Id가 해당 주문의 가게Id가 맞는지 검증
    private static void checkStoreId(Long storeId, Order order, String methodUrl) {
        if(!storeId.equals(order.getStore().getStoreId())){
            throw new CustomException(INVALID_STORE_ID, methodUrl);
        }
    }

}
