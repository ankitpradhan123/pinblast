package com.pinterestscheduler.pinterest.Exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resource, String fieldName, String fieldValue) {
        super(resource+" not found for "+fieldName+" with "+fieldValue);
    }
}
