package com.xuy.spring.jdbc.springjdbcdemo.service;

import com.xuy.spring.jdbc.springjdbcdemo.dao.CUserRepository;
import com.xuy.spring.jdbc.springjdbcdemo.domain.CUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class CUserServiceImpl implements CUserService {

    @Autowired
    private CUserRepository cUserRepository;

//    By default, a transaction will be rolling back on {@link RuntimeException}
//	 * and {@link Error} but not on checked exceptions (business exceptions)
    @Override
    @Transactional
    public int save(CUser cUser) {
        int result = cUserRepository.save(cUser);
//        运行时异常，增加@Transactional事务回滚
        if (result  == 1) {
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public int update(CUser cUser) {
        return cUserRepository.update(cUser);
    }

    @Override
    @Transactional
    public int delete(long id) {
        return cUserRepository.delete(id);
    }

    @Override
    public CUser findById(long id) {
        return cUserRepository.findById(id);
    }
}
