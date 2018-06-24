package webback.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Action {
    private int actionId;
    private int signInTime;


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
    @Column(name = "actionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    @Basic
    @Column(name = "signInTime")
    public int getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(int signInTime) {
        this.signInTime = signInTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return actionId == action.actionId &&
                signInTime == action.signInTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(actionId, signInTime);
    }
}
