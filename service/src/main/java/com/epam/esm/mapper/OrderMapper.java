package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.model.Order;

import java.util.List;

public interface OrderMapper {
    OrderDTO toDto(Order order);

    List<OrderDTO> toDto(List<Order> orders);
}
