package com.epam.esm.mapper.impl;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {
    private final ModelMapper modelMapper;

    @Override
    public OrderDTO toDto(Order order) {
        return Objects.isNull(order) ? null : modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> toDto(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
