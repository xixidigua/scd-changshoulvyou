package com.yida.scdchangshoulvyoudemo.entity;

import javax.persistence.Table;

/**
 * javabean类
 */
@Table(name="t_read_state")//类名和数据库里面的表名关联
public class ReadState {
    private  Integer id;
    private  String name;

    public ReadState() {
    }

    public ReadState(Integer id, String name) {
        this.id = id;
        this.name = name;
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
        return "ReadState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
