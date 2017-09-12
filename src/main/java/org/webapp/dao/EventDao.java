package org.webapp.dao;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.webapp.models.Event;


@Transactional
@Repository
public interface EventDao extends CrudRepository<Event, Integer>
{
	Event findByUid(int uid);
	Long deleteByUid(int uid);
	
	@SuppressWarnings("unchecked")
	Event save(Event event);
}
