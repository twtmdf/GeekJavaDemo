package io.xuy.dds.publish.service;

import io.xuy.dds.dao.OrderInfoRepository;
import io.xuy.dds.domain.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publishOrderInfoService")
public class OrderInfoService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public OrderInfo update(OrderInfo orderInfo) {
        return orderInfoRepository.save(orderInfo);
    }
}
