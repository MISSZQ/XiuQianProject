package webback.bean;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Discuss {
    private int discussId;
    private String discussIntroduce;
    private String discussImageUrl;
    private int discussPraise;
    private Date discussTime;

    private Action actionId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "actionId")
    public Action getActionId() {
        return actionId;
    }

    public void setActionId(Action actionId) {
        this.actionId = actionId;
    }

    @Id
    @Column(name = "discussId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getDiscussId() {
        return discussId;
    }

    public void setDiscussId(int discussId) {
        this.discussId = discussId;
    }

    @Basic
    @Column(name = "discussIntroduce")
    public String getDiscussIntroduce() {
        return discussIntroduce;
    }

    public void setDiscussIntroduce(String discussIntroduce) {
        this.discussIntroduce = discussIntroduce;
    }

    @Basic
    @Column(name = "discussImageUrl")
    public String getDiscussImageUrl() {
        return discussImageUrl;
    }

    public void setDiscussImageUrl(String discussImageUrl) {
        this.discussImageUrl = discussImageUrl;
    }

    @Basic
    @Column(name = "discussPraise")
    public int getDiscussPraise() {
        return discussPraise;
    }

    public void setDiscussPraise(int discussPraise) {
        this.discussPraise = discussPraise;
    }

    @Basic
    @Column(name = "discussTime")
    public Date getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(Date discussTime) {
        this.discussTime = discussTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discuss discuss = (Discuss) o;
        return discussId == discuss.discussId &&
                discussPraise == discuss.discussPraise &&
                Objects.equals(discussIntroduce, discuss.discussIntroduce) &&
                Objects.equals(discussImageUrl, discuss.discussImageUrl) &&
                Objects.equals(discussTime, discuss.discussTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(discussId, discussIntroduce, discussImageUrl, discussPraise, discussTime);
    }
}
