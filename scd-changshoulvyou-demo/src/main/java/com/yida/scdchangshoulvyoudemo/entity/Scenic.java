package com.yida.scdchangshoulvyoudemo.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * javabean类
 */
@Table(name="t_scenic")//类名和数据库里面的表名关联
public class Scenic implements Serializable {
    private static final long serialVersionUID = 794097225021993516L;
    private  Integer id;
    private  String name;

    public Scenic() {
    }

    public Scenic(Integer id, String name) {
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
        return "Scenic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
