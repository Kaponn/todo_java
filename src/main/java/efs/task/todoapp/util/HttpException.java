package efs.task.todoapp.util;

import efs.task.todoapp.constants.HttpStatus;

public class HttpException extends RuntimeException {

  HttpStatus httpStatus;

  public HttpException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
