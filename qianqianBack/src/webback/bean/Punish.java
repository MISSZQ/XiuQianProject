package webback.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PunishTable", schema = "XiuQian")
public class Punish {
    private int punishId;
    private String punishName;
    private String punishIntroduce;

    @Id
    @Column(name = "punishId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getPunishId() {
        return punishId;
    }

    public void setPunishId(int punishId) {
        this.punishId = punishId;
    }

    @Basic
    @Column(name = "punishName")
    public String getPunishName() {
        return punishName;
    }

    public void setPunishName(String punishName) {
        this.punishName = punishName;
    }

    @Basic
    @Column(name = "punishIntroduce")
    public String getPunishIntroduce() {
        return punishIntroduce;
    }

    public void setPunishIntroduce(String punishIntroduce) {
        this.punishIntroduce = punishIntroduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Punish punish = (Punish) o;
        return punishId == punish.punishId &&
                Objects.equals(punishName, punish.punishName) &&
                Objects.equals(punishIntroduce, punish.punishIntroduce);
    }

    @Override
    public int hashCode() {

        return Objects.hash(punishId, punishName, punishIntroduce);
    }
}
