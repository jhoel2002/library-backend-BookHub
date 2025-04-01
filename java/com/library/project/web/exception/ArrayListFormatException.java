package com.library.project.web.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ArrayListFormatException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String resourceName;
    private final List<String> fieldName;

    public ArrayListFormatException(List<String> fieldName, String resourceName) {
        super(String.format("%s format is incorrect: %s",resourceName,fieldName));
        this.setResourceName(resourceName);
        this.fieldName = fieldName;
    }

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public List<String> getFieldName() {
		return fieldName;
	}

}
