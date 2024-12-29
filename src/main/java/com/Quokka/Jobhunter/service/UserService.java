package com.Quokka.Jobhunter.service;

import com.Quokka.Jobhunter.domain.User;
import com.Quokka.Jobhunter.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  void handlCreateUser(User user){
        this.userRepository.save(user);
    }
}
