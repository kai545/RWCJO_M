package rwcjom.awit.com.rwcjo_m.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PERSON_INFO.
 */
public class PersonInfo {

    private String userid;
    private String username;
    private String usertel;

    public PersonInfo() {
    }

    public PersonInfo(String userid) {
        this.userid = userid;
    }

    public PersonInfo(String userid, String username, String usertel) {
        this.userid = userid;
        this.username = username;
        this.usertel = usertel;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertel() {
        return usertel;
    }

    public void setUsertel(String usertel) {
        this.usertel = usertel;
    }

}