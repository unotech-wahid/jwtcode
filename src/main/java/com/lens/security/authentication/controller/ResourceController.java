package com.lens.security.authentication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {

   @GetMapping("/get")
   @PreAuthorize("hasAuthority('READ_USER')")
    public String get() {
    	System.out.println("Resorce read triggered");
        return "Secured Endpoint :: GET - Test controller"; 
    }

   @PostMapping("/post")
   @PreAuthorize("hasAuthority('CREATE_USER')")
    public String post() {
    	System.out.println("Resorce read triggered");
        return "Secured Endpoint :: post - Test controller";
    }
    
   @PutMapping("/put")
   @PreAuthorize("hasAuthority('UPDATE_USER')")
    public String put() {
    	System.out.println("Resorce read triggered");
        return "Secured Endpoint :: put - Test controller";
    }
   
   @DeleteMapping("/delete")
   @PreAuthorize("hasAuthority('DELETE_USER')")
    public String delete() {
    	System.out.println("Resource read triggered");
        return "Secured Endpoint :: delete - Test controller";
    }

}
