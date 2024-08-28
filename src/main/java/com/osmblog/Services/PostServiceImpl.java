package com.osmblog.Services;

import com.osmblog.Entities.Category;
import com.osmblog.Entities.Post;
import com.osmblog.Entities.User;
import com.osmblog.Exceptions.ResourceNotFoundException;
import com.osmblog.Payload.PostDto;
import com.osmblog.Repository.CategoryRepository;
import com.osmblog.Repository.PostRepository;
import com.osmblog.Repository.UserRepository;
import com.osmblog.Utils.Pagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public PostDto createPost(PostDto postDto, Long userID, Long categoryId)  {
        User user = userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + userID));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with this id: " + categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto)  {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());

        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }
    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        postRepository.deleteById(postId);
    }

    @Override
    public Pagination getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> page = postRepository.findAll(pageable);
        List<PostDto> pageDetails = page.getContent().stream().map((p) -> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());

        Pagination pagination = new Pagination();
        pagination.setPosts(pageDetails);
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        pagination.setLastPage(page.isLast());

        return pagination;
    }

    @Override
    public Pagination getAllPostsByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + userId));

        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> page = postRepository.findByUser(user, pageable);
        List<PostDto> pageDetails = page.getContent().stream().map((p) -> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());

        Pagination pagination = new Pagination();
        pagination.setPosts(pageDetails);
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        pagination.setLastPage(page.isLast());

        return pagination;
    }

    @Override
    public Pagination getAllPostsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with this id: " + categoryId));

        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> page = postRepository.findByCategory(category, pageable);
        List<PostDto> pageDetails = page.getContent().stream().map((p) -> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());

        Pagination pagination = new Pagination();
        pagination.setPosts(pageDetails);
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        pagination.setLastPage(page.isLast());

        return pagination;
    }

    @Override
    public Pagination findByTitleContaining(String postTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> page  = postRepository.findByPostTitleContaining(postTitle, pageable);
        List<PostDto> pageDetails = page.getContent().stream().map((p) -> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());

        Pagination pagination = new Pagination();
        pagination.setPosts(pageDetails);
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        pagination.setLastPage(page.isLast());

        return pagination;
    }
}
