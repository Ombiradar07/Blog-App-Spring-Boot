package com.osmblog.Services;

import com.osmblog.Payload.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto comment,Long postId ,Long userId);

    void deleteComment(Long commentId);
}
