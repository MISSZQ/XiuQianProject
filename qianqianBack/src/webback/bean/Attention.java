package webback.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Attention {
    private Integer attentionId;
    private User userId;
    private User attentionuserId;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId",insertable = false,updatable = false)
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attentionuserId")
    public User getAttentionuserId() {
        return attentionuserId;
    }

    public void setAttentionuserId(User attentionuserId) {
        this.attentionuserId = attentionuserId;
    }

    @Id
    @Column(name = "attentionId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getAttentionId() {
        return attentionId;
    }

    public void setAttentionId(Integer attentionId) {
        this.attentionId = attentionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attention attention = (Attention) o;
        return Objects.equals(attentionId, attention.attentionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attentionId);
    }
}
