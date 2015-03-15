package interfaces;

import objects.UrlItem;

/**
 * Created by Max on 14.03.2015.
 */
public interface UrlShortening {

    void encodeLongUrl(UrlItem urlItem);

    void decodeShortUrl(UrlItem urlItem);

    void goToUrl(UrlItem urlItem);

    void copyUrl(UrlItem urlItem);

}
