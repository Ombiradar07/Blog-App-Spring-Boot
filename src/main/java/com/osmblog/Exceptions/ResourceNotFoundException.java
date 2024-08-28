package com.osmblog.Exceptions;

public class ResourceNotFoundException extends RuntimeException{


   public ResourceNotFoundException(String massage){
        super(massage);
   }

}
