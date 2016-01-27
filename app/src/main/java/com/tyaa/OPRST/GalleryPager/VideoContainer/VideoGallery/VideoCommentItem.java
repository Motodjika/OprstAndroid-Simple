package com.tyaa.OPRST.GalleryPager.VideoContainer.VideoGallery;

/**
 * Created by Павел on 10/11/2015.
 */
public class VideoCommentItem {
    public static final String TAG = "VideoCommentItem";

    private static String id;
    private static String youtube;
    private static String preview;

    public static String getPreview() {
        return preview;
    }

    public static void setPreview(String preview) {
        VideoCommentItem.preview = preview;
    }
    public static String getYoutube() {
        return youtube;
    }

    public static void setYoutube(String youtube) {
        VideoCommentItem.youtube = youtube;
    }
    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        VideoCommentItem.id = id;
    }

    //имя отправителя
    private String name;//jot-name
    //дата комментария
    private String date;//jot-date
    //тем комментария
    private String subject;//jot-subject
    //текст
    private String comment;//jot-message

    public String getUrlUp() {
        return urlUp;
    }

    public void setUrlUp(String urlUp) {
        this.urlUp = urlUp;
    }

    public String getUrlDown() {
        return urlDown;
    }

    public void setUrlDown(String urlDown) {
        this.urlDown = urlDown;
    }

    //ссылка на + к комменту
    private String urlUp;//jot-btn jot-btn-up href
    //ссылка на - к комменту
    private String urlDown;//jot-btn jot-btn-down href
    //рейтинг
    private String rating;//jot-btn jot-rating jot-p


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
