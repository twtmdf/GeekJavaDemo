package io.xuy.dds;

import io.xuy.dds.domain.OrderInfo;
import io.xuy.dds.domain.enums.OrderState;
import io.xuy.dds.offline.service.OrderInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class DynamicDataSourceApplicationTests {

    @Test
    void contextLoads() {
    }
}
