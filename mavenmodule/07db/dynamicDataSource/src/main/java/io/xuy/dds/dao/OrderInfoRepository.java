package io.xuy.dds.dao;

import io.xuy.dds.domain.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderInfoRepository extends JpaRepository<OrderInfo,Long>, JpaSpecificationExecutor<OrderInfo> {
}
