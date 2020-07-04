package br.com.jetty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().body("Teste Jetty");
    }
}
