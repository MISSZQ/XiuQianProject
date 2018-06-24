package webback.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Userdopraise {
    private int userPraiseId;
    private int doPraise;

    private User userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    private Discuss discussId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discussId")
    public Discuss getDiscussId() {
        return discussId;
    }

    public void setDiscussId(Discuss discussId) {
        this.discussId = discussId;
    }

    @Id
    @Column(name = "userPraiseId", nullable = false)
    public int getUserPraiseId() {
        return userPraiseId;
    }

    public void setUserPraiseId(int userPraiseId) {
        this.userPraiseId = userPraiseId;
    }


    @Basic
    @Column(name = "doPraise", nullable = false)
    public int getDoPraise() {
        return doPraise;
    }

    public void setDoPraise(int doPraise) {
        this.doPraise = doPraise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Userdopraise that = (Userdopraise) o;
        return userPraiseId == that.userPraiseId &&
                doPraise == that.doPraise;
    }

    @Override
    public int hashCode() {

        return Objects.hash(userPraiseId, doPraise);
    }
}
