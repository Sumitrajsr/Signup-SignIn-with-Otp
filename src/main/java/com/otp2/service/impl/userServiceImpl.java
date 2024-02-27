package com.otp2.service.impl;

import com.otp2.entity.User;
import com.otp2.exception.ResourceNotFoundException;
import com.otp2.payload.UserDto;
import com.otp2.repository.UserRepository;
import com.otp2.service.userService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser == null) {
            User user = modelMapper.map(userDto, User.class);
            User savedUser = userRepository.save(user);
            return modelMapper.map(savedUser, UserDto.class);
        } else {
            throw new ResourceNotFoundException("Email already registered");
        }
    }


    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return modelMapper.map(user,UserDto .class);
    }

    @Override
    public void verifyEmail(UserDto userDto) {
        User user=modelMapper.map(userDto,User.class);
        user.setEmailVerified(true);
        userRepository.save(user);

    }

    @Override
    public boolean isEmailverified(String email)  {
        User user = userRepository.findByEmail(email);
        return user !=null && user.isEmailVerified();
    }
}
