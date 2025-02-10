package clovar.howkiki.domain.order.controller;

import clovar.howkiki.domain.order.dto.requestDto.OrderAcceptedRequestDto;
import clovar.howkiki.domain.order.dto.requestDto.OrderCancelRequestDto;
import clovar.howkiki.domain.order.dto.requestDto.OrderCreateRequestDto;
import clovar.howkiki.domain.order.dto.responseDto.*;
import clovar.howkiki.domain.order.entity.OrderStatus;
import clovar.howkiki.domain.order.service.OrderCreateService;
import clovar.howkiki.domain.order.service.OrderQueryService;
import clovar.howkiki.domain.order.service.OrderUpdateService;
import clovar.howkiki.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/orders")
public class OrderController {

    private final OrderCreateService orderCreateService;
    private final OrderQueryService orderQueryService;
    private final OrderUpdateService orderUpdateService;

    /* 주문 생성 */
    @PostMapping
    public ApiResponse<OrderResponseDto<OrderDetailDto>> createOrder(@RequestHeader(name = "sessionToken")String sessionToken,
                                                                     @PathVariable(name = "storeId") Long storeId,
                                                                     @RequestBody OrderCreateRequestDto requestDto){
        OrderResponseDto<OrderDetailDto> responseDto = orderCreateService.createNewOrder(storeId, sessionToken, requestDto);
        ApiResponse<OrderResponseDto<OrderDetailDto>> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "주문 생성 성공",
                responseDto
        );
        return response;

    }

    /* ------------------------------------------------------------------ */

    /* 주문 목록 전체 조회 */
    @GetMapping("/all")
    public ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> getOrderList(@PathVariable(name = "storeId") Long storeId){

        List<OrderResponseDto<OrderDetailBriefDto>> responseDto = orderQueryService.getOrderList(storeId);
        ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "주문 목록 전체 조회 성공",
                responseDto
        );
        return response;
    }

    /* 포장 주문 전체 조회 */
    @GetMapping("/take-out")
    public ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> getTakeOutOrder(@PathVariable(name = "storeId") Long storeId){

        List<OrderResponseDto<OrderDetailBriefDto>> responseDto = orderQueryService.getTakeOutOrder(storeId);
        ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "포장 주문 목록 전체 조회 성공",
                responseDto
        );
        return response;
    }

    /* 테이블 주문 전체 조회 */
    @GetMapping("/tables/all")
    public ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> getTableOrderList(@PathVariable(name = "storeId") Long storeId){

        List<OrderResponseDto<OrderDetailBriefDto>> responseDto = orderQueryService.getTableOrderList(storeId);
        ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "테이블 주문 목록 전체 조회 성공",
                responseDto
        );
        return response;
    }

    /* 특정 상태의 주문 조회 */
    @GetMapping()
    public ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> getOrderByStatus(@PathVariable(name = "storeId") Long storeId,
                                                                                     @RequestParam OrderStatus status){
        List<OrderResponseDto<OrderDetailBriefDto>> responseDto = orderQueryService.getOrderByStatus(storeId, status);
        ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "특정 주문 상태의 주문 목록 조회 성공",
                responseDto
        );
        return response;
    }

    /* 해당 테이블 주문 목록 조회 */
    @GetMapping("/tables/{tableNumber}")
    public ApiResponse<TableOrderResponseDto<OrderDetailDto>> getTableOrder(@PathVariable(name = "storeId") Long storeId,
                                                            @PathVariable(name = "tableNumber") Long tableNumber){
        TableOrderResponseDto<OrderDetailDto> responseDto = orderQueryService.getTableOrder(storeId, tableNumber);
        ApiResponse<TableOrderResponseDto<OrderDetailDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "해당 테이블 주문 목록 조회 성공",
                responseDto
        );
        return response;
    }

    /* 주문 상세 조회 */
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponseDto<OrderDetailDto>> getOrderDetail(@PathVariable(name = "storeId") Long storeId,
                                                                        @PathVariable(name = "orderId") Long orderId){
        OrderResponseDto<OrderDetailDto> responseDto = orderQueryService.getOrderDetail(storeId, orderId);
        ApiResponse<OrderResponseDto<OrderDetailDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "주문 상세 조회 성공",
                responseDto
        );
        return response;
    }

    /* 주문 예상 시간 조회 */
    @GetMapping("/{orderId}/expectedPrepTime")
    public ApiResponse<OrderExpectedPrepTimeResponseDto> getOrderExpectedPrepTime(@PathVariable(name = "storeId") Long storeId,
                                                                                  @PathVariable(name = "orderId") Long orderId){
        OrderExpectedPrepTimeResponseDto responseDto = orderQueryService.getOrderExpectedPrepTime(storeId, orderId);
        ApiResponse<OrderExpectedPrepTimeResponseDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "주문 예상시간 조회 성공",
                responseDto
        );
        return response;
    }

    /*--------------------------------------------------------*/

    /* 주문자의 주문 쥐소 */
    @PatchMapping("/{orderId}/user")
    public ApiResponse<OrderResponseDto<Void>> canceledByUser(@PathVariable(name = "storeId") Long storeId,
                                                      @PathVariable(name = "orderId") Long orderId){
        OrderResponseDto<Void> responseDto = orderUpdateService.canceledByUser(storeId, orderId);
        ApiResponse<OrderResponseDto<Void>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "주문자의 주문 취소 성공",
                responseDto
        );
        return response;
    }

    /* 운영자의 주문 쥐소 */
    @PatchMapping("/{orderId}/admin")
    public ApiResponse<OrderCancelResponseDto> canceledByAdmin(@PathVariable(name = "storeId") Long storeId,
                                                              @PathVariable(name = "orderId") Long orderId,
                                                               @RequestBody OrderCancelRequestDto requestDto){
        OrderCancelResponseDto responseDto = orderUpdateService.canceledByAdmin(storeId, orderId, requestDto);
        ApiResponse<OrderCancelResponseDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "운영자의 주문 취소 성공",
                responseDto
        );
        return response;
    }

    /* 주문 상태 변경 */
    @PatchMapping("/{orderId}/status")
    public ApiResponse<OrderResponseDto<Void>> updateOrderStatus(@PathVariable(name = "storeId") Long storeId,
                                                                 @PathVariable(name = "orderId") Long orderId,
                                                                 @RequestParam OrderStatus orderStatus){
        OrderResponseDto<Void> responseDto = orderUpdateService.updateOrderStatus(storeId, orderId, orderStatus);
        ApiResponse<OrderResponseDto<Void>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "주문 상태 변경 성공",
                responseDto
        );
        return response;
    }

    /* 해당 테이블의 주문 결제 완료  */
    @PatchMapping("/tables/{tableNumber}/status-paid")
    public ApiResponse<TableOrderResponseDto<TableOrderDetailBriefDto>> updateTableOrderStatusPaid(@PathVariable(name = "storeId") Long storeId,
                                                                         @PathVariable(name = "tableNumber") Long tableNumber){
        TableOrderResponseDto<TableOrderDetailBriefDto> responseDto = orderUpdateService.updateTableOrderStatusPaid(storeId, tableNumber);
        ApiResponse<TableOrderResponseDto<TableOrderDetailBriefDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "해당 테이블의 주문 결제 완료로 변경 성공",
                responseDto
        );
        return response;
    }

    /* 주문 수락 */
    @PatchMapping("/{orderId}/order-acceptance")
    public ApiResponse<OrderExpectedPrepTimeResponseDto> acceptOrder(@PathVariable(name = "storeId") Long storeId,
                                                           @PathVariable(name = "orderId") Long orderId,
                                                           @RequestBody OrderAcceptedRequestDto requestDto){
        OrderExpectedPrepTimeResponseDto responseDto = orderUpdateService.acceptOrder(storeId, orderId, requestDto);
        ApiResponse<OrderExpectedPrepTimeResponseDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "주문 수락 성공",
                responseDto
        );
        return response;
    }

}
