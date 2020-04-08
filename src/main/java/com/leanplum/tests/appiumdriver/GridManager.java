package com.leanplum.tests.appiumdriver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GridManager {

    private static List<URL> urlsForGrid = new ArrayList<>();

    public static void addUrlForGrid(URL gridUrl) {
        urlsForGrid.add(gridUrl);
    }

    public static List<URL> getGridURLs() {
        return urlsForGrid;
    }

    public static URL getGridURL(int i) {
        return urlsForGrid.get(i);
    }

}
