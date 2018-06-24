package webback.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AdminS {
    private int adminId;
    private String adminName;
    private String adminPassword;

    @Id
    @Column(name = "adminId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "adminName")
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Basic
    @Column(name = "adminPassword")
    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminS adminS = (AdminS) o;
        return adminId == adminS.adminId &&
                Objects.equals(adminName, adminS.adminName) &&
                Objects.equals(adminPassword, adminS.adminPassword);
    }

    @Override
    public int hashCode() {

        return Objects.hash(adminId, adminName, adminPassword);
    }
}
