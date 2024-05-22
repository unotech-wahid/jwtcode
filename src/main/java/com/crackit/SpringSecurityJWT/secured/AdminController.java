package com.crackit.SpringSecurityJWT.secured;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crackit/v1/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class AdminController {

   @GetMapping("/get")
   @PreAuthorize("hasAuthority('READ_USER')")
    public String getAdmin() {
    	System.out.println("Admin read triggered");
        return "Secured Endpoint :: GET - Admin controller"; 
    }

   @PostMapping("/post")
   @PreAuthorize("hasAuthority('CREATE_USER')")
    public String POST() {
    	System.out.println("Admin read triggered");
        return "Secured Endpoint :: post - Admin controller";
    }
    
   @PutMapping("/put")
   @PreAuthorize("hasAuthority('UPDATE_USER')")
    public String put() {
    	System.out.println("Admin read triggered");
        return "Secured Endpoint :: put - Admin controller";
    }
   
   @DeleteMapping("/deleteall")
   @PreAuthorize("hasAuthority('DELETE_USER')")
    public String delete() {
    	System.out.println("Admin read triggered");
        return "Secured Endpoint :: delete - Admin controller";
    }

}
