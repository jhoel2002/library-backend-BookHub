package com.library.project.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String resourceName;
    public BadRequestException(String resourceName){
        super(String.format("ERROR when executing the %s method",resourceName));
        this.setResourceName(resourceName);
    }
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}
