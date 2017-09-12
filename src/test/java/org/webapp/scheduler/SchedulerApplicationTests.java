package org.webapp.scheduler;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.webapp.dao.UserDao;
import org.webapp.models.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchedulerApplicationTests 
{
	
	@Autowired
	UserDao userDao;
	
	@Test
	public void isTestUserInDatabase()
	{
		User user = userDao.findByUsername("test");
		assertTrue(user.isMatchingPassword("test"));
	}

}
