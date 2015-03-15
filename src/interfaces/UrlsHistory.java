package interfaces;

import objects.UrlItem;

/**
 * Created by Max on 14.03.2015.
 */
public interface UrlsHistory {

    void add(UrlItem urlItem);

    void delete(UrlItem urlItem);

    void clearHistory();
}
