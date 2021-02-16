package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.builder.impl.OrderHateoasBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * The type Order controller.
 * The class used to manipulate CRUD operations on Order data.
 */
@RestController
@RequestMapping(value = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderHateoasBuilder orderHateoasBuilder;

    /**
     * Create a new Order.
     *
     * @param orderDTO the orderDTO with information to create
     * @return the created order
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public OrderDTO add(@RequestBody OrderDTO orderDTO) {
        return orderService.add(orderDTO);
    }

    /**
     * Remove a Order by id.
     *
     * @param id the id order to remove
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> remove(@PathVariable("id") long id) {
        orderService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find a OrderDTO by id
     *
     * @param id the id order for search
     * @return the OrderDTO with the requested id
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'USER')")
    public OrderDTO findById(@PathVariable("id") long id) {
        return orderHateoasBuilder.addLinks(orderService.findById(id));
    }

    /**
     * Find all orders.
     *
     * @param params the search parameters
     * @return the list of OrderDTO
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'USER')")
    public RepresentationModel<OrderDTO> findAll(@RequestParam Map<String, String> params) {
        Map<String, String> parameters = new LinkedCaseInsensitiveMap<>();
        parameters.putAll(params);
        List<OrderDTO> orders = orderService.findAll(parameters);
        long count = orderService.defineCount(parameters);
        return orderHateoasBuilder.addLinks(orders, parameters, count);
    }
}
