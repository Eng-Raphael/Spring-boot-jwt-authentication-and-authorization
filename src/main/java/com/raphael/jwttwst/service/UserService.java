package com.raphael.jwttwst.service;


import com.raphael.jwttwst.entity.User;
import com.raphael.jwttwst.exception.NoDataFoundException;
import com.raphael.jwttwst.repo.UserRepo;
import com.raphael.jwttwst.utils.PageUtil;
import com.raphael.jwttwst.utils.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);


    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return userRepo.save(user) ;

    }

    public boolean checkIfUserExsists(String username){
        return userRepo.existsByUsername(username);
    }

    public PagedResponse<User> getAllUsers(int page, int size) {
        PageUtil.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> usersPage = userRepo.findAll(pageable);
        if (usersPage.getContent().isEmpty()) {
            throw new NoDataFoundException("No users found for the requested page");
        }
        return new PagedResponse<>(usersPage);
    }

}
