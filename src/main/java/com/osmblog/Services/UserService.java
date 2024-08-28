package com.osmblog.Services;

import com.osmblog.Payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public  UserDto signUp(UserDto userDto);
    public String signIn(UserDto userDto);
    public UserDto updateUser(Long id ,UserDto userDto);
    public void deleteUser(Long id);
    public UserDto getUserById(Long id);
    public List<UserDto> getAllUsers();
}
