package org.webapp.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.webapp.dao.EventDao;
import org.webapp.dao.UserDao;
import org.webapp.models.Event;
import org.webapp.models.User;

public class AbstractController
{
	@Autowired
    protected UserDao userDao;
	
	@Autowired
	protected EventDao eventDao;

    public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) 
    {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, User user) 
    {
    	session.setAttribute(userSessionKey, user.getUid());
    }
    
    protected List<User> getAllUsersFromDatabase()
    {
    	return userDao.findAll();
    }
    
    protected User getUserFromDatabaseByUid(int uid)
    {
    	return userDao.findByUid(uid);
    }
    
    protected User getUserFromDatabaseByUsername(String username)
    {
    	return userDao.findByUsername(username);
    }
    
    protected void saveUserToDatabase(User user)
    {
    	userDao.save(user);
    }
    
    protected void saveEventToDatabase(Event event)
    {
    	eventDao.save(event);
    }
    
}
