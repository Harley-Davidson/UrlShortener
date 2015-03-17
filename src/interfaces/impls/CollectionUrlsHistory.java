package interfaces.impls;

import interfaces.UrlsHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.UrlItem;

/**
 * Created by Max on 14.03.2015.
 */
public class CollectionUrlsHistory implements UrlsHistory {
    private ObservableList<UrlItem> urlItemList = FXCollections.observableArrayList();

    @Override
    public void add(UrlItem urlItem) {
        urlItemList.add(0, urlItem);
    }

    @Override
    public void delete(UrlItem urlItem) {
        urlItemList.remove(urlItem);
    }

    @Override
    public void clearHistory() {
        urlItemList.clear();
    }

    public ObservableList<UrlItem> getUrlItemList() {
        return urlItemList;
    }

    public void print() {
        System.out.println();
        for (UrlItem urlItem : urlItemList) {
            System.out.println(urlItem.toString());
        }
    }

    public void fillTestData() {
        urlItemList.add(new UrlItem("http://vk.com/im?sel=2650132", "mindvalley.com/aabbc"));
        urlItemList.add(new UrlItem("http://www.awesomenessfest.com", "mindvalley.com/fsdff"));
        urlItemList.add(new UrlItem("https://github.com/Harley-Davidson", "mindvalley.com/tyhfg"));
        urlItemList.add(new UrlItem("http://www.hameister.org/JavaFX_GameOfLife.html", "mindvalley.com/fw4rf"));
        urlItemList.add(new UrlItem("https://translate.google.com/?hl=ru#en/ru/encode", "mindvalley.com/gdf5y"));
        urlItemList.add(new UrlItem("http://easy-code.ru/lesson/enum-types-java", "mindvalley.com/eqfds"));
        urlItemList.add(new UrlItem("http://espreso.tv/", "mindvalley.com/09oiy"));
        urlItemList.add(new UrlItem("http://itc.ua/articles/nvidia-geforce-gtx-960-vs-amd-radeon-r9-285/", "mindvalley.com/gdfte"));
        urlItemList.add(new UrlItem("http://motherboard.vice.com/read/is-the-philae-comet-lander-dead-well-find-out-soon", "mindvalley.com/7u6uy"));
        urlItemList.add(new UrlItem("https://www.facebook.com/events/353980464803475/", "mindvalley.com/j96rh"));

    }
}
