package com.xuy.spring.jdbc.springjdbcdemo.dao;

import com.xuy.spring.jdbc.springjdbcdemo.domain.CUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class CUserRepositoryImpl implements CUserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(CUser cUser) {
        return jdbcTemplate.update("INSERT INTO cuser(name,  sex) values(?, ?)",
                cUser.getName(), cUser.getSex());
    }

    @Override
    public int update(CUser cUser) {
        return jdbcTemplate.update("UPDATE cuser SET name = ? , sex = ? WHERE id=?",
                cUser.getName(),cUser.getSex(), cUser.getId());
    }

    @Override
    public int delete(long id) {
        return jdbcTemplate.update("DELETE FROM cuser where id = ? ",id);
    }

    @Override
    public CUser findById(long id) {
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * FROM cuser WHERE id=?", new Object[] { id });
        return CUser.builder()
                .id(Long.valueOf((Integer)result.get("id")))
                .name((String)result.get("name"))
                .sex((Boolean) result.get("sex"))
                .build();
    }
}
