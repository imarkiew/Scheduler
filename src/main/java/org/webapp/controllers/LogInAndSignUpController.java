package org.webapp.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.webapp.models.User;

@Controller
@EnableAutoConfiguration
public class LogInAndSignUpController extends AbstractController
{

	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String logInAndSignUpPanel()
	{
		return "LogInAndSignUpPage";
	}
	
	@RequestMapping(value = "/login", params = {"username", "password", "verify"}, method = RequestMethod.POST) 
	public String signUpForm(HttpServletRequest request, @RequestParam String username, @RequestParam String password,
			@RequestParam String verify, Model model)
	{
		if(!password.equals(verify))
		{
			model.addAttribute("verify_error", "Hasło i jego potwierdzenie nie są zgodne");
			return "LogInAndSignUpPage";
		}
		else if(!User.isValidUsername(username) || !(this.getUserFromDatabaseByUsername(username) == null))
		{	
			model.addAttribute("username_error", "Nazwa użytkownika jest nieprawidłowa lub juz istnieje");
			return "LogInAndSignUpPage";
		}
		else if(!User.isValidPassword(password))
		{
			model.addAttribute("password_error", "Hasło jest nieprawidłowe");
			return "LogInAndSignUpPage";
		}
		else
		{
			User user = new User(username, password);
			this.saveUserToDatabase(user);
			this.setUserInSession(request.getSession(true), user); 
			model.addAttribute("user", user);
			return "redirect:/DisabledMainPageModifyForm"; 
		}
	}
	
	@RequestMapping(value = "/login", params = {"login_username", "login_password"}, method = RequestMethod.POST) 
	public String LogInForm(HttpServletRequest request, @RequestParam String login_username, @RequestParam String login_password, Model model)
	{
		User user = this.getUserFromDatabaseByUsername(login_username);
		
		if(user == null)
		{
			model.addAttribute("login_username_error", "Nie znaleziono podanej nazwy użytkownika");
			return "LogInAndSignUpPage";
		}
		else if(!user.isMatchingPassword(login_password))
		{	
			model.addAttribute("login_password_error", "Nieprawidłowe hasło");
			return "LogInAndSignUpPage";
		}
		else
		{
			this.setUserInSession(request.getSession(true), user); 
			model.addAttribute("user", user);
			return "redirect:/DisabledMainPageModifyForm"; 
		}
	}
}
