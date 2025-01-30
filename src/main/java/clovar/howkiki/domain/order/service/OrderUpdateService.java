package clovar.howkiki.domain.order.service;

import clovar.howkiki.domain.order.dto.requestDto.OrderCancelRequestDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderCancelResponseDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderResponseDto;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import clovar.howkiki.domain.order.repository.OrderRepository;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static clovar.howkiki.domain.order.entity.OrderStatus.*;
import static clovar.howkiki.global.exception.ErrorCode.*;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderUpdateService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    /* 주문자의 주문 취소 */
    public OrderResponseDto<Void> canceledByUser(Long storeId, Long orderId) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" +orderId + "/user";
        findStore(storeId, methodUrl);

        Order order = orderRepository.findOrderByOrderId(orderId);

        // 검증 - 가게Id가 해당 주문의 가게Id가 맞는지
        if(!storeId.equals(order.getStore().getStoreId())){
            throw new CustomException(INVALID_STORE_ID, methodUrl);
        }

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
        if(!storeId.equals(order.getStore().getStoreId())){
            throw new CustomException(INVALID_STORE_ID, methodUrl);
        }

        // 검증 - 주문 상태가 AWAITING_ACCEPTANCE 또는 IN_PROGRESS 인지 확인
        OrderStatus status = order.getStatus();
        if(!(status.equals(AWAITING_ACCEPTANCE) || status.equals(IN_PROGRESS))){
            throw new CustomException(ORDER_CANNOT_BE_CANCELLED, methodUrl);
        }

        order.updateOrderByAdmin(requestDto.getCancelReason(), requestDto.getSoldOutMenu());

        // @Transactional로 영속성 컨택스트로 관리되므로 save()메서드 생략 가능

        return OrderCancelResponseDto.from(order);
    }

    /* 주문 상태 변경 */
    public OrderResponseDto<Void> updateOrderStatus(Long storeId, Long orderId, OrderStatus orderStatus) {

        // 검증 - 해당 가게 찾기
        String methodUrl = "/stores/"+ storeId +"/orders/" +orderId + "/user";
        findStore(storeId, methodUrl);

        Order order = orderRepository.findOrderByOrderId(orderId);

        // 검증 - 가게Id가 해당 주문의 가게Id가 맞는지
        if(!storeId.equals(order.getStore().getStoreId())){
            throw new CustomException(INVALID_STORE_ID, methodUrl);
        }

        order.updateStatus(orderStatus);

        // @Transactional로 영속성 컨택스트로 관리되므로 save()메서드 생략 가능

        return OrderResponseDto.fromWithoutOrderDetail(order);

    }

    /*-----------------------------------------------------------*/

    // 가게 존재 검증
    private void findStore(Long storeId, String methodUrl) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, methodUrl));
    }


}
