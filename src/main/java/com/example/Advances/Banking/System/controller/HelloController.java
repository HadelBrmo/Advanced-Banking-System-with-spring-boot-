package com.example.Advances.Banking.System.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

 // ⭐ هذا ضروري
@RestController
public class HelloController {

     @GetMapping("/hello")
     public String hello() {
         return "Hello World from Banking System!";
     }
 }