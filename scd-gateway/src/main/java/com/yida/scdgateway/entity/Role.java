package com.yida.scdgateway.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;

/**
 * (Role)实体类
 *
 * @author xixidigua
 * @since 2021-12-28 21:48:36
 */
@Data
@Table(name = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = 111599028208946900L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Integer id;

    private String name;
    private HashSet<Permission> permissions;
}

