package com.urlshortener.urlshortener.route.core.v1.handlers;

import com.urlshortener.urlshortener.database.UrlShortenerDao;
import com.urlshortener.urlshortener.model.UrlEntry;
import com.urlshortener.urlshortener.model.dto.CreateUrlDto;
import com.urlshortener.urlshortener.route.ApiResponse;
import com.urlshortener.urlshortener.route.utils.StrUtils;
import com.urlshortener.urlshortener.route.utils.UrlShortener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
public class UrlCreateService {
  private final UrlShortenerDao dao;
  private static final Logger logger = LoggerFactory.getLogger(UrlCreateService.class);

  public UrlCreateService(UrlShortenerDao dao) {
    this.dao = dao;
  }

  public ResponseEntity<ApiResponse<UrlEntry>> createUrl(CreateUrlDto urlEntry) {
    try {
      if (urlEntry.getUrl() == null
          || urlEntry.getUrl().isEmpty()
          || urlEntry.getName() == null
          || urlEntry.getName().isEmpty()) {
        logger.warn("url or name is empty");
        ApiResponse<UrlEntry> apiResponse = new ApiResponse<>("url or name is empty", 0, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
      }
      if (!StrUtils.isValidUrl(urlEntry.getUrl())) {
        logger.warn("url is invalid");
        ApiResponse<UrlEntry> apiResponse = new ApiResponse<>("url is invalid", 0, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
      }
      long oneYearInMillis = 365L * 24L * 60L * 60L * 1000L;

      UrlEntry response =
          new UrlEntry.Builder()
              .id(UUID.randomUUID().toString())
              .name(urlEntry.getName())
              .url(urlEntry.getUrl())
              .shortenedUrl(UrlShortener.shortenUrl(urlEntry.getUrl()))
              .expiryDate(System.currentTimeMillis() + oneYearInMillis)
              .build();

      logger.info("Create url: {}", urlEntry.getUrl());
      dao.insertUrlInfo(response);
      ApiResponse<UrlEntry> apiResponse =
          new ApiResponse<>("url created successfully", 1, response);
      return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    } catch (Exception e) {
      logger.error("Error creating url", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse<>(e.getMessage(), null, null));
    }
  }
}
