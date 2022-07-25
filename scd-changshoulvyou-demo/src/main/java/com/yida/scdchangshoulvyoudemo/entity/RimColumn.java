package com.yida.scdchangshoulvyoudemo.entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * javabean类
 */
@Table(name="t_rim_column")//类名和数据库里面的表名关联
public class RimColumn {
     @Id  //主键自增长
     @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//针对mysql,获取插入后,主键的值
    // @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_employee_seq.nextval from dual")//针对oracle,获取插入后,主键的值。前提是：需要在application.properties里配置mapper.before=true
     private  Integer id;
     private String title;//景点标题
    private  ConsumptionClassify consumptionClassify;//所属类型
    private String abodeClassify;//住所类型
    private String trafficMessage;//交通信息
    private  String consultPrice;//参考价格
    private  String recommendGreens;//推荐菜
    private String  address;//地址
    private String  mapShowName;//地图显示名称
    private String  longitude;//纬度
    private String  latitude;//经度
    private String  telphone;//联系电话1
    private String  intro;//简介
    private String  fileSourse;//配图照片存放路径
    private String  fileSoursePhonto;//缩略图存放路径
     private String updateTime;//发布时间

    public RimColumn() {
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

    public ConsumptionClassify getConsumptionClassify() {
        return consumptionClassify;
    }

    public void setConsumptionClassify(ConsumptionClassify consumptionClassify) {
        this.consumptionClassify = consumptionClassify;
    }

    public String getAbodeClassify() {
        return abodeClassify;
    }

    public void setAbodeClassify(String abodeClassify) {
        this.abodeClassify = abodeClassify;
    }

    public String getTrafficMessage() {
        return trafficMessage;
    }

    public void setTrafficMessage(String trafficMessage) {
        this.trafficMessage = trafficMessage;
    }

    public String getConsultPrice() {
        return consultPrice;
    }

    public void setConsultPrice(String consultPrice) {
        this.consultPrice = consultPrice;
    }

    public String getRecommendGreens() {
        return recommendGreens;
    }

    public void setRecommendGreens(String recommendGreens) {
        this.recommendGreens = recommendGreens;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "RimColumn{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", abodeClassify='" + abodeClassify + '\'' +
                ", trafficMessage='" + trafficMessage + '\'' +
                ", consultPrice='" + consultPrice + '\'' +
                ", recommendGreens='" + recommendGreens + '\'' +
                ", address='" + address + '\'' +
                ", mapShowName='" + mapShowName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", telphone='" + telphone + '\'' +
                ", intro='" + intro + '\'' +
                ", fileSourse='" + fileSourse + '\'' +
                ", fileSoursePhonto='" + fileSoursePhonto + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
