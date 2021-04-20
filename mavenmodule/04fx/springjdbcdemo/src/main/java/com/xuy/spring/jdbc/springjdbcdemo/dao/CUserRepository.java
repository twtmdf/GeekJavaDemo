package com.xuy.spring.jdbc.springjdbcdemo.dao;

import com.xuy.spring.jdbc.springjdbcdemo.domain.CUser;

public interface CUserRepository {
    int save(CUser cUser);
    int update(CUser cUser);
    CUser findById(long id);
    int delete(long id);
}
