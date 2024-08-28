package com.osmblog.Services;

import com.osmblog.Entities.Comment;
import com.osmblog.Entities.Post;
import com.osmblog.Entities.User;
import com.osmblog.Exceptions.ResourceNotFoundException;
import com.osmblog.Payload.CommentDto;
import com.osmblog.Repository.CommentRepository;
import com.osmblog.Repository.PostRepository;
import com.osmblog.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId, Long userId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with PostId: " + postId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with UserId: " + userId));

        Comment comment = modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }
}
