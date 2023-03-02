package com.masai.Controller;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.masai.Model.User;
import com.masai.Model.UserDetails;
import com.masai.Model.UserSession;
import com.masai.Repository.UserDetailRepository;
import com.masai.Repository.UserRepository;
import com.masai.Repository.UserSessionRepository;



@Controller
//@RestController
public class UserController {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private UserSessionRepository userSessionRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailRepository udRepository; 
	
	@PostMapping("/login")
	public RedirectView  saveUser ( @RequestParam String username, @RequestParam String password,@RequestParam String osName,
			                @RequestParam String lattitude, @RequestParam String Longitude,@RequestParam String browserName,RedirectAttributes redirectAttributes) {
		final RedirectView redirectViewlogin = new RedirectView("/login", true);
		 Optional<User> userOpt	= userRepository.findByEmail(username);
		 
		 List<UserDetails> userdetails1 =  udRepository.findByUsername(username);

		 
			
		 if(userOpt.isPresent()) {
			 UserSession loggedInUser = userSessionRepo.findByUsername(username);
			 if(loggedInUser ==null) {
				
				 	if(userOpt.get().getPassword().equals(password)) {
				 		
				 		    UserSession userSession = new UserSession();
					 		userSession.setUsername(username);
					 		userSession.setPassword(password);
					 		userSessionRepo.save(userSession);	
					 			

							
							UserDetails ud = new UserDetails();
							ud.setBrowserName(browserName);
							ud.setLoginTime(LocalTime.now());
							ud.setLongitude(Longitude);
							ud.setOsName(osName);
							ud.setSessionTime(null);
							ud.setUsername(username);
							ud.setLattitude(lattitude);
							udRepository.save(ud);
							
							List<UserDetails> userdetails2 =  udRepository.findByUsername(username);
							
							for (UserDetails userDetails2 : userdetails2) {
								redirectAttributes.addFlashAttribute("username", userDetails2.getUsername());
								redirectAttributes.addFlashAttribute("browser", userDetails2.getBrowserName());
								redirectAttributes.addFlashAttribute("osname", userDetails2.getOsName());
								redirectAttributes.addFlashAttribute("lattitude", userDetails2.getLattitude());
								redirectAttributes.addFlashAttribute("longitude", userDetails2.getLongitude());
								redirectAttributes.addFlashAttribute("logintime", userDetails2.getLoginTime());
								redirectAttributes.addFlashAttribute("sessiontime", userDetails2.getSessionTime());
								}
							
							return redirectViewlogin;
				 		
				 	}else {
				 		redirectAttributes.addFlashAttribute("errorMsg", "incorrect passwprd !!!");
				 	}
				 
			 }else {
				
//				 redirectAttributes.addFlashAttribute("errorMsg", "User already logged in");
				    UserDetails userDetails2 = userdetails1.get(userdetails1.size()-2);
					redirectAttributes.addFlashAttribute("username", userDetails2.getUsername());
					redirectAttributes.addFlashAttribute("browser", userDetails2.getBrowserName());
					redirectAttributes.addFlashAttribute("osname", userDetails2.getOsName());
					redirectAttributes.addFlashAttribute("lattitude", userDetails2.getLattitude());
					redirectAttributes.addFlashAttribute("longitude", userDetails2.getLongitude());
					redirectAttributes.addFlashAttribute("logintime", userDetails2.getLoginTime());
					redirectAttributes.addFlashAttribute("sessiontime", userDetails2.getSessionTime());
					
			 }
		 }else {
			 redirectAttributes.addFlashAttribute("errorMsg", "User not found please signup first!!!");	
		 }
		
		redirectAttributes.addFlashAttribute("login","error");
		
			return redirectViewlogin;
	}
	
	@GetMapping("/login")
	public String homeLauncher() {
		
		return "login";
	}
	

	
	@GetMapping("/signup")
	public String SignupLauncher() {
		
		return "signup";
	}
	
	@PostMapping("/signup")
	public RedirectView signUp( @RequestParam String username, @RequestParam String password,RedirectAttributes redirectAttributes) {
		
		final RedirectView redirectViewsignup = new RedirectView("/signup", true);
		
		if(userRepository.findByEmail(username).isEmpty()) {
		
		final RedirectView redirectViewlogin = new RedirectView("/login", true);
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(password);
		User saved =  	userRepository.save(user);
		redirectAttributes.addFlashAttribute("login", saved);
        redirectAttributes.addFlashAttribute("signUpSuccess", true);
		 return redirectViewlogin;
		}else {
			redirectAttributes.addFlashAttribute("signup");
	        redirectAttributes.addFlashAttribute("errorMsg", "User already exist with this email");
			return redirectViewsignup;
		}
	}
	
	@PostMapping("/logout")
	public RedirectView LogOut (Model model, @RequestParam String username,RedirectAttributes redirectAttributes) {
		
		UserSession loggedUser = userSessionRepo.findByUsername(username);
		
		if(loggedUser!=null) {
			List<UserDetails> userdetails =  udRepository.findByUsername(username);
			UserDetails userDetails2 = userdetails.get(userdetails.size()-1);
			LocalTime currenTime = LocalTime.now();
			
			Long timeDiff = (userDetails2.getLoginTime().until(currenTime, ChronoUnit.MINUTES)); 

			userDetails2.setSessionTime(timeDiff);
			Query query = new Query().addCriteria(Criteria.where("username").is(username));
			Update update =new Update().set("sessionTime", timeDiff);
//			FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
			mongoTemplate.findAndModify(query, update,UserDetails.class);
			
			udRepository.save(userDetails2);
			
			userSessionRepo.delete(loggedUser);
			
			final RedirectView redirectViewlogin = new RedirectView("/login", true);
			redirectAttributes.addFlashAttribute("login",loggedUser);
			redirectAttributes.addFlashAttribute("errorMsg","logout successfull!!");
			
			return redirectViewlogin;
			
		}else {
			
			final RedirectView redirectViewlogout = new RedirectView("/logout", true);
			redirectAttributes.addFlashAttribute("logout");
			redirectAttributes.addFlashAttribute("errorMsg", "User not found");
			return redirectViewlogout;
			
		}
		
	}
	
	@GetMapping("/logout")
	public String logout() {
		
		return "logout";
	}
	

	 

}
