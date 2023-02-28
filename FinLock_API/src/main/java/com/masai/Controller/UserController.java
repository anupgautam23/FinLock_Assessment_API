package com.masai.Controller;


import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

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
public class UserController {
	
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
		 if(userOpt.isPresent()) {
			 UserSession loggedInUser = userSessionRepo.findByUsername(username);
			 if(loggedInUser ==null) {
				
				 	if(userOpt.get().getPassword().equals(password)) {
				 		
				 		    UserSession userSession = new UserSession();
					 		userSession.setUsername(username);
					 		userSession.setPassword(password);
					 		userSessionRepo.save(userSession);	
					 			
					 		final RedirectView redirectView = new RedirectView("/welcome", true);
							
							UserDetails ud = new UserDetails();
							ud.setBrowserName(browserName);
							ud.setLoginTime(LocalTime.now());
							ud.setLongitude(Longitude);
							ud.setOsName(osName);
							ud.setSessionTime(LocalTime.now());
							ud.setUsername(username);
							ud.setLattitude(lattitude);
							udRepository.save(ud);
							redirectAttributes.addFlashAttribute("welcome", loggedInUser);
					        redirectAttributes.addFlashAttribute("loginsuccess", true);	
							return redirectView;
				 		
				 	}else {
				 		redirectAttributes.addFlashAttribute("errorMsg", "incorrect passwprd !!!");
				 	}
				 
			 }else {
				
				 redirectAttributes.addFlashAttribute("errorMsg", "User already logged in");	
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
	
	@GetMapping("/welcome")
	public String welcome(Model model) {
		List<UserDetails> list  = udRepository.findAll();
		for (UserDetails userDetails : list) {
			model.addAttribute("username",userDetails.getUsername());
			model.addAttribute("browser",userDetails.getBrowserName());
			model.addAttribute("osname",userDetails.getOsName());
			model.addAttribute("lattitude",userDetails.getLattitude());
			model.addAttribute("longitude",userDetails.getLongitude());
			model.addAttribute("logtime",userDetails.getLoginTime());
			model.addAttribute("logsession",userDetails.getSessionTime());
		}
		
		
		return "welcome";
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
	public RedirectView LogOut(@RequestParam String email,RedirectAttributes redirectAttributes) {
		final RedirectView redirectViewlogout = new RedirectView("/logout", true);
		UserSession loggedUser = userSessionRepo.findByUsername(email);
		if(loggedUser!=null) {
			userSessionRepo.delete(loggedUser);
			
			final RedirectView redirectViewlogin = new RedirectView("/login", true);
			redirectAttributes.addFlashAttribute("login");
			redirectAttributes.addFlashAttribute("errorMsg",true);
			return redirectViewlogin;
		}else {
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
