package com.yida.scdgateway.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-12-28 21:24:39
 */
@Data
@Table(name = "user")//如果表名和类名相同可以不写
public class User implements Serializable {
    private static final long serialVersionUID = 182850139560857990L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC") //获取主键自增长id的值，自动注入到当前属性id上
    private Integer id;

    private String name;

    private String password;

    private Integer state;

    private String salt;
   private HashSet<Role> roles;
}

