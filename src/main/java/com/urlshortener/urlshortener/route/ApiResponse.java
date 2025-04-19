package com.urlshortener.urlshortener.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private Optional<String> message = Optional.empty();
  private Optional<Integer> total = Optional.empty();
  private T data;

  // Constructor for success or error responses
  public ApiResponse(String message, Integer total, T data) {
    this.message = Optional.ofNullable(message);
    this.total = Optional.ofNullable(total);
    this.data = data;
  }

  // Getters and Setters
  public String getMessage() {
    return message.orElse(null);
  }

  public void setMessage(String message) {
    this.message = Optional.ofNullable(message);
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Integer getTotal() {
    return total.orElse(null);
  }

  public void setTotal(Integer total) {
    this.total = Optional.ofNullable(total);
  }
}
