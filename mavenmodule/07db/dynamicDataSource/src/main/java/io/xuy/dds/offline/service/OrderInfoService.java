package io.xuy.dds.offline.service;

import io.xuy.dds.dao.OrderInfoRepository;
import io.xuy.dds.domain.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("offlineOrderInfoService")
public class OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public OrderInfo save(OrderInfo orderInfo) {
        return orderInfoRepository.save(orderInfo);
    }

    public Optional<OrderInfo> findById(Long id) {
        return orderInfoRepository.findById(id);
    }
}
