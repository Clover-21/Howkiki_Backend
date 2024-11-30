package clovar.howkiki.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderListResponseDto {
    private final List<OrderListDetailResponseDto> orders;

    public OrderListResponseDto(List<OrderListDetailResponseDto> orders) {
        this.orders = orders;
    }
}
