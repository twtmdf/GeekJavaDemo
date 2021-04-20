package com.xuy.spring.jdbc.springjdbcdemo;

import com.xuy.spring.jdbc.springjdbcdemo.dao.CUserRepository;
import com.xuy.spring.jdbc.springjdbcdemo.domain.CUser;
import com.xuy.spring.jdbc.springjdbcdemo.service.CUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class SpringjdbcdemoApplicationTests {
    @Autowired
    private CUserRepository cUserRepository;

    @Autowired
    private CUserService cUserService;

	@Test
	void contextLoads() {
	}
    //测试插入数据，直接调用 userRepository 对应的 save 方法
    @Test
    public void testSave() {
        CUser user =CUser.builder().id(1L).name("test1").sex(Boolean.FALSE).build();
        cUserRepository.save(user);
    }

    @Test
    public void testUpdate() {
        CUser user =CUser.builder().id(1l).name("test1").sex(Boolean.TRUE).build();
        int result = cUserRepository.update(user);
        assertEquals(result,1);
    }
    @Test
    public void testFindById() {
        CUser user = cUserRepository.findById(1L);
        assertEquals(user.getId().longValue(),1L);
    }
    @Test
    public void testDelete() {
        CUser user =CUser.builder().id(1l).name("test1").sex(Boolean.TRUE).build();
        int result = cUserRepository.delete(1L);
        assertEquals(result,1);
    }

    @Test
    public void testServiceSave() {
        CUser user =CUser.builder().name("test1").sex(Boolean.FALSE).build();
        cUserService.save(user);
    }

    @Test
    public void testServiceDelete() {
//        cUserService.delete(1);
    }
}
