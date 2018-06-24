package webback.bean;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Everysignin {
    private int signinId;
    private Date signinDate;
    private int succeedSignin;

    private User userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }


    private Activity activityId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activityId")
    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }

    @Id
    @Column(name = "signinId", nullable = false)
    public int getSigninId() {
        return signinId;
    }

    public void setSigninId(int signinId) {
        this.signinId = signinId;
    }

    @Basic
    @Column(name = "signinDate", nullable = false)
    public Date getSigninDate() {
        return signinDate;
    }

    public void setSigninDate(Date signinDate) {
        this.signinDate = signinDate;
    }

    @Basic
    @Column(name = "succeedSignin", nullable = false)
    public int getSucceedSignin() {
        return succeedSignin;
    }

    public void setSucceedSignin(int succeedSignin) {
        this.succeedSignin = succeedSignin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Everysignin that = (Everysignin) o;
        return signinId == that.signinId &&
                succeedSignin == that.succeedSignin &&
                Objects.equals(signinDate, that.signinDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(signinId, signinDate, succeedSignin);
    }
}
