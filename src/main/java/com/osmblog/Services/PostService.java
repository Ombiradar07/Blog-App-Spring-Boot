package com.osmblog.Services;

import com.osmblog.Payload.PostDto;
import com.osmblog.Utils.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {

    PostDto createPost(PostDto postDto, Long userID, Long categoryId);

    PostDto updatePost(Long postId, PostDto postDto) ;

    PostDto getPostById(Long postId);

    void deletePostById(Long postId);

    Pagination getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    Pagination getAllPostsByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    Pagination getAllPostsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    Pagination findByTitleContaining(String postTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
