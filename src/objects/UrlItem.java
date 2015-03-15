package objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Max on 14.03.2015.
 */
public class UrlItem {
    private static int idCounter;
    private String longUrl;
    private String shortUrl;
    private int id;
    private long registrationTime;

    public UrlItem(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        registrationTime = System.currentTimeMillis();
        this.id = (int) registrationTime + idCounter;
        idCounter++;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistrationTime() {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss, dd-MMM-yy");
        String timeFormatted = formatter.format(registrationTime);
        return timeFormatted;
    }

    public void setRegistrationTime(long registrationTime) {
        this.registrationTime = registrationTime;
    }
}
