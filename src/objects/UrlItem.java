package objects;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

/**
 * Created by Max on 14.03.2015.
 */
public class UrlItem {
    private static int idCounter;
    private SimpleStringProperty longUrl = new SimpleStringProperty("");
    private SimpleStringProperty shortUrl = new SimpleStringProperty("");
    private int id;
    private long registrationTime;

    public UrlItem(String longUrl, String shortUrl) {
        this.longUrl.set(longUrl);
        this.shortUrl.set(shortUrl);
        registrationTime = System.currentTimeMillis();
        this.id = (int) ((int) ((int) registrationTime / (1 + Math.random()) + idCounter * (1 + Math.random())) / (333 * (1 + Math.random())));
        idCounter++;
    }

    public String getLongUrl() {
        return longUrl.get();
    }

    public void setLongUrl(String longUrl) {
        this.longUrl.set(longUrl);
    }

    public String getShortUrl() {
        return shortUrl.get();
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl.set(shortUrl);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistrationTime() {
        return (new SimpleDateFormat("HH:mm:ss, dd-MMM-yy")).format(registrationTime);
    }

    public void setRegistrationTime(long registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public String toString() {
        return "UrlItem{" +
                "longUrl='" + longUrl.get() + '\'' +
                ", shortUrl='" + shortUrl.get() + '\'' +
                ", registrationTime=" + getRegistrationTime() +
                '}';
    }

    public SimpleStringProperty longUrlProperty() {
        return longUrl;
    }

    public SimpleStringProperty shortUrlProperty() {
        return shortUrl;
    }

}
