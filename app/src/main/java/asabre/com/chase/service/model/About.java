package asabre.com.chase.service.model;

import com.google.gson.annotations.SerializedName;

public class About {

    @SerializedName("_id")
    private String _id;
    @SerializedName("title")
    private String title;
    @SerializedName("msg")
    private String msg;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public About() {
    }

    public About(String _id, String title, String msg, String createdAt, String updatedAt) {
        this._id = _id;
        this.title = title;
        this.msg = msg;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "About{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
