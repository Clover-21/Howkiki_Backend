package clovar.howkiki.domain.order.controller;

import clovar.howkiki.domain.order.dto.requestDto.OrderCreateRequestDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderDetailBriefDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderDetailDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderResponseDto;
import clovar.howkiki.domain.order.service.OrderCreateService;
import clovar.howkiki.domain.order.service.OrderQueryService;
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

    /* 주문 생성 */
    @PostMapping
    public ApiResponse<OrderResponseDto<OrderDetailDto>> createOrder(@PathVariable(name = "storeId") Long storeId,
                                                                     @RequestBody OrderCreateRequestDto requestDto){
        OrderResponseDto<OrderDetailDto> responseDto = orderCreateService.createNewOrder(storeId, requestDto);
        ApiResponse<OrderResponseDto<OrderDetailDto>> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "주문 생성 성공",
                responseDto
        );
        return response;

    }

    /* 주문 목록 전체 조회 */
    @GetMapping
    public ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> getOrderList(@PathVariable(name = "storeId") Long storeId){

        List<OrderResponseDto<OrderDetailBriefDto>> responseDto = orderQueryService.getOrderList(storeId);
        ApiResponse<List<OrderResponseDto<OrderDetailBriefDto>>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "주문 목록 전체 조회 성공",
                responseDto
        );
        return response;
    }






}
