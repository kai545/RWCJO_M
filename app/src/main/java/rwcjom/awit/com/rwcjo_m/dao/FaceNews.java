package rwcjom.awit.com.rwcjo_m.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table FACE_NEWS.
 */
public class FaceNews {

    private String faceId;
    private String faceCode;
    private String faceName;
    private String f_siteid;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public FaceNews() {
    }

    public FaceNews(String faceId) {
        this.faceId = faceId;
    }

    public FaceNews(String faceId, String faceCode, String faceName, String f_siteid) {
        this.faceId = faceId;
        this.faceCode = faceCode;
        this.faceName = faceName;
        this.f_siteid = f_siteid;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getFaceCode() {
        return faceCode;
    }

    public void setFaceCode(String faceCode) {
        this.faceCode = faceCode;
    }

    public String getFaceName() {
        return faceName;
    }

    public void setFaceName(String faceName) {
        this.faceName = faceName;
    }

    public String getF_siteid() {
        return f_siteid;
    }

    public void setF_siteid(String f_siteid) {
        this.f_siteid = f_siteid;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
