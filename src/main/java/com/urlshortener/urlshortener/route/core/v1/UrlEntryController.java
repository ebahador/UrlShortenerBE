package com.urlshortener.urlshortener.route.core.v1;

import com.urlshortener.urlshortener.database.UrlShortenerDao;
import com.urlshortener.urlshortener.model.UrlEntry;
import com.urlshortener.urlshortener.model.dto.CreateUrlDto;
import com.urlshortener.urlshortener.route.ApiResponse;
import com.urlshortener.urlshortener.route.core.v1.handlers.UrlCreateService;
import com.urlshortener.urlshortener.route.core.v1.handlers.UrlInfoService;
import com.urlshortener.urlshortener.route.utils.ApiUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUtils.V1_PATH)
public class UrlEntryController {
  private static Logger logger;
  private final UrlShortenerDao dao;
  private final UrlCreateService urlCreateService;
  private final UrlInfoService urlInfoService;

  @Autowired
  public UrlEntryController(
      UrlShortenerDao dao, UrlCreateService urlCreateService, UrlInfoService urlInfoService) {
    this.dao = dao;
    this.urlCreateService = urlCreateService;
    this.urlInfoService = urlInfoService;
    logger = LoggerFactory.getLogger(UrlEntryController.class);
  }

  @PostMapping("/url")
  public ResponseEntity<ApiResponse<UrlEntry>> createUrlEntry(@RequestBody CreateUrlDto urlEntry) {
    logger.info("Creating url entry: {}", urlEntry);
    return urlCreateService.createUrl(urlEntry);
  }

  @GetMapping("/url/{id}")
  public ResponseEntity<ApiResponse<UrlEntry>> getUrlEntry(@PathVariable String id) {
    logger.info("Retrieving url entry: {}", id);
    return urlInfoService.getUrlById(id);
  }

  @GetMapping("/url")
  public ResponseEntity<ApiResponse<List<UrlEntry>>> getAllUrlEntries() {
    logger.info("Retrieving all url entries");
    return urlInfoService.getAllUrlRecords();
  }
}
