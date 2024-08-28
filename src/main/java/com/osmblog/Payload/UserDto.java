package com.osmblog.Payload;

import com.osmblog.Entities.Comment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class UserDto {


    private Long id;

    @NotEmpty
    @Size(min=4, message =  "Username must be minimum of 4 chars!!")
    private String name;

    @Email(message ="Please enter a valid email")
    private String email;

    @Size(min = 8, message = " Password should contains at least 8 characters!! ")
    private String password;

    private String about;
}
