package efs.task.todoapp.constants;

public enum HttpStatus {
  OK (200, "OK"),
  CREATED (201, "Created"),
  BAD_REQUEST (400, "Bad Request"),
  UNAUTHORIZED (401, "Unauthorized"),
  FORBIDDEN (403, "Forbidden"),
  NOT_FOUND (404, "Not Found"),
  METHOD_NOT_ALLOWED (405, "Method Not Allowed"),
  CONFLICT (409, "Conflict");

  private final int status;
  private final String title;

  HttpStatus(int status, String title) {
    this.status = status;
    this.title = title;
  }

  public int getStatus() {
    return status;
  }

  public String getTitle() {
    return title;
  }

  public String status() {
    return status + " " + title;
  }
}
