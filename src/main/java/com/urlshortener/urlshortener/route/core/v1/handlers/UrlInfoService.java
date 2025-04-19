package com.urlshortener.urlshortener.route.core.v1.handlers;

import com.urlshortener.urlshortener.database.UrlShortenerDao;
import com.urlshortener.urlshortener.model.UrlEntry;
import com.urlshortener.urlshortener.route.ApiResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UrlInfoService {
  private final UrlShortenerDao dao;
  public final Logger logger = LoggerFactory.getLogger(UrlInfoService.class);

  @Autowired
  public UrlInfoService(UrlShortenerDao dao) {
    this.dao = dao;
  }

  public ResponseEntity<ApiResponse<UrlEntry>> getUrlById(String id) {
    try {
      if (StringUtils.hasLength(id)) {
        logger.warn("Id is null or empty");
        ApiResponse<UrlEntry> apiResponse = new ApiResponse<>("Id is null or empty", 0, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
      }

      if (!dao.isIdValid(id)) {
        logger.warn("Id not found");
        ApiResponse<UrlEntry> apiResponse = new ApiResponse<>("Id not found", 0, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
      }

      logger.info("Get url by id: {}", id);
      UrlEntry urlEntries = dao.getUrlInfoById(id);
      ApiResponse<UrlEntry> apiResponse = new ApiResponse<>(null, 1, urlEntries);
      return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    } catch (Exception e) {
      logger.error("Error while fetching all URLs", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse<>(e.getMessage(), null, null));
    }
  }

  public ResponseEntity<ApiResponse<List<UrlEntry>>> getAllUrlRecords() {
    try {
      logger.info("Get all url records");
      List<UrlEntry> urlEntryList = dao.getAllUrls();
      ApiResponse<List<UrlEntry>> apiResponse =
          new ApiResponse<>(null, urlEntryList.size(), urlEntryList);
      return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    } catch (Exception e) {
      logger.error("Error while fetching all URLs", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse<>(e.getMessage(), null, null));
    }
  }
}
