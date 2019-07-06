package com.groupX.appX.enums;

public enum ErrorCode {

	  DATABASE(0, "A database error has occured."),
	  DUPLICATE_USER(1, "This user already exists."),
	  USER_NOT_FOUND(2,"User not found"),
	  TASK_NOT_FOUND(3,"Task not found");
	  
	  private final int code;
	  private final String description;

	  private ErrorCode(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }

}
