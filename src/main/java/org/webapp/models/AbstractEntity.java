package org.webapp.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;

@MappedSuperclass
public class AbstractEntity 
{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @NotNull
    @Column(name = "uid", unique = true)
	private int uid;

	public int getUid()
	{
		return this.uid;
	}
	
	public void setUid(int uid)
	{
        this.uid = uid;
    }
}
