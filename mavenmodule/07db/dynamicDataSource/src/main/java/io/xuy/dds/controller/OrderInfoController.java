package io.xuy.dds.controller;

import io.xuy.dds.domain.OrderInfo;
import io.xuy.dds.domain.enums.OrderState;
import io.xuy.dds.offline.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order-info")
public class OrderInfoController {
    @Autowired
    private OrderInfoService offlineOrderInfoService;

    @Autowired
    private io.xuy.dds.publish.service.OrderInfoService publishOrderInfoService;

    @Autowired
    private io.xuy.dds.publish.slave.service.OrderInfoService publishSlaveOrderInfoService;

    @Autowired
    private io.xuy.dds.sd.service.OrderInfoService orderInfoService;

    @GetMapping(value = "/from/slave")
    public void getFromSlave() {
        Optional<OrderInfo> orderInfoOp = publishSlaveOrderInfoService.findById(1L);
        orderInfoOp.ifPresent(orderInfo -> log.info(orderInfo.getUserId().toString()));
    }

    @PostMapping(value = "/to/publish")
    public void saveToPublish() {
        Optional<OrderInfo> orderInfoOp = offlineOrderInfoService.findById(1L);
        orderInfoOp.ifPresent(orderInfo -> log.info(orderInfo.getState().toString()));
        OrderInfo orderInfo = orderInfoOp.get();
        orderInfo.setState(OrderState.ORDER_SUCCESS);
        publishOrderInfoService.update(orderInfo);
        log.info(orderInfo.getState().toString());
    }

    @PostMapping(value = "/to/offline")
    public void saveToOffline() {
        OrderInfo orderInfo = OrderInfo.builder()
                .userId(2L)
                .state(OrderState.NEWS)
                .discountAmount(new BigDecimal(1))
                .shippingAmount(new BigDecimal(51))
                .totalAmount(new BigDecimal(50))
                .currency("RMB")
                .userAccountId(1L)
                .paySeq(UUID.randomUUID().toString())
                .build();
        offlineOrderInfoService.save(orderInfo);
    }

    @GetMapping(value = "/from/sd/slave")
    public void getFromSdSlave() {
        Optional<OrderInfo> orderInfoOp = orderInfoService.findById(1L);
        orderInfoOp.ifPresent(orderInfo -> log.info(orderInfo.getUserId().toString()));
    }

    @PostMapping(value = "/to/sd/master")
    public void saveToSdMaster() {
        OrderInfo orderInfo = OrderInfo.builder()
                .userId(3L)
                .state(OrderState.NEWS)
                .discountAmount(new BigDecimal(1))
                .shippingAmount(new BigDecimal(51))
                .totalAmount(new BigDecimal(50))
                .currency("RMB")
                .userAccountId(1L)
                .paySeq(UUID.randomUUID().toString())
                .build();
        orderInfoService.save(orderInfo);
    }
}
