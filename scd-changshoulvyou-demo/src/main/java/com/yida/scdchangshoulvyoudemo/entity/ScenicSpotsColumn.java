package com.yida.scdchangshoulvyoudemo.entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * javabean类
 */
@Table(name="t_scenic_spots_column")//类名和数据库里面的表名关联
public class ScenicSpotsColumn implements Serializable {
    private static final long serialVersionUID = 767210323189987204L;
     @Id  //主键自增长
     @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//针对mysql,获取插入后,主键的值
    // @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_employee_seq.nextval from dual")//针对oracle,获取插入后,主键的值。前提是：需要在application.properties里配置mapper.before=true
     private  Integer id;
     private String title;//景点标题
    private  Scenic scenic;//所在景区
    private String playTime;//游玩时长
    private String openTime;//开放时间
    private String trafficMessage;//交通信息
    private String personPrice;//成人门票价格
    private String childrenPrice;//儿童门票价格
    private String  address;//地址
    private String  ticketShow;//门票说明
    private String  mapShowName;//地图显示名称
    private String  longitude;//纬度
    private String  latitude;//经度
    private String  intro;//简介
    private String  fileSourse;//配图照片存放路径
    private String  fileSoursePhonto;//缩略图存放路径
     private String updateTime;//发布时间

    public ScenicSpotsColumn() {
    }

    public ScenicSpotsColumn(String title, Scenic scenic, String playTime, String openTime, String trafficMessage, String personPrice, String childrenPrice, String address, String ticketShow, String mapShowName, String longitude, String latitude, String intro, String fileSourse, String fileSoursePhonto, Date updatetime) {
        this.title = title;
        this.scenic = scenic;
        this.playTime = playTime;
        this.openTime = openTime;
        this.trafficMessage = trafficMessage;
        this.personPrice = personPrice;
        this.childrenPrice = childrenPrice;
        this.address = address;
        this.ticketShow = ticketShow;
        this.mapShowName = mapShowName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.intro = intro;
        this.fileSourse = fileSourse;
        this.fileSoursePhonto = fileSoursePhonto;
        this.updateTime = updateTime;
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

    public Scenic getScenic() {
        return scenic;
    }

    public void setScenic(Scenic scenic) {
        this.scenic = scenic;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getTrafficMessage() {
        return trafficMessage;
    }

    public void setTrafficMessage(String trafficMessage) {
        this.trafficMessage = trafficMessage;
    }

    public String getPersonPrice() {
        return personPrice;
    }

    public void setPersonPrice(String personPrice) {
        this.personPrice = personPrice;
    }

    public String getChildrenPrice() {
        return childrenPrice;
    }

    public void setChildrenPrice(String childrenPrice) {
        this.childrenPrice = childrenPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTicketShow() {
        return ticketShow;
    }

    public void setTicketShow(String ticketShow) {
        this.ticketShow = ticketShow;
    }

    public String getMapShowName() {
        return mapShowName;
    }

    public void setMapShowName(String mapShowName) {
        this.mapShowName = mapShowName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public String getUpdatetime() {
        return updateTime;
    }

    public void setUpdatetime(String updatetime) {
        this.updateTime = updatetime;
    }

    @Override
    public String toString() {
        return "ScenicSpotsColumn{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", playTime='" + playTime + '\'' +
                ", openTime='" + openTime + '\'' +
                ", trafficMessage='" + trafficMessage + '\'' +
                ", personPrice='" + personPrice + '\'' +
                ", childrenPrice='" + childrenPrice + '\'' +
                ", address='" + address + '\'' +
                ", ticketShow='" + ticketShow + '\'' +
                ", mapShowName='" + mapShowName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", intro='" + intro + '\'' +
                ", fileSourse='" + fileSourse + '\'' +
                ", fileSoursePhonto='" + fileSoursePhonto + '\'' +
                ", updatetime=" + updateTime +
                '}';
    }
}
