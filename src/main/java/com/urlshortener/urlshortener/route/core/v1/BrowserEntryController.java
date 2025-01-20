package com.urlshortener.urlshortener.route.core.v1;

import com.urlshortener.urlshortener.database.UrlShortenerDao;
import com.urlshortener.urlshortener.model.UrlEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowserEntryController {
  private final Logger logger = LoggerFactory.getLogger(BrowserEntryController.class);
  private final UrlShortenerDao dao;

  @Autowired
  public BrowserEntryController(UrlShortenerDao dao) {
    this.dao = dao;
  }

  @GetMapping("/{urlCode}")
  public ResponseEntity<Void> openUrlEntryByUrlCode(@PathVariable String urlCode) {
    logger.info("Opening URL code: {}", urlCode);
    UrlEntry urlEntry = dao.getUrlInfoByUrlCode(urlCode);
    if (urlEntry == null) {
      logger.error("No url entry found for url code: {}", urlCode);
      return ResponseEntity.notFound().build();
    }
    String url = urlEntry.getUrl();
    logger.info("Retrieved url: {}", url);
    return ResponseEntity.status(302).header("Location", url).build();
  }
}
