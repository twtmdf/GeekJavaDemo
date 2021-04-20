package com.xuy.spring.jdbc.springjdbcdemo.service;

import com.xuy.spring.jdbc.springjdbcdemo.domain.CUser;

public interface CUserService {
    int save(CUser cUser);
    int update(CUser cUser);
    CUser findById(long id);
    int delete(long id);
}
