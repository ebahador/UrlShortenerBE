package com.urlshortener.urlshortener.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(as = CreateUrlDto.class)
public class CreateUrlDto {
  private String id;
  private String name;
  private String url;

  private CreateUrlDto() {}

  private CreateUrlDto(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.url = builder.url;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public String getId() {
    return id;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
    private String id;
    private String name;
    private String url;

    public Builder() {}

    public Builder(CreateUrlDto createUrlDto) {
      this.id = createUrlDto.id;
      this.name = createUrlDto.getName();
      this.url = createUrlDto.getUrl();
    }

    public Builder name(String val) {
      this.name = val;
      return this;
    }

    public Builder url(String val) {
      this.url = val;
      return this;
    }

    public Builder expiryDate(long val) {
      return this;
    }

    public Builder id(String val) {
      this.id = val;
      return this;
    }

    public CreateUrlDto build() {
      return new CreateUrlDto(this);
    }
  }
}
