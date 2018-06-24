package webback.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Blacklist {
    private int blacklistId;


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
    @Column(name = "blacklistId", nullable = false)
    public int getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(int blacklistId) {
        this.blacklistId = blacklistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blacklist that=(Blacklist) o;

        return blacklistId == that.blacklistId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(blacklistId);
    }
}
