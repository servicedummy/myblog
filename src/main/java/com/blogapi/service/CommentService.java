package com.blogapi.service;

import com.blogapi.entity.Comment;
import com.blogapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long id, CommentDto commentDto);

    List<CommentDto> findByPostId(long postId);
    void deleteComment(long postId, long commentId);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
}
