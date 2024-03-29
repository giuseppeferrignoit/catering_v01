package it.uniroma3.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.catering.model.Credenziali;
import it.uniroma3.catering.model.Utente;
import it.uniroma3.catering.service.CredentialsService;
import it.uniroma3.catering.validator.CredentialsValidator;
import it.uniroma3.catering.validator.UserValidator;


@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new Utente());
		model.addAttribute("credentials", new Credenziali());
		return "registerUser";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		return "loginForm";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index";
	}
	
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credenziali credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credenziali.ADMIN_ROLE)) {
            return "admin/home";
        }
        return "home";
    }
	
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") Utente user,
                 BindingResult userBindingResult,
                 @ModelAttribute("credentials") Credenziali credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

			        // validate user and credentials fields
			        this.userValidator.validate(user, userBindingResult);
			        this.credentialsValidator.validate(credentials, credentialsBindingResult);
			
			        // if neither of them had invalid contents, store the User and the Credentials into the DB
			        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			            // set the user and store the credentials;
			            // this also stores the User, thanks to Cascade.ALL policy
			            credentials.setUser(user);
			            credentialsService.saveCredentials(credentials);
			            return "registrationSuccessful";
			        }
			        return "registerUser";
			    }
}

