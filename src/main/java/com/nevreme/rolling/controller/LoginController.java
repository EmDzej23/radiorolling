package com.nevreme.rolling.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nevreme.rolling.model.Role;
import com.nevreme.rolling.model.User;
import com.nevreme.rolling.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		String viewName = isCurrentAuthenticationAnonymous()?"login":"redirect:"+System.getProperty("APP_ROOT")+"/";
		return viewName;
	}
	
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String hom() {
		return "redirect:"+System.getProperty("APP_ROOT")+"/music/autoplay/Rolling";
	}
	
	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication == null ? true : authenticationTrustResolver.isAnonymous(authentication);
	}
	
	@RequestMapping(value = "/vest", method = RequestMethod.GET)
	public ModelAndView news() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("appRoot",System.getProperty("APP_ROOT"));
		modelAndView.setViewName("post_view");
		
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getUsername());
		if (userExists != null) {
			bindingResult.rejectValue("username", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			Set<Role> roles = new HashSet<>();
			Role role = new Role();
			role.setRole("ADMIN");
			roles.add(role);
			user.setRoles(roles);
			userService.save(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String home() {
		System.out.println("******************************************");
		System.out.println(System.getProperty("APP_ROOT"));
		System.out.println("******************************************");
		return "redirect:"+System.getProperty("APP_ROOT")+"/admin/setup";
	}
}
