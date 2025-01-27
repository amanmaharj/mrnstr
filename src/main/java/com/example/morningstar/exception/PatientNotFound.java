package com.example.morningstar.exception;

public class PatientNotFound extends RuntimeException{
    public PatientNotFound(String message){
        super(message);
    }
}
