package com.crackit.SpringSecurityJWT.secured;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crackit/v1/management")
public class MemberController {

	
	 @GetMapping("/get")
	    public String getAdmin() {
	    	System.out.println("Admin read triggered");
	        return "Secured Endpoint :: GET - MEMBER controller"; 
	    }

	   @PostMapping("/post")
	    public String POST() {
	    	System.out.println("Admin read triggered");
	        return "Secured Endpoint :: post - MEMBER controller";
	    }
	    
	   @PutMapping("/put")
	    public String put() {
	    	System.out.println("Admin read triggered");
	        return "Secured Endpoint :: put - MEMBER controller";
	    }
	   
	   @DeleteMapping("/deleteall")
	    public String delete() {
	    	System.out.println("Admin read triggered");
	        return "Secured Endpoint :: delete - MEMBER controller";
	    }

	   
	   
	   
	   
	   
	   
	   
	   @GetMapping("/getuser")
	    public String getuser() {
	    	System.out.println("Admin read triggered");
	        return "Secured Endpoint :: GET - MEMBER controller"; 
	    }

	   @PostMapping("/postuser")
	    public String postuser() {
	    	System.out.println("Admin read triggered");
	        return "Secured Endpoint :: post - MEMBER controller";
	    }
	    
	   @PutMapping("/putuser")
	    public String putuser() {
	    	System.out.println("Admin read triggered");
	        return "Secured Endpoint :: put - MEMBER controller";
	    }
	   
	   @DeleteMapping("/deleteuser")
	    public String deleteuser() {
	    	System.out.println("Admin read triggered");
	        return "Secured Endpoint :: delete - MEMBER controller";
	    }
}
