package webback.bean;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
public class Activity {
    private Integer activityId;
    private String activityTitle;
    private String activityIntroduce;
    private String activityImageUrl;
    private Date activityStartTime;
    private Date activityEndTime;
    private Integer activityModel;
    private Integer activityUserNumber;
    private Integer activityAttendNum;
    private Integer activityIsPrivate;

    @Basic
    @Column(name = "activityIsPrivate", nullable = false)
    public Integer getActivityIsPrivate() {
        return activityIsPrivate;
    }

    public void setActivityIsPrivate(Integer activityIsPrivate) {
        this.activityIsPrivate = activityIsPrivate;
    }

    private User activityStartUser;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activityStartUser")
    public User getActivityStartUser() {
        return activityStartUser;
    }

    public void setActivityStartUser(User activityStartUser) {
        this.activityStartUser = activityStartUser;
    }


    private Punish punishId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "punishId")
    public Punish getPunishId() {
        return punishId;
    }

    public void setPunishId(Punish punishId) {
        this.punishId = punishId;
    }

    @Id
    @Column(name = "activityId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @Basic
    @Column(name = "activityTitle", nullable = false, length = 45)
    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    @Basic
    @Column(name = "activityIntroduce", nullable = false, length = 200)
    public String getActivityIntroduce() {
        return activityIntroduce;
    }

    public void setActivityIntroduce(String activityIntroduce) {
        this.activityIntroduce = activityIntroduce;
    }

    @Basic
    @Column(name = "activityImageUrl", nullable = false, length = 45)
    public String getActivityImageUrl() {
        return activityImageUrl;
    }

    public void setActivityImageUrl(String activityImageUrl) {
        this.activityImageUrl = activityImageUrl;
    }

    @Basic
    @Column(name = "activityStartTime", nullable = false)
    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    @Basic
    @Column(name = "activityEndTime", nullable = false)
    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    @Basic
    @Column(name = "activityModel", nullable = false)
    public Integer getActivityModel() {
        return activityModel;
    }

    public void setActivityModel(Integer activityModel) {
        this.activityModel = activityModel;
    }

    @Basic
    @Column(name = "activityUserNumber", nullable = false)
    public Integer getActivityUserNumber() {
        return activityUserNumber;
    }

    public void setActivityUserNumber(Integer activityUserNumber) {
        this.activityUserNumber = activityUserNumber;
    }

    @Basic
    @Column(name = "activityAttendNum", nullable = false)
    public Integer getActivityAttendNum() {
        return activityAttendNum;
    }

    public void setActivityAttendNum(Integer activityAttendNum) {
        this.activityAttendNum = activityAttendNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(activityId, activity.activityId) &&
                Objects.equals(activityTitle, activity.activityTitle) &&
                Objects.equals(activityIntroduce, activity.activityIntroduce) &&
                Objects.equals(activityImageUrl, activity.activityImageUrl) &&
                Objects.equals(activityStartTime, activity.activityStartTime) &&
                Objects.equals(activityEndTime, activity.activityEndTime) &&
                Objects.equals(activityModel, activity.activityModel) &&
                Objects.equals(activityUserNumber, activity.activityUserNumber) &&
                Objects.equals(activityAttendNum, activity.activityAttendNum) &&
                Objects.equals(activityIsPrivate, activity.activityIsPrivate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(activityId, activityTitle, activityIntroduce, activityImageUrl, activityStartTime, activityEndTime, activityModel, activityUserNumber, activityAttendNum,activityIsPrivate);
    }
}

