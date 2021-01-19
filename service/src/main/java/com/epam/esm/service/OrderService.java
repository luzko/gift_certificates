package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface Order service.
 * The interface defines certain operations on orders.
 */
public interface OrderService extends BaseService<OrderDTO> {
    /**
     * Find all orders.
     *
     * @param parameters the search parameters
     * @return the list of OrderDTO
     */
    List<OrderDTO> findAll(Map<String, String> parameters);

    /**
     * Define count certificates from DB.
     *
     * @param parameters the search parameters
     * @return the count orders from DB
     */
    long defineCount(Map<String, String> parameters);
}
