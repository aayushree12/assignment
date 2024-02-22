package com.ttek.carparkfinder.exception;

import org.springframework.http.HttpStatus;

public class MissingCoordinatesException extends IllegalArgumentException {

//  public MissingCoordinatesException(String message) {
//    super(message);
//  }
private final HttpStatus status;

  public MissingCoordinatesException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}