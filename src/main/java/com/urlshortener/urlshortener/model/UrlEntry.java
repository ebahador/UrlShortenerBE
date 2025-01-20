package com.urlshortener.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlEntry {
  private String id;
  private String name;
  private String url;
  private String shortenedUrl;
  private long expiryDate;

  private UrlEntry() {}

  private UrlEntry(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.url = builder.url;
    this.shortenedUrl = builder.shortenedUrl;
    this.expiryDate = builder.expiryDate;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public String getShortenedUrl() {
    return shortenedUrl;
  }

  public long getExpiryDate() {
    return expiryDate;
  }

  public static class Builder {
    private String id;
    private String name;
    private String url;
    private String shortenedUrl;
    private long expiryDate;

    public Builder() {}

    public Builder(UrlEntry urlEntry) {
      this.id = urlEntry.id;
      this.name = urlEntry.name;
      this.url = urlEntry.url;
      this.shortenedUrl = urlEntry.shortenedUrl;
      this.expiryDate = urlEntry.expiryDate;
    }

    public Builder id(String val) {
      id = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder url(String val) {
      url = val;
      return this;
    }

    public Builder shortenedUrl(String val) {
      shortenedUrl = val;
      return this;
    }

    public Builder expiryDate(long val) {
      expiryDate = val;
      return this;
    }

    public UrlEntry build() {
      return new UrlEntry(this);
    }
  }
}
