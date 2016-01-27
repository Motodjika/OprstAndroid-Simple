package com.tyaa.OPRST.DataService;

import android.net.Uri;
import android.util.Log;

import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoContainerItem;
import com.tyaa.OPRST.GalleryPager.PhotoContainer.PhotoGallery.PhotoGalleryItem;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoContainerItem;
import com.tyaa.OPRST.GalleryPager.VideoContainer.VideoGallery.VideoCommentItem;
import com.tyaa.OPRST.TitleFragments.Projects.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;


public class SiteConnector {
    public static final String TAG = "SiteConnector";
    //название сайта для подсключения
    //protected static final String OPRST_ENDPOINT = "http://oprst.com.ua/";
    protected static final String OPRST_ENDPOINT = "http://weed.esy.es/";

    //адрес скрипта для получения списка фото материалов
    private static final String OPRST_PHOTO_ENDPOINT = OPRST_ENDPOINT+
            "rest/getItemsPhoto.php";
    //адрес скрипта для получения колличества фото материалов
    private static final String OPRST_PHOTO_ID_ENDPOINT =
            OPRST_ENDPOINT+"rest/getItemsPhotoId.php";
    //адрес скрипта для получения списка видео материалов
    private static final String OPRST_VIDEO_ENDPOINT = OPRST_ENDPOINT+
            "rest/getItemsVideo.php";
    //адрес скрипта для получения колличества видео материалов
    private static final String OPRST_VIDEO_ID_ENDPOINT =
            OPRST_ENDPOINT+"rest/getItemsVideoId.php";
    //адрес скрипта для получения галереи фотографий
    private static final String OPRST_PHOTO_GALLERY_ENDPOINT =
            OPRST_ENDPOINT+"rest/getItemsPhotoGallery.php";
    //адрес скрипта для получения комментариев к видео
    private static final String OPRST_VIDEO_COMMENTS_ENDPOINT =
            OPRST_ENDPOINT+"rest/getItemsVideoComments.php";

    public String fetchPhotoItemsId(){
    String mEndPoint=OPRST_PHOTO_ID_ENDPOINT;
    Connection.Response response = null;
    String text=null;
    try{
        response = Jsoup.connect(mEndPoint)
                .method(Connection.Method.GET)
                .execute();
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }
        text=response.parse().text();
    }catch (IOException jo) {
        Log.e(TAG, "Failed to get photo gallery id", jo);
    }
        return text;
    }
    public String fetchVideoItemId(){
        String mEndPoint=OPRST_VIDEO_ID_ENDPOINT;
        Connection.Response response = null;
        String text=null;
        try{
            response = Jsoup.connect(mEndPoint)
                    .method(Connection.Method.GET)
                    .execute();
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            text=response.parse().text();
        }catch (IOException jo) {
            Log.e(TAG, "Failed to get photo gallery id", jo);
        }
        return text;
    }

    private String gzipDecompress(String urlSpec){
        HttpURLConnection connection =null;
        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection)url.openConnection();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            in = new GZIPInputStream(in);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            String body = out.toString();
            return body;
        }catch (IOException io) {
            Log.e(TAG, "Failed to connect", io);
        } finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
        return null;
    }
    //превращает ответ сервера в строку
    private String getUrlR(String urlSpec) {
       return new String(getUrlBytes(urlSpec));
    }
    //превращает ответ сервера в JSON объект
    private JSONObject getJSONObject(String urlSpec){
        try {
            String result = getUrlR(urlSpec);
            JSONObject json=new JSONObject(result);
            return json;
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return null;
    }
    //превращает ответ сервера в JSON массив
    private JSONArray getJSONArray(String urlSpec){
        return getJSONArray(urlSpec,false);
    }
    private JSONArray getJSONArray(String urlSpec,boolean gzip){
        String result= null;
        try {
            if(gzip){
                result=gzipDecompress(urlSpec);
            }else{
                result = getUrlR(urlSpec);
            }
            JSONArray json=new JSONArray(result);
            return json;
        } catch (JSONException ioj) {
            Log.e(TAG, "Failed to convert json", ioj);
        }
        return null;
    }
    //превращает ответ сервера в массив байт
    byte[] getUrlBytes(String urlSpec){
        HttpURLConnection connection =null;
        try {
        URL url = new URL(urlSpec);
        connection = (HttpURLConnection)url.openConnection();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }catch (IOException io) {
            Log.e(TAG, "Failed to connect", io);
        } finally {
            if(connection!=null){
            connection.disconnect();
            }
        }
        return null;
    }

    //разбивает ответ сервера на массив объектов и количество объектов на сервере
    //принимает в себя начальный индекс объекта и количество возвращаемых объектов после него
    public ArrayList<PhotoContainerItem> fetchPhotoItems(String width,String height){
        JSONArray jsonValues=null;
        String mEndPoint=OPRST_PHOTO_ENDPOINT;
        String url=Uri.parse(mEndPoint).buildUpon()
                .appendQueryParameter("width", width)
                .appendQueryParameter("height", height)
                .build().toString();
        jsonValues=getJSONArray(url);
        if(jsonValues==null){return null;}
        ArrayList<PhotoContainerItem> items= PhotoContainerItem.photoArray(jsonValues, OPRST_ENDPOINT);
        return items;
    }
    public ArrayList<VideoContainerItem> fetchVideoItems(String width,String height){
        JSONArray jsonValues=null;
        String mEndPoint=OPRST_VIDEO_ENDPOINT;
        String url=Uri.parse(mEndPoint).buildUpon()
                .appendQueryParameter("width", width)
                .appendQueryParameter("height", height)
                .build().toString();
        jsonValues=getJSONArray(url);
        if(jsonValues==null){return null;}
        ArrayList<VideoContainerItem> items= VideoContainerItem.videoArray(jsonValues, OPRST_ENDPOINT);
        return items;
    }

    public ArrayList<PhotoGalleryItem> fetchPhotoGalleryItems(String id,String width,String height){
        JSONArray jsonValues=null;
        String mEndPoint=OPRST_PHOTO_GALLERY_ENDPOINT;
        String url=Uri.parse(mEndPoint).buildUpon()
                .appendQueryParameter("id", id)
                .appendQueryParameter("width", width)
                .appendQueryParameter("height", height)
                .build().toString();
        jsonValues=getJSONArray(url);
        if(jsonValues==null){return null;}
        return PhotoGalleryItem.photoArray(jsonValues, OPRST_ENDPOINT);
    }
    public ArrayList<VideoCommentItem> getVideoComments(String id){
        String url=Uri.parse(OPRST_VIDEO_COMMENTS_ENDPOINT).buildUpon()
                .appendQueryParameter("id", id)
                .build().toString();
        String response = getUrlR(url);
        Document doc = Jsoup.parse(response);
        ArrayList<VideoCommentItem> items=new ArrayList<>();
        VideoCommentItem item=null;
        for(Element comment:doc.select(".jot-comment")){
            item=new VideoCommentItem();
            item.setName(comment.select(".jot-name").text());
            item.setDate(comment.select(".jot-date").text());
            item.setSubject(comment.select(".jot-subject").text());
            item.setComment(comment.select(".jot-message").text());
            item.setUrlDown(OPRST_ENDPOINT+
                    comment.select(".jot-btn-down").attr("href"));
            item.setRating(comment.select(".jot-rating").text());
            item.setUrlUp(OPRST_ENDPOINT+
                    comment.select(".jot-btn-up").attr("href"));
            items.add(item);
        }
        return items;
    }

    public String vote(String urlSpec){
        Connection.Response response = null;
        Connection.Response cookiesResponse = null;
        String numberVotes=null;
        String [] urlSplit=urlSpec.split("/");
        String mEndPoint=OPRST_ENDPOINT+"video/"+urlSplit[urlSplit.length - 1];

        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss z");
        date.setTimeZone(TimeZone.getTimeZone("GMT"));
        String localTime = date.format(currentLocalTime);

        String [] hostSplit=OPRST_ENDPOINT.split("//");
        String host=hostSplit[1].substring(0, hostSplit[1].length() - 1);
        try {
            cookiesResponse = Jsoup.connect(mEndPoint)
                    .method(Connection.Method.GET)
                    .execute();

            Map<String, String> cookies = cookiesResponse.cookies();
            response = Jsoup.connect(mEndPoint)
                    .header("Cache-Control", "private, must-revalidate")
                    .header("Content-Encoding", "gzip")
                    .header("Content-Type", "text/html; charset=utf-8")
                    .header("Date", localTime)
                    .header("Vary", "Accept-Encoding")

                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Connection", "close")
                    .cookies(cookies)

                    .header("Host", host)

                    .referrer(mEndPoint)

                    .header("X-Requested-With", "XMLHttpRequest")

                    .method(Connection.Method.GET)
                    .execute();
            String text=response.parse().text();
            numberVotes=text;
            int code=response.statusCode();
            if ( code!= HttpURLConnection.HTTP_OK) {
                return null;
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Connection error: ", ioe);
        }
        return numberVotes;
    }
}