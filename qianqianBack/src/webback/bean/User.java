package webback.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    private Integer userId;
    private String userNum;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userImageUrl;
    private String userIntroduce;
    private String userPhone;

    @Id
    @Column(name = "userId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "userNum", nullable = false, length = 10)
    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    @Basic
    @Column(name = "userName", nullable = false, length = 45)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "userPassword", nullable = false, length = 20)
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "userEmail", nullable = true, length = 45)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "userImageUrl", nullable = true, length = 45)
    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    @Basic
    @Column(name = "userIntroduce", nullable = true, length = 45)
    public String getUserIntroduce() {
        return userIntroduce;
    }

    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce;
    }

    @Basic
    @Column(name = "userPhone", nullable = false, length = 45)
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userNum, user.userNum) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(userEmail, user.userEmail) &&
                Objects.equals(userImageUrl, user.userImageUrl) &&
                Objects.equals(userIntroduce, user.userIntroduce) &&
                Objects.equals(userPhone, user.userPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, userNum, userName, userPassword, userEmail, userImageUrl, userIntroduce, userPhone);
    }
}
