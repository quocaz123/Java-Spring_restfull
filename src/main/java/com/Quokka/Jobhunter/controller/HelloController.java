package com.Quokka.Jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Quokka.Jobhunter.util.error.IdInvalidException;

@RestController
public class HelloController {
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<String> handleException(IdInvalidException idInvalidException) {
        return ResponseEntity.badRequest().body(idInvalidException.getMessage());
    }

    @GetMapping("/")
    public String getHelloPage() throws IdInvalidException {
        if (true)
            throw new IdInvalidException("Check");
        return "Hello world";
    }
}
