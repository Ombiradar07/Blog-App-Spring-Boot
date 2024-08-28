package com.osmblog.Controllers;

import com.osmblog.Payload.CommentDto;
import com.osmblog.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    CommentService commentService;

    // Create a new comment
    @PostMapping("/user/{userId}/posts/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Long postId, @PathVariable Long userId) {
        CommentDto createdComment = commentService.createComment(comment, postId, userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Delete a comment
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

}
