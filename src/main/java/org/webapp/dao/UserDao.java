package org.webapp.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.webapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer>
{
    User findByUid(int uid);
    
    List<User> findAll();
    
    User findByUsername(String username);
    
    @SuppressWarnings("unchecked")
	User save(User user);
    
    List<User> deleteByUsername(String username);
    
}