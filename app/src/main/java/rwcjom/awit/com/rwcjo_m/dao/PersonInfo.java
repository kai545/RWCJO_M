package rwcjom.awit.com.rwcjo_m.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table PERSON_INFO.
 */
public class PersonInfo {

    private String userid;
    private String username;
    private String usertel;
    private String ptype;
    private String f_sectid;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public PersonInfo() {
    }

    public PersonInfo(String userid) {
        this.userid = userid;
    }

    public PersonInfo(String userid, String username, String usertel, String ptype, String f_sectid) {
        this.userid = userid;
        this.username = username;
        this.usertel = usertel;
        this.ptype = ptype;
        this.f_sectid = f_sectid;
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

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getF_sectid() {
        return f_sectid;
    }

    public void setF_sectid(String f_sectid) {
        this.f_sectid = f_sectid;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
