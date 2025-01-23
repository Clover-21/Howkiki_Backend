package clovar.howkiki.domain.order.controller;

import clovar.howkiki.domain.order.dto.requestDto.OrderCreateRequestDto;
import clovar.howkiki.domain.order.dto.responseDto.OrderResponseDto;
import clovar.howkiki.domain.order.service.OrderService;
import clovar.howkiki.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/orders")
public class OrderController {

    private final OrderService orderService;

    /* 주문 생성 */
    @PostMapping
    public ApiResponse<OrderResponseDto> createOrder(@PathVariable(name = "storeId") Long storeId,
                                                     @RequestBody OrderCreateRequestDto requestDto){
        OrderResponseDto responseDto = orderService.createNewOrder(storeId, requestDto);
        ApiResponse<OrderResponseDto> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "주문 생성 성공",
                responseDto
        );
        return response;

    }


}
