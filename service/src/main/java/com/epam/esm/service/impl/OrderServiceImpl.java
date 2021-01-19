package com.epam.esm.service.impl;

import com.epam.esm.constant.LinkParam;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.GiftCertificateOrderDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.OrderException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.OrderGiftCertificate;
import com.epam.esm.model.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.specification.Specification;
import com.epam.esm.utils.PaginationUtil;
import com.epam.esm.utils.generator.OrderCertificateQueryGenerator;
import com.epam.esm.utils.generator.OrderQueryGenerator;
import com.epam.esm.validation.AppValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Order service.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final GiftCertificateMapper giftCertificateMapper;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final GiftCertificateDAO giftCertificateDAO;
    private final OrderCertificateQueryGenerator orderCertificateQueryGenerator;
    private final OrderQueryGenerator orderQueryGenerator;
    private final AppValidator validator;
    private final PaginationUtil paginationUtil;

    @Override
    @Transactional
    public OrderDTO add(OrderDTO orderDTO) {
        validator.validate(orderDTO);
        User user = userDAO.findById(orderDTO.getUserId());
        List<GiftCertificateOrderDTO> giftCertificateOrderDTOs = orderDTO.getCertificates();
        List<OrderGiftCertificate> orderGiftCertificates = defineOrderGiftCertificates(giftCertificateOrderDTOs);
        Order order = orderDAO.add(fillOrder(user.getId(), orderGiftCertificates));
        orderGiftCertificates.forEach(orderGiftCertificate -> orderGiftCertificate.setOrder(order));
        order.setOrderGiftCertificates(orderDAO.add(orderGiftCertificates));
        return defineOrderDTO(order);
    }

    @Override
    @Transactional
    public void remove(long id) {
        orderDAO.remove(id);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO findById(long id) {
        return defineOrderDTO(orderDAO.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(Map<String, String> parameters) {
        List<Specification> specifications = orderQueryGenerator.generate(parameters);
        int limit = paginationUtil.defineLimit(parameters.get(LinkParam.LIMIT.getValue()));
        int offset = paginationUtil.defineOffset(parameters.get(LinkParam.PAGE.getValue()), limit);
        List<Order> orders = orderDAO.findAll(specifications, offset, limit);
        if (orders.isEmpty()) {
            throw new OrderException(ExceptionType.ORDERS_NOT_FOUND);
        }
        return orders.stream()
                .map(this::defineOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long defineCount(Map<String, String> parameters) {
        List<Specification> specifications = orderQueryGenerator.generate(parameters);
        return orderDAO.defineCount(specifications);
    }

    private List<OrderGiftCertificate> defineOrderGiftCertificates(List<GiftCertificateOrderDTO> certificateOrderDTOs) {
        String query = orderCertificateQueryGenerator.generate(certificateOrderDTOs);
        Map<Long, GiftCertificate> giftCertificateMap = giftCertificateDAO.findById(query);
        validator.validate(certificateOrderDTOs, giftCertificateMap);
        return certificateOrderDTOs.stream()
                .map(giftCertificateOrderDTO -> new OrderGiftCertificate(
                        giftCertificateMap.get(giftCertificateOrderDTO.getId()),
                        giftCertificateOrderDTO.getCount()
                )).collect(Collectors.toList());
    }

    private Order fillOrder(Long userId, List<OrderGiftCertificate> orderGiftCertificates) {
        Order order = new Order();
        order.setUserId(userId);
        order.setCost(defineOrderCost(orderGiftCertificates));
        order.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        return order;
    }

    private BigDecimal defineOrderCost(List<OrderGiftCertificate> orderGiftCertificates) {
        BigDecimal cost = new BigDecimal(0);
        for (OrderGiftCertificate orderGiftCertificate : orderGiftCertificates) {
            cost = cost.add(orderGiftCertificate.getGiftCertificate().getPrice().
                    multiply(BigDecimal.valueOf(orderGiftCertificate.getCount())));
        }
        return cost;
    }

    private OrderDTO defineOrderDTO(Order order) {
        OrderDTO orderDTO = orderMapper.toDto(order);
        orderDTO.setCertificates(giftCertificateMapper.toOrderDto(order.getOrderGiftCertificates()));
        return orderDTO;
    }
}
