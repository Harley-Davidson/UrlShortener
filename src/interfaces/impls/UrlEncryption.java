package interfaces.impls;

import controllers.MainController;
import interfaces.Encryption;
import objects.UrlItem;

/**
 * Created by Max on 16.03.2015.
 */
public class UrlEncryption implements Encryption {

    private static final String ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ALPHABET_LENGTH = ALPHABET.length();
    private static final char[] CHARSET = ALPHABET.toCharArray();

    @Override
    public void encrypt(UrlItem urlItem) {
        int id = urlItem.getId();
        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            int residue = id % ALPHABET_LENGTH;
            id /= ALPHABET_LENGTH;
            shortUrl.append(CHARSET[residue]);
        }
//        if (shortUrl.length()>5) shortUrl.delete(0, shortUrl.length()-5);
        urlItem.setShortUrl(MainController.SITE_ADRESS.concat(shortUrl.reverse().toString()));
    }

    @Override
    public int decrypt(String shortUrl) {
        int id = 0;
        char[] chars = shortUrl.replaceAll(MainController.SITE_ADRESS, "").toCharArray();
        for (char c : chars) {
            id = id * ALPHABET_LENGTH + ALPHABET.indexOf(c);
        }
        return id;
    }
}
