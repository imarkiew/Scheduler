package org.webapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User extends AbstractEntity
{
	@NotNull
    @Column(name = "username", unique = true)
	private String username;
	
	@NotNull
    @Column(name = "pwhash")
	private String pwHash;
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public User() {} 
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Event> events = new ArrayList<Event>();

	public User(String username, String password)
	{
		if (!isValidUsername(username))
		{
			throw new IllegalArgumentException("Invalid username");
		}
		
		if (!isValidPassword(password))
		{
			throw new IllegalArgumentException("Invalid password");
		}
		
		this.username = username;
		this.pwHash = hashPassword(password);
	}
	
	public boolean isMatchingPassword(String password){return encoder.matches(password, pwHash);}
	
	public static boolean isValidPassword(String password)
	{
		Pattern validUsernamePattern = Pattern.compile("[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9]{1,15}"); 
		Matcher matcher = validUsernamePattern.matcher(password);
		return matcher.matches();
	}
	
	public static boolean isValidUsername(String username) 
	{
		Pattern validUsernamePattern = Pattern.compile("[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9]{1,15}");
		Matcher matcher = validUsernamePattern.matcher(username);
		return matcher.matches();
	}
	
	public void addEvent(Event event){events.add(event);}
	
	public static BCryptPasswordEncoder getEncoder() {return encoder;}
	
	private static String hashPassword(String password) {return encoder.encode(password);}
	
	public String getUsername() {return username;}

	public void setUsername(String username) {this.username = username;}
	
	public String getPwHash() {return pwHash;}
	
	public void setPwHash(String pwHash) {this.pwHash = pwHash;}
	
	public List<Event> getEvents() {return events;}

	public void setEvents(List<Event> events) {this.events = events;}

}
