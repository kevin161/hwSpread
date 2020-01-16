package com.leisurely.spread.model.bean;

public class News {
//    {
//        "visible": 1,
//            "listed": 1,
//            "subject": "老村长叶子888号",
//            "imageUrl": "http://114.67.176.178/hbf/uploadimg//image/1578377373033.jpg",
//            "topic": "PRODUCT",
//            "language": "zh",
//            "id": 85,
//            "type": 1,
//            "publicationDate": 1578018593000,
//            "content": "<img src=\"http://114.67.176.178/hbf/uploadimg//image/1578377429054.jpg\" alt=\"6f8a44bf53dd1434.jpg\">"
//    }
    private String id;
    private String topic;
    private String subject;
    private String language;
    private long publicationDate;
    private String content;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(long publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
