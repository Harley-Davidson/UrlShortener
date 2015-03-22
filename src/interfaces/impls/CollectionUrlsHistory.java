package interfaces.impls;

import interfaces.UrlsHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.UrlItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Max on 14.03.2015.
 */
public class CollectionUrlsHistory implements UrlsHistory {
    private ObservableList<UrlItem> urlItemList = FXCollections.observableArrayList();

    @Override
    public void add(UrlItem urlItem) {
        urlItemList.add(0, urlItem);
        ArrayList<String> arr = new ArrayList<>();
        Path in = Paths.get("../UrlShortener/src/interfaces/impls/history.txt");
        for (UrlItem item : urlItemList) {
            arr.add(item.toArchive());
        }
        try {
            Files.write(in, arr, Charset.defaultCharset());
//            System.out.println(arr.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void fillArchiveData() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("../UrlShortener/src/interfaces/impls/history.txt"));

            while (true) {
                String str = in.readLine();
                if (str == null || str.equals("")) break;
                String[] ar = str.split(", ");
                urlItemList.add(new UrlItem(ar[0], ar[1], Integer.parseInt(ar[2]), Long.parseLong(ar[3])));
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }
    }
}
