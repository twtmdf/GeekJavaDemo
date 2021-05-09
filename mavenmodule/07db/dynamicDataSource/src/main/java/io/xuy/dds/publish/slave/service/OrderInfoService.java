package io.xuy.dds.publish.slave.service;

import io.xuy.dds.dao.OrderInfoRepository;
import io.xuy.dds.domain.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("publishSlaveOrderInfoService")
public class OrderInfoService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public Optional<OrderInfo> findById(Long id) {
        return orderInfoRepository.findById(id);
    }
}
