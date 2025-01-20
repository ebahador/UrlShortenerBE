package com.urlshortener.urlshortener.route.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
  private static final String URL_REGEX =
      "^(https?|ftp)://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})?(:\\d+)?(/.*)?$";
  private static final Pattern pattern = Pattern.compile(URL_REGEX);

  public static boolean isValidUrl(String url) {
    if (url == null || url.isEmpty()) {
      return false;
    }

    Matcher matcher = pattern.matcher(url);
    return matcher.matches();
  }
}
