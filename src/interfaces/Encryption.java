package interfaces;

import objects.UrlItem;

/**
 * Created by Max on 14.03.2015.
 */
public interface Encryption {

    void encrypt(UrlItem urlItem);

    int decrypt(String shortUrl);

}
