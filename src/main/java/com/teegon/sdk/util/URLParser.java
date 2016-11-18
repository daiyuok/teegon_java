package com.teegon.sdk.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by uu on 16-11-16.
 */
public class URLParser {

    private String protocol;
    private String host;
    private String path;
    private int port;
    private String query;

    public URLParser(String url) {
        try {
            URL currentUrl = new URL(url);
            this.protocol = currentUrl.getProtocol();
            this.host = currentUrl.getHost();
            this.port = currentUrl.getPort();
            this.path = currentUrl.getPath();
            this.query = currentUrl.getQuery();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return this.path;
    }

    public String getSite() {
        return protocol + "://" + host + (port == -1 ? "" : (":" + port));
    }

    public String getSite(String path) {
        return protocol + "://" + host + (port == -1 ? "" : (":" + port)) + path;
    }

    public String getSiteWithPath() {
        return protocol + "://" + host + (port == -1 ? "" : (":" + port)) + path;
    }

    public String getSiteWithAppendPath(String appendPath) {
        return protocol + "://" + host + (port == -1 ? "" : (":" + port)) + path + appendPath;
    }

    public String getFullUrl() {
        String pathUrl = protocol + "://" + host + (port == -1 ? "" : (":" + port)) + path;
        if (query != null) {
            return pathUrl + "?" + query;
        }
        return pathUrl;
    }

    public String getWsUrl(String method) {
        return "ws://" + host + (port == -1 ? "" : (":" + port)) + path + method;
    }

    public String getWsPath(String method) {
        return path + method;
    }


}
