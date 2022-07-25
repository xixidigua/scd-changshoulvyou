package com.yida.scdchangshoulvyoudemo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * javabean类
 */
@Table(name="t_video_column")//类名和数据库里面的表名关联
public class VideoColumn {
    @Id //主键自增长
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//针对mysql,获取插入后,主键的值
   // @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_employee_seq.nextval from dual")//针对oracle,获取插入后,主键的值。前提是：需要在application.properties里配置mapper.before=true
    private  Integer id;
    private String title;//景点标题
    private  Classify classify;//所在景区
    private String  videoTotalSize;//文件大小
    private String  fileVideoSourse;//视频存放路径
    private String  fileSoursePhonto;//缩略图存放路径
    private String updateTime;//发布时间
    public VideoColumn() {
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

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public String getVideoTotalSize() {
        return videoTotalSize;
    }

    public void setVideoTotalSize(String videoTotalSize) {
        this.videoTotalSize = videoTotalSize;
    }

    public String getFileVideoSourse() {
        return fileVideoSourse;
    }

    public void setFileVideoSourse(String fileVideoSourse) {
        this.fileVideoSourse = fileVideoSourse;
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
        return "VideoColumn{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", videoTotalSize='" + videoTotalSize + '\'' +
                ", fileVideoSourse='" + fileVideoSourse + '\'' +
                ", fileSoursePhonto='" + fileSoursePhonto + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
