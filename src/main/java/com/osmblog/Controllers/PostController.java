package com.osmblog.Controllers;

import com.osmblog.Payload.PostDto;
import com.osmblog.Services.PostService;
import com.osmblog.Utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    // Create a new post
    @PostMapping("/user/{userID}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createNewPost(@RequestBody PostDto postDto,
                                                 @PathVariable Long userID,
                                                 @PathVariable Long categoryId)
    {
        PostDto dto = postService.createPost(postDto, userID, categoryId);
        return new ResponseEntity<PostDto>(dto, HttpStatus.CREATED);
    }

    // Update an existing post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId,
                                              @RequestBody PostDto postDto)
    {
        PostDto updatedPost = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // Delete a post
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post deleted successfully!!!", HttpStatus.OK);
    }

    // Retrieve a specific post by ID
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Retrieve all posts
    @GetMapping("/posts")
    public ResponseEntity<Pagination> getAllPosts(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "postTitle", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "ASC", required = false) String sortDir) {
        Pagination allPosts = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    // Retrieve posts by user ID
    @GetMapping("/user/{userID}/posts")
    public ResponseEntity<Pagination> getAllPostsByUserId(
            @PathVariable Long userID,
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "postTitle", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "ASC", required = false) String sortDir) {
        Pagination allPostsByUserId = postService.getAllPostsByUserId(userID, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPostsByUserId, HttpStatus.OK);
    }

    // Retrieve posts by category ID
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<Pagination> getAllPostsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "postTitle", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "ASC", required = false) String sortDir) {
        Pagination allPostsByCategoryId = postService.getAllPostsByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPostsByCategoryId, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<Pagination> findByTitleContaining(
            @PathVariable String keywords,
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "postTitle", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "ASC", required = false) String sortDir) {
        Pagination searchResult = postService.findByTitleContaining(keywords, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
