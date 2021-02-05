package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.GiftCertificateOrderDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.exception.OrderException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.impl.GiftCertificateMapperImpl;
import com.epam.esm.mapper.impl.OrderMapperImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.OrderGiftCertificate;
import com.epam.esm.model.User;
import com.epam.esm.utils.PaginationUtil;
import com.epam.esm.utils.generator.OrderCertificateQueryGenerator;
import com.epam.esm.utils.generator.OrderQueryGenerator;
import com.epam.esm.validation.AppValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.validation.Validation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.time.ZonedDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Spy
    private final OrderCertificateQueryGenerator orderCertificateQueryGenerator = new OrderCertificateQueryGenerator();
    @Spy
    private final OrderQueryGenerator orderQueryGenerator = new OrderQueryGenerator();
    @Spy
    private final OrderMapper orderMapper = new OrderMapperImpl(new ModelMapper());
    @Spy
    private final GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapperImpl(new ModelMapper());
    @Spy
    private final AppValidator validator = new AppValidator(Validation.buildDefaultValidatorFactory().getValidator());
    @Spy
    private final PaginationUtil paginationUtil = new PaginationUtil();
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;

    @Test
    void addPositiveTest() {
        GiftCertificateOrderDTO giftCertificateOrderDTO1 = new GiftCertificateOrderDTO(1L, 2);
        GiftCertificateOrderDTO giftCertificateOrderDTO2 = new GiftCertificateOrderDTO(2L, 2);
        OrderDTO inputOrder = new OrderDTO(1L, Arrays.asList(giftCertificateOrderDTO1, giftCertificateOrderDTO2));
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "best certificate", "new best certificate", BigDecimal.valueOf(10),
                of(LocalDateTime.of(2020, Month.FEBRUARY, 11, 16, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.MARCH, 16, 11, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        OrderGiftCertificate orderGiftCertificate1 = new OrderGiftCertificate(certificate1, 1);
        OrderGiftCertificate orderGiftCertificate2 = new OrderGiftCertificate(certificate2, 2);
        Map<Long, GiftCertificate> certificateMap = new HashMap<>();
        certificateMap.put(certificate1.getId(), certificate1);
        certificateMap.put(certificate2.getId(), certificate2);
        Order order = new Order(1L, 1L, BigDecimal.TEN, null,
                Arrays.asList(orderGiftCertificate1, orderGiftCertificate2));
        OrderDTO expectedOrder = new OrderDTO(
                1L, 1L, BigDecimal.TEN, null, giftCertificateMapper.toOrderDto(
                Arrays.asList(orderGiftCertificate1, orderGiftCertificate2)
        ));
        Mockito.when(giftCertificateDAO.findById(Mockito.anyString())).thenReturn(certificateMap);
        Mockito.when(userDAO.findById(1L)).thenReturn(new User());
        Mockito.when(orderDAO.add(Mockito.any(Order.class))).thenReturn(order);
        Mockito.when(orderDAO.add(Mockito.anyList()))
                .thenReturn(Arrays.asList(orderGiftCertificate1, orderGiftCertificate2));
        OrderDTO actualOrder = orderService.add(inputOrder);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void addNegativeTest() {
        GiftCertificateOrderDTO giftCertificateOrderDTO1 = new GiftCertificateOrderDTO(1L, 2);
        GiftCertificateOrderDTO giftCertificateOrderDTO2 = new GiftCertificateOrderDTO(2L, 2);
        OrderDTO inputOrder = new OrderDTO(1L, Arrays.asList(giftCertificateOrderDTO1, giftCertificateOrderDTO2));
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "best certificate", "new best certificate", BigDecimal.valueOf(10),
                of(LocalDateTime.of(2020, Month.FEBRUARY, 11, 16, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.MARCH, 16, 11, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        OrderGiftCertificate orderGiftCertificate1 = new OrderGiftCertificate(certificate1, 1);
        OrderGiftCertificate orderGiftCertificate2 = new OrderGiftCertificate(certificate1, 2);
        Map<Long, GiftCertificate> certificateMap = new HashMap<>();
        certificateMap.put(certificate1.getId(), certificate1);
        certificateMap.put(certificate2.getId(), certificate2);
        Order order = new Order(1L, 1L, BigDecimal.TEN, null,
                Arrays.asList(orderGiftCertificate1, orderGiftCertificate2));
        OrderDTO expectedOrder = new OrderDTO(
                1L, 1L, BigDecimal.TEN, null, giftCertificateMapper.toOrderDto(
                Collections.singletonList(orderGiftCertificate1)
        ));
        Mockito.when(giftCertificateDAO.findById(Mockito.anyString())).thenReturn(certificateMap);
        Mockito.when(userDAO.findById(1L)).thenReturn(new User());
        Mockito.when(orderDAO.add(Mockito.any(Order.class))).thenReturn(order);
        Mockito.when(orderDAO.add(Mockito.anyList()))
                .thenReturn(Arrays.asList(orderGiftCertificate1, orderGiftCertificate2));
        OrderDTO actualOrder = orderService.add(inputOrder);
        assertNotEquals(expectedOrder, actualOrder);
    }

    @Test
    void addExceptionTest() {
        GiftCertificateOrderDTO giftCertificateOrderDTO1 = new GiftCertificateOrderDTO(1L, 2);
        OrderDTO inputOrder = new OrderDTO(-1L, Collections.singletonList(giftCertificateOrderDTO1));
        assertThrows(OrderException.class, () -> orderService.add(inputOrder));
    }

    @Test
    void removePositiveTest() {
        long inputId = 1;
        Mockito.doNothing().when(orderDAO).remove(Mockito.anyLong());
        orderService.remove(inputId);
        Mockito.verify(orderDAO).remove(inputId);
    }

    @Test
    void removeExceptionTest() {
        long inputId = 1;
        Mockito.doThrow(OrderException.class).when(orderDAO).remove(Mockito.anyLong());
        assertThrows(OrderException.class, () -> orderService.remove(inputId));
    }

    @Test
    void findByIdPositiveTest() {
        long inputId = 1;
        Order order = new Order(
                1L, 1L, BigDecimal.TEN,
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                new ArrayList<>()
        );
        OrderDTO expectedOrder = orderMapper.toDto(order);
        Mockito.when(orderDAO.findById(Mockito.anyLong())).thenReturn(order);
        OrderDTO actualOrder = orderService.findById(inputId);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void findByIdNegativeTest() {
        long inputId = 2;
        Order order1 = new Order(
                1L, 1L, BigDecimal.TEN,
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                new ArrayList<>()
        );
        Order order2 = new Order(
                2L, 2L, BigDecimal.ONE,
                of(LocalDateTime.of(2021, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                new ArrayList<>()
        );
        OrderDTO expectedOrder = orderMapper.toDto(order1);
        Mockito.when(orderDAO.findById(Mockito.anyLong())).thenReturn(order2);
        OrderDTO actualOrder = orderService.findById(inputId);
        assertNotEquals(expectedOrder, actualOrder);
    }

    @Test
    void findByIdExceptionTest() {
        long inputId = 1;
        Mockito.doThrow(OrderException.class).when(orderDAO).findById(Mockito.anyLong());
        assertThrows(OrderException.class, () -> orderService.findById(inputId));
    }

    @Test
    void findAllPositiveTest() {
        Order order1 = new Order(
                1L, 1L, BigDecimal.TEN,
                of(LocalDateTime.of(2020, Month.JANUARY, 5, 4, 0), ZoneId.systemDefault()),
                new ArrayList<>()
        );
        Order order2 = new Order(
                2L, 2L, BigDecimal.ONE,
                of(LocalDateTime.of(2021, Month.FEBRUARY, 16, 14, 0), ZoneId.systemDefault()),
                new ArrayList<>()
        );
        List<Order> orders = Arrays.asList(order1, order2);
        OrderDTO expectedOrder1 = orderMapper.toDto(order1);
        OrderDTO expectedOrder2 = orderMapper.toDto(order2);
        List<OrderDTO> expectedOrders = Arrays.asList(expectedOrder1, expectedOrder2);
        Mockito.when(orderDAO.findAll(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(orders);
        List<OrderDTO> actualOrders = orderService.findAll(new HashMap<>());
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void findAllNegativeTest() {
        Order order1 = new Order(
                1L, 1L, BigDecimal.TEN,
                of(LocalDateTime.of(2020, Month.JANUARY, 5, 4, 0), ZoneId.systemDefault()),
                new ArrayList<>()
        );
        Order order2 = new Order(
                2L, 2L, BigDecimal.ONE,
                of(LocalDateTime.of(2021, Month.FEBRUARY, 16, 14, 0), ZoneId.systemDefault()),
                new ArrayList<>()
        );
        List<Order> orders = Collections.singletonList(order1);
        OrderDTO expectedOrder1 = orderMapper.toDto(order1);
        OrderDTO expectedOrder2 = orderMapper.toDto(order2);
        List<OrderDTO> expectedOrders = Arrays.asList(expectedOrder1, expectedOrder2);
        Mockito.when(orderDAO.findAll(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(orders);
        List<OrderDTO> actualOrders = orderService.findAll(new HashMap<>());
        assertNotEquals(expectedOrders, actualOrders);
    }

    @Test
    void findAllExceptionTest() {
        Mockito.when(orderDAO.findAll(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());
        assertThrows(OrderException.class, () -> orderService.findAll(new HashMap<>()));
    }

    @Test
    void defineCountPositiveTest() {
        long expected = 5L;
        Mockito.when(orderDAO.defineCount(Mockito.anyList())).thenReturn(expected);
        long actual = orderService.defineCount(new HashMap<>());
        assertEquals(expected, actual);
    }

    @Test
    void defineCountNegativeTest() {
        long expected = 5L;
        Mockito.when(orderDAO.defineCount(Mockito.anyList())).thenReturn(0L);
        long actual = orderService.defineCount(new HashMap<>());
        assertNotEquals(expected, actual);
    }
}
