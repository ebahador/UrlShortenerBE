package com.urlshortener.urlshortener.route.utils;

import java.util.HashMap;
import java.util.Map;

public class UrlShortener {
  private static final String BASE62 =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private static final Map<String, String> urlMap =
      new HashMap<>(); // Maps short keys to original URLs

  // Encode the counter to Base62
  private static String encodeBase62(int num) {
    StringBuilder sb = new StringBuilder();
    while (num > 0) {
      sb.insert(0, BASE62.charAt(num % 62));
      num /= 62;
    }
    return sb.toString();
  }

  // Shorten the URL
  public static String shortenUrl(String originalUrl) {
    int uniqueId = (int) (Math.random() * 10000);
    String shortKey = encodeBase62(uniqueId);

    urlMap.put(shortKey, originalUrl);

    return shortKey;
  }
}
