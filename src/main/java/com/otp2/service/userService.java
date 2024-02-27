package com.otp2.service;

import com.otp2.payload.UserDto;

public interface userService {

    public UserDto registerUser(UserDto userDto);


    public UserDto getUserByEmail(String email);

    public void verifyEmail(UserDto userDto);
    public boolean isEmailverified(String email);

}
