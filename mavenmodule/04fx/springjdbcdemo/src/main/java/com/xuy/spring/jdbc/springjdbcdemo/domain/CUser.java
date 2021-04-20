package com.xuy.spring.jdbc.springjdbcdemo.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CUser {
    private Long id;
    private String name;
    private Boolean sex;
    private LocalDateTime createtime;
    private LocalDateTime modifytime;
}
