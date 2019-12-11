package org.launchcode.service;

import org.launchcode.models.Role;
import org.launchcode.models.User;
import org.launchcode.models.data.RoleDao;
import org.launchcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserDao userDao;

    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus("VERIFIED");
        Role userRole = roleDao.findByRole("SITE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userDao.save(user);
    }

    @Override
    public boolean isUserAlreadyPresent(User user) {
        boolean isUserAlreadyExists = false;
        User existingUser = userDao.findByEmail(user.getEmail());
        // If user is found in database, then then user already exists.
        if(existingUser != null){
            isUserAlreadyExists = true;
        }
        return isUserAlreadyExists;
    }
}
