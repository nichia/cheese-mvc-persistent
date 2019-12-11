package org.launchcode.service;

import org.launchcode.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void saveUser(User user);
    public boolean isUserAlreadyPresent(User user);

}
