package clovar.howkiki.domain.order.service;

import clovar.howkiki.domain.order.dto.responseDto.OrderDetailBriefDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderResponseDto;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import clovar.howkiki.domain.order.repository.OrderRepository;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static clovar.howkiki.global.exception.ErrorCode.STORE_ID_NOT_FOUND;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    /* 주문 목록 전체 조회 */
    public List<OrderResponseDto<OrderDetailBriefDto>> getOrderList(Long storeId) {

        // 검증 - 해당 가게 찾기
        storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, "/stores/"+storeId+"/orders"));

        // 해당 가게의 모든 주문 조회
        List<Order> orders = orderRepository.findByStoreId(storeId);

        List<OrderResponseDto<OrderDetailBriefDto>> orderResponseDtos = new ArrayList<>();  // 주문 목록 리스트

        // 각 주문에 대한 orderDetail 가져와서 orderDetailBriefDto 형식인 orderDetail 생성한 후, 리스트화
        for (Order order : orders) {
            // orderDetails 생성
            List<OrderDetailBriefDto> orderDetails = order.getOrderDetails().stream()
                    .map(OrderDetailBriefDto::from)
                    .toList();

            // 주문 1개
            OrderResponseDto<OrderDetailBriefDto> orderResponseDto = OrderResponseDto.from(order, orderDetails);
            // 주문 1개 리스트에 추가
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }

    /* 포장 주문 전체 조회 */
    public List<OrderResponseDto<OrderDetailBriefDto>> getTakeOutOrder(Long storeId) {

        // 검증 - 해당 가게 찾기
        storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, "/stores/"+storeId+"/orders/take-out"));

        // 해당 가게의 포장 주문 조회
        List<Order> orders = orderRepository.findTakeOutOrderByStoreId(storeId);

        List<OrderResponseDto<OrderDetailBriefDto>> orderResponseDtos = new ArrayList<>();  // 주문 목록 리스트

        // 각 주문에 대한 orderDetail 가져와서 orderDetailBriefDto 형식인 orderDetail 생성한 후, 리스트화
        for (Order order : orders) {
            // orderDetails 생성
            List<OrderDetailBriefDto> orderDetails = order.getOrderDetails().stream()
                    .map(OrderDetailBriefDto::from)
                    .toList();

            // 주문 1개
            OrderResponseDto<OrderDetailBriefDto> orderResponseDto = OrderResponseDto.from(order, orderDetails);
            // 주문 1개 리스트에 추가
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }

    /* 테이블 주문 전체 조회 */
    public List<OrderResponseDto<OrderDetailBriefDto>> getTableOrder(Long storeId) {

        // 검증 - 해당 가게 찾기
        storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, "/stores/"+storeId+"/orders/tables/all"));

        // 해당 가게의 테이블 주문 조회
        List<Order> orders = orderRepository.findTableOrderByStoreId(storeId);

        List<OrderResponseDto<OrderDetailBriefDto>> orderResponseDtos = new ArrayList<>();  // 주문 목록 리스트

        // 각 주문에 대한 orderDetail 가져와서 orderDetailBriefDto 형식인 orderDetail 생성한 후, 리스트화
        for (Order order : orders) {
            // orderDetails 생성
            List<OrderDetailBriefDto> orderDetails = order.getOrderDetails().stream()
                    .map(OrderDetailBriefDto::from)
                    .toList();

            // 주문 1개
            OrderResponseDto<OrderDetailBriefDto> orderResponseDto = OrderResponseDto.from(order, orderDetails);
            // 주문 1개 리스트에 추가
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }

    /* 특정 상태의 주문 목록 조회 */
    public List<OrderResponseDto<OrderDetailBriefDto>> getOrderByStatus(Long storeId, OrderStatus status) {

        // 검증 - 해당 가게 찾기
        storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, "/stores/"+storeId+"/orders?status="+status));

        // 해당 가게의 특정 상태의 주문 조회
        List<Order> orders = orderRepository.findOrderByStoreIdAndStatus(storeId, status);

        List<OrderResponseDto<OrderDetailBriefDto>> orderResponseDtos = new ArrayList<>();  // 주문 목록 리스트

        // 각 주문에 대한 orderDetail 가져와서 orderDetailBriefDto 형식인 orderDetail 생성한 후, 리스트화
        for (Order order : orders) {
            // orderDetails 생성
            List<OrderDetailBriefDto> orderDetails = order.getOrderDetails().stream()
                    .map(OrderDetailBriefDto::from)
                    .toList();

            // 주문 1개
            OrderResponseDto<OrderDetailBriefDto> orderResponseDto = OrderResponseDto.from(order, orderDetails);
            // 주문 1개 리스트에 추가
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }



}
