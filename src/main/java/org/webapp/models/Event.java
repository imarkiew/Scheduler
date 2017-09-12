package org.webapp.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "events")
public class Event extends AbstractEntity implements Comparable<Event>
{
	@NotNull
	@Size(min = 1, message = "Tytuł nie może być pusty")
    @Column(name = "title")
	private String title;
	
    @Column(name = "description")
	private String description;
    
    @NotNull
    @Size(min = 1, message = "Data nie może być pusta")
    @Column(name = "datetime")
	private String datetime;

	@ManyToOne
	@JoinColumn(name = "foregin_key")
    private User user;
    
    public Event() {}
    
    public Event(String title, String description, String datetime)
    {
    	this.title = title;
    	this.description = description;
    	this.datetime = datetime;
    }
    
    public Event(String title, String description, String datetime, User user)
    {
    	this.title = title;
    	this.description = description;
    	this.datetime = datetime;
    	this.user = user;
    }
    
    @Override
    public int compareTo(Event event)
    {
    	LocalDateTime parsedDateTime = LocalDateTime.parse(datetime);
    	return parsedDateTime.compareTo(LocalDateTime.parse(event.getDatetime()));
    }

	public String getTitle(){return title;}

	public void setTitle(String title){this.title = title;}

	public String getDescription(){return description;}

	public void setDescription(String description){this.description = description;} 
	
	public String getDatetime(){return datetime;}

	public void setDatetime(String datetime){this.datetime = datetime;}

	public User getUser(){return user;}

	public void setUser(User user) {this.user = user;}

}
