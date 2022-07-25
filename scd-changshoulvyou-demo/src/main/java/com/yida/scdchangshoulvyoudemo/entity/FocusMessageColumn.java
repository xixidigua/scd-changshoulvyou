package com.yida.scdchangshoulvyoudemo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * javabean类
 */
@Table(name="t_focus_message_column")//类名和数据库里面的表名关联
public class FocusMessageColumn {
    @Id //主键自增长
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//针对mysql,获取插入后,主键的值
   // @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_employee_seq.nextval from dual")//针对oracle,获取插入后,主键的值。前提是：需要在application.properties里配置mapper.before=true
    private  Integer id;
    private String title;//景点标题
    private String  fileSourse;//配图照片存放路径
    private String  fileSoursePhonto;//缩略图存放路径
    private String updateTime;//发布时间
    private Integer logo;//焦点YES/NO
    public FocusMessageColumn() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "FocusMessageColumn{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", fileSourse='" + fileSourse + '\'' +
                ", fileSoursePhonto='" + fileSoursePhonto + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", logo=" + logo +
                '}';
    }
}
