package com.yida.scdchangshoulvyoudemo.entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * javabean类
 */
@Table(name = "t_client_classify")
public class Client {
    @Id
    private Integer id;
    private String name;

    public Client() {
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
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
