package com.blogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{//purely for runtime & Status code 404

    public ResourceNotFoundException(long id){//call constructor with id
        super("Resource Not Found For id: "+id);//super keyword does display the msg with id no. in postman response
    }
}
