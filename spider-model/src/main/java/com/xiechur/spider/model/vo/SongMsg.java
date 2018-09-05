package com.xiechur.spider.model.vo;

import com.xiechur.spider.model.Album;
import com.xiechur.spider.model.Singer;

import java.util.List;

public class SongMsg {

    private int publishTime;
    private int fee;
    private int ftype;
    private int no;
    private List<String> alias;
    private Privilege privilege;
    private int djid;
    private Album album;
    private List<Singer> artists;
    private int score;
    private int copyrightId;
    private long mvid;
//    private String transNames;
    private String commentThreadId;
    private int type;
    private long duration;
    private int status;
    private String name;
    private long id;
    public void setPublishTime(int publishTime) {
         this.publishTime = publishTime;
     }
     public int getPublishTime() {
         return publishTime;
     }

    public void setFee(int fee) {
         this.fee = fee;
     }
     public int getFee() {
         return fee;
     }

    public void setFtype(int ftype) {
         this.ftype = ftype;
     }
     public int getFtype() {
         return ftype;
     }

    public void setNo(int no) {
         this.no = no;
     }
     public int getNo() {
         return no;
     }

    public void setAlias(List<String> alias) {
         this.alias = alias;
     }
     public List<String> getAlias() {
         return alias;
     }

    public void setPrivilege(Privilege privilege) {
         this.privilege = privilege;
     }
     public Privilege getPrivilege() {
         return privilege;
     }

    public void setDjid(int djid) {
         this.djid = djid;
     }
     public int getDjid() {
         return djid;
     }

    public void setAlbum(Album album) {
         this.album = album;
     }
     public Album getAlbum() {
         return album;
     }

    public void setArtists(List<Singer> artists) {
         this.artists = artists;
     }
     public List<Singer> getArtists() {
         return artists;
     }

    public void setScore(int score) {
         this.score = score;
     }
     public int getScore() {
         return score;
     }

    public void setCopyrightId(int copyrightId) {
         this.copyrightId = copyrightId;
     }
     public int getCopyrightId() {
         return copyrightId;
     }

    public void setMvid(long mvid) {
         this.mvid = mvid;
     }
     public long getMvid() {
         return mvid;
     }

//    public void setTransNames(String transNames) {
//         this.transNames = transNames;
//     }
//     public String getTransNames() {
//         return transNames;
//     }

    public void setCommentThreadId(String commentThreadId) {
         this.commentThreadId = commentThreadId;
     }
     public String getCommentThreadId() {
         return commentThreadId;
     }

    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setDuration(long duration) {
         this.duration = duration;
     }
     public long getDuration() {
         return duration;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    @Override
    public String toString() {
        return "SongMsg{" +
                "publishTime=" + publishTime +
                ", fee=" + fee +
                ", ftype=" + ftype +
                ", no=" + no +
                ", alias=" + alias +
                ", privilege=" + privilege +
                ", djid=" + djid +
                ", album=" + album +
                ", artists=" + artists +
                ", score=" + score +
                ", copyrightId=" + copyrightId +
                ", mvid=" + mvid +
                ", commentThreadId='" + commentThreadId + '\'' +
                ", type=" + type +
                ", duration=" + duration +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}