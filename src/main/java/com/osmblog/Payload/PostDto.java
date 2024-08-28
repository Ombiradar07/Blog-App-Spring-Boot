package com.osmblog.Payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Long postId;

    @NotEmpty(message = "Post title should not be empty")
    private String postTitle;

    @NotEmpty(message = "Post content should not be empty")
    private String postContent;

    // Automatically set while creating the post
    private Date addedDate;


    private CategoryDto categoryDto;

    private UserDto userDto;

    private List<CommentDto> comments = new ArrayList<>();

}
