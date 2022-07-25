package com.yida.scdchangshoulvyoudemo.entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * javabean类 该类暂时不使用
 */
@Table(name = "s_dept")
public class Dept {
    @Id
    private Integer id;
    private String name;

    public Dept() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
