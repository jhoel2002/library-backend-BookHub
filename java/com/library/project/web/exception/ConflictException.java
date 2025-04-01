package com.library.project.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ConflictException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("There is already a %s with that %s %s",resourceName,fieldName,fieldValue));
        this.setResourceName(resourceName);
        this.setFieldName(fieldName);
        this.setFieldValue(fieldValue);
    }

    public ConflictException(String resourceName, String fieldName){
        super(String.format("Cannot delete because there are %s associated with this %s"
                ,resourceName,fieldName));
        this.setResourceName(resourceName);
        this.setFieldName(fieldName);
    }
    
    public ConflictException(String message){
        super(message);
    }

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
}
