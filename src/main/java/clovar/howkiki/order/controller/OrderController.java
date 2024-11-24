package clovar.howkiki.order.controller;

import clovar.howkiki.global.response.ApiResponse;
import clovar.howkiki.order.dto.OrderResponseDto;
import clovar.howkiki.order.dto.OrderRequestDto;
import clovar.howkiki.order.service.OrderService;
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
                                                     @RequestBody OrderRequestDto requestDto){
        OrderResponseDto responseDto = orderService.createNewOrder(storeId, requestDto);
        ApiResponse<OrderResponseDto> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "주문 생성 성공",
                responseDto
        );
        return response;

    }
}
