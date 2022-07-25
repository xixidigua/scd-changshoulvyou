package com.yida.scdgateway.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Permission)实体类
 *
 * @author xixidigua
 * @since 2021-12-28 21:50:52
 */
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 988532942274639438L;

    private Integer id;

    private String name;

    private String url;
}

