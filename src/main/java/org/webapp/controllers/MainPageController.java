package org.webapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.webapp.models.Event;
import org.webapp.models.User;


@Controller
@EnableAutoConfiguration
public class MainPageController extends AbstractController
{
	public List<Event> listOfEvents = new ArrayList<Event>(); 
	
	@RequestMapping(value = "/DisabledMainPageModifyForm", method = RequestMethod.GET) 
	public String view(HttpServletRequest request, Model model)
	{	
		User user = this.getUserFromSession(request.getSession(true)); 
		model.addAttribute("user", user); 
		model.addAttribute("event", new Event()); 
		
		listOfEvents = user.getEvents();
		Collections.sort(listOfEvents);
		
		model.addAttribute("listOfEvents", listOfEvents);
		
		return "DisabledMainPageModifyForm";
	}
	
	@RequestMapping(value = "/DisabledMainPageModifyForm", params = {"title", "description", "datetime"}, method = RequestMethod.POST) 
	public String addEvent(HttpServletRequest request, @ModelAttribute @Valid Event event, Errors errors, Model model) 
	{
		User user = this.getUserFromSession(request.getSession(true)); 
		model.addAttribute("user", user); 
		
		if(errors.hasErrors())
		{
			model.addAttribute("listOfEvents", listOfEvents);
			return "DisabledMainPageModifyForm";
		}
		
		event.setUser(user);
		this.saveEventToDatabase(event); 
		listOfEvents.add(event);
		Collections.sort(listOfEvents);
		model.addAttribute("listOfEvents", listOfEvents);
		
		return "DisabledMainPageModifyForm";
	}
	
	@RequestMapping(value = "/fill_event", params = {"table_id", "uid", "title", "description", "datetime"}, method = RequestMethod.POST)
	public String fillModifyForm(HttpServletRequest request, String table_id, @ModelAttribute @Valid Event event, Errors errors, Model model)
	{
		User user = this.getUserFromSession(request.getSession(true));
		model.addAttribute("user", user);
		model.addAttribute("table_id", table_id);
		model.addAttribute("listOfEvents", listOfEvents);
		
		return "DisabledMainPageAddForm";
	}
	
	@RequestMapping(value = "/modify_event",  params = {"table_id", "uid", "title", "description", "datetime"}, method = RequestMethod.POST)
	public String modifyEvent(HttpServletRequest request, String table_id, @ModelAttribute @Valid Event event, Errors errors, Model model)
	{
		User user = this.getUserFromSession(request.getSession(true));
		model.addAttribute("user", user);
		
		if(errors.hasErrors())
		{
			model.addAttribute("listOfEvents", listOfEvents);
			model.addAttribute("table_id", table_id);
			return "DisabledMainPageAddForm";
		}
		
		Event databaseEvent = this.eventDao.findByUid(event.getUid());
		databaseEvent.setTitle(event.getTitle());
		databaseEvent.setDescription(event.getDescription());
		databaseEvent.setDatetime(event.getDatetime());
		this.eventDao.save(databaseEvent);
		
		return "redirect:/DisabledMainPageModifyForm";
		
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel()
	{
		return "redirect:/DisabledMainPageModifyForm";
	}
	
	@RequestMapping(value = "/delete_event", method = RequestMethod.POST) 
	public String deleteEvent(HttpServletRequest request, @RequestParam int uid, Model model)
	{
		for(int i = 0; i < listOfEvents.size(); i++)
		{
			if(listOfEvents.get(i).getUid() == uid)
			{
				this.eventDao.deleteByUid(uid); 
				break;
			}
		}
		
		return "redirect:/DisabledMainPageModifyForm"; 
	}
	
	@RequestMapping(value = "/DisabledMainPageAddForm", method = RequestMethod.POST)
	public String logoutFromDisabledModifyForm(HttpServletRequest request)
	{
        request.getSession().invalidate();
		
        return "redirect:/login"; 
	}
	
	@RequestMapping(value = "/fill_event", method = RequestMethod.POST)
	public String logoutFromModify(HttpServletRequest request)
	{
        request.getSession().invalidate();
        return "redirect:/login"; 
	}
	
	@RequestMapping(value = "/DisabledMainPageModifyForm", method = RequestMethod.POST)
	public String logout(HttpServletRequest request)
	{
        request.getSession().invalidate();
        return "redirect:/login"; 
	}
	
}

