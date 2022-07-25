package com.yida.scdchangshoulvyoudemo.entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * javabean类
 */
@Table(name = "t_classify")
public class Classify {
    @Id
    private Integer id;
    private String name;

    public Classify() {
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
        return "Classify{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
