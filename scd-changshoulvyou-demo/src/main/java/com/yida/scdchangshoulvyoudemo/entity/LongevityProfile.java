package com.yida.scdchangshoulvyoudemo.entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * javabean类
 */
@Table(name="t_longevity_profile")//类名和数据库里面的表名关联
public class LongevityProfile {
     @Id  //主键自增长
     @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//针对mysql,获取插入后,主键的值
    // @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_employee_seq.nextval from dual")//针对oracle,获取插入后,主键的值。前提是：需要在application.properties里配置mapper.before=true
     private  Integer id;
     private String name;//景点标题
    private String  fileSourse;//存放富文本框内容
    private String  fileSoursePhonto;//缩略图存放路径

    public LongevityProfile() {
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

    public String getFileSourse() {
        return fileSourse;
    }

    public void setFileSourse(String fileSourse) {
        this.fileSourse = fileSourse;
    }

    public String getFileSoursePhonto() {
        return fileSoursePhonto;
    }

    public void setFileSoursePhonto(String fileSoursePhonto) {
        this.fileSoursePhonto = fileSoursePhonto;
    }

    @Override
    public String toString() {
        return "LongevityProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fileSourse='" + fileSourse + '\'' +
                ", fileSoursePhonto='" + fileSoursePhonto + '\'' +
                '}';
    }
}
