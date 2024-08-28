package com.osmblog.Services;

import com.osmblog.Entities.Roles;
import com.osmblog.Entities.User;
import com.osmblog.Exceptions.InvalidCredentialsException;
import com.osmblog.Exceptions.ResourceNotFoundException;
import com.osmblog.Exceptions.UserAlreadyExistException;
import com.osmblog.Payload.UserDto;
import com.osmblog.Repository.RolesRepository;
import com.osmblog.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Value("${OFFICIAL.ADMINS}")
    private String[] adminList;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolesRepository rolesRepository;


    @Override
    public UserDto signUp(UserDto userDto) {
        User user = mapToUser(userDto);
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            throw new UserAlreadyExistException("User already exists");
        }

        // Password Encryption
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assigning Roles to Users
        boolean isAdmin = Stream.of(adminList).anyMatch(email -> email.equals(user.getEmail())) || userRepository.count() == 0;

        if (isAdmin) {
            Roles adminRole = rolesRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            user.getRoles().add(adminRole);
        }

        Roles userRole = rolesRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        // Add ROLE_USER only if it's not an admin or if both roles are needed
        if (!isAdmin || !user.getRoles().contains(userRole)) {
            user.getRoles().add(userRole);
        }

        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }


    @Override
    public String signIn(UserDto userDto) {

        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return "Login Successful";
        } else {
            throw new InvalidCredentialsException("Password is incorrect");
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registration Not found with id :" + id));

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setAbout(userDto.getAbout());

        User updatedUser = userRepository.save(existingUser);
        return mapToDto(updatedUser);

    }

    @Override
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) throw new ResourceNotFoundException("User not found With this id :" + id);

        userRepository.deleteById(id);
    }


    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id:" + id));

        return mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public User mapToUser(UserDto userDto) {
/*
        User user = new User();
        // Mapping from userDto to user
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return user;
*/

        return modelMapper.map(userDto, User.class);
    }

    public UserDto mapToDto(User user) {
/*
        UserDto userDto = new UserDto();
        // Mapping from user to userDto
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;
*/

        return modelMapper.map(user, UserDto.class);
    }
}
