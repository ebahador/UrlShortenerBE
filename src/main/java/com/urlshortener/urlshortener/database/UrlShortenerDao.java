package com.urlshortener.urlshortener.database;

import com.urlshortener.urlshortener.model.UrlEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class UrlShortenerDao {
  private static final String TABLE_NAME = "urls";
  private final HashOperations<String, String, String> hashOperations;
  private final StringRedisTemplate redisTemplate;
  private final Logger logger = LoggerFactory.getLogger(UrlShortenerDao.class);

  public UrlShortenerDao(
      HashOperations<String, String, String> hashOperations, StringRedisTemplate redisTemplate) {
    this.hashOperations = hashOperations;
    this.redisTemplate = redisTemplate;
  }

  public void insertUrlInfo(UrlEntry urlEntry) {
    try {
      String key = TABLE_NAME + ":" + urlEntry.getId();
      hashOperations.put(key, "id", urlEntry.getId());
      hashOperations.put(key, "url", urlEntry.getUrl());
      hashOperations.put(key, "name", urlEntry.getName());
      hashOperations.put(key, "shortenedUrl", urlEntry.getShortenedUrl());
      hashOperations.put(key, "expiryDate", String.valueOf(urlEntry.getExpiryDate()));
    } catch (Exception e) {
      logger.error("Error while inserting url info", e);
      throw new RuntimeException(e);
    }
  }

  public UrlEntry getUrlInfo(String id) {
    try {
      String key = TABLE_NAME + ":" + id;
      return new UrlEntry.Builder()
          .id(hashOperations.get(key, "id"))
          .name(hashOperations.get(key, "name"))
          .url(hashOperations.get(key, "url"))
          .shortenedUrl(hashOperations.get(key, "shortenedUrl"))
          .expiryDate(Long.parseLong(Objects.requireNonNull(hashOperations.get(key, "expiryDate"))))
          .build();
    } catch (Exception e) {
      logger.error("Error while getting url info", e);
      throw new RuntimeException(e);
    }
  }

  public List<UrlEntry> getAllUrls() {
    try {
      Set<String> keys = redisTemplate.keys(TABLE_NAME + ":*");
      List<UrlEntry> urlEntries = new ArrayList<>();

      for (String key : keys) {
        UrlEntry urlEntry =
            new UrlEntry.Builder()
                .id(hashOperations.get(key, "id"))
                .name(hashOperations.get(key, "name"))
                .url(hashOperations.get(key, "url"))
                .shortenedUrl(hashOperations.get(key, "shortenedUrl"))
                .expiryDate(
                    Long.parseLong(Objects.requireNonNull(hashOperations.get(key, "expiryDate"))))
                .build();
        urlEntries.add(urlEntry);
      }
      return urlEntries;
    } catch (Exception e) {
      logger.error("Error while getting all url info", e);
      throw new RuntimeException(e);
    }
  }

  public boolean isIdValid(String id) {
    try {

      String key = TABLE_NAME + ":" + id;
      return hashOperations.get(key, "id") != null;
    } catch (Exception e) {
      logger.error("Error while id validation", e);
      throw new RuntimeException(e);
    }
  }

  public UrlEntry getUrlInfoByUrlCode(String shortenedUrl) {
    try {
      // Iterate over all keys to find the matching shortenedUrl
      Set<String> keys = redisTemplate.keys(TABLE_NAME + ":*");
      for (String key : keys) {
        String storedShortenedUrl = hashOperations.get(key, "shortenedUrl");
        if (shortenedUrl.equals(storedShortenedUrl)) {
          // Found a match, retrieve the full record
          return new UrlEntry.Builder()
              .id(hashOperations.get(key, "id"))
              .name(hashOperations.get(key, "name"))
              .url(hashOperations.get(key, "url"))
              .shortenedUrl(storedShortenedUrl)
              .expiryDate(
                  Long.parseLong(Objects.requireNonNull(hashOperations.get(key, "expiryDate"))))
              .build();
        }
      }
      throw new IllegalArgumentException("No URL entry found for shortened URL: " + shortenedUrl);
    } catch (Exception e) {
      logger.error("Error while getting URL info based on shortened URL", e);
      throw new RuntimeException("Failed to retrieve URL info: " + shortenedUrl, e);
    }
  }
}
