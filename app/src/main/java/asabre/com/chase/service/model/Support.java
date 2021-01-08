package asabre.com.chase.service.model;

import com.google.gson.annotations.SerializedName;

public class Support {
    @SerializedName("_id")
    private String _id;
    @SerializedName("topic")
    private String topic;
    @SerializedName("author")
    private String author;
    @SerializedName("msg")
    private String msg;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public Support() {
    }

    public Support(String _id,
                   String topic,
                   String author,
                   String msg,
                   String createdAt,
                   String updatedAt) {
        this._id = _id;
        this.topic = topic;
        this.author = author;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return "Support{" +
                "_id='" + _id + '\'' +
                ", topic='" + topic + '\'' +
                ", author='" + author + '\'' +
                ", msg='" + msg + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
