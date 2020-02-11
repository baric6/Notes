package com.gmail.baric0748;

//model object class for firebase
public class ListSubject {

    private String title;
    private String comment;
    private String keyRefrence;
    private String id;
    private String topic;

    public ListSubject(){}

    public ListSubject(String id, String title, String comment, String keyRefrence, String topic)
    {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.keyRefrence = keyRefrence;
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getkeyRefrence() {
        return keyRefrence;
    }

    public void setkeyRefrence(String keyRefrence) {
        this.keyRefrence = keyRefrence;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
