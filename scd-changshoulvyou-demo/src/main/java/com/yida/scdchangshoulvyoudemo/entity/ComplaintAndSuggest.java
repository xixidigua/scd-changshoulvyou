package com.yida.scdchangshoulvyoudemo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * javabean类
 */
@Table(name="t_complaint_suggest")//类名和数据库里面的表名关联 景区咨询
public class ComplaintAndSuggest {
    @Id  //主键自增长
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//针对mysql,获取插入后,主键的值
    // @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_employee_seq.nextval from dual")//针对oracle,获取插入后,主键的值。前提是：需要在application.properties里配置mapper.before=true
    private  Integer id;
    private String name;//反馈内容
    private  ReadState readState;//阅读状态
    private String  telphone;//联系电话
    private String updateTime;//发布时间
    public ComplaintAndSuggest() {
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

    public ReadState getReadState() {
        return readState;
    }

    public void setReadState(ReadState readState) {
        this.readState = readState;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ComplaintAndSuggest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telphone='" + telphone + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
