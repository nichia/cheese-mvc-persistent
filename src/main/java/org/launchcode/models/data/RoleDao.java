package org.launchcode.models.data;

import org.launchcode.models.Role;
import org.launchcode.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleDao extends CrudRepository<Role, Integer>  {
    public Role findByRole(String role);
}
