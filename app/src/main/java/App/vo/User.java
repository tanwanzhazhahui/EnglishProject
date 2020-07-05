package App.vo;


import java.io.Serializable;


public class User implements Serializable{
    private int userid;
    private String phonenumber;
    private String username;
    private String sex;
    private String email;
    private String avator;

    private String password;//mongo中的密码

    public User() {

    }

    public User(String phonenumber, String password) {
        this.phonenumber = phonenumber;
        this.password = password;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userid != user.userid) return false;
        if (phonenumber != null ? !phonenumber.equals(user.phonenumber) : user.phonenumber != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (avator != null ? !avator.equals(user.avator) : user.avator != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid;
        result = 31 * result + (phonenumber != null ? phonenumber.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (avator != null ? avator.hashCode() : 0);
        return result;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
