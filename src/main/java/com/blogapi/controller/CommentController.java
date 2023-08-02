package com.blogapi.controller;

import com.blogapi.payload.CommentDto;
import com.blogapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//create comment with postId
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> findByPostId(@PathVariable("postId") long postId){//get all comment with postId
        List<CommentDto> dtos = commentService.findByPostId(postId);
        return dtos;
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")//giving both postId & commentId in url
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId, @PathVariable("id") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment is deleted", HttpStatus.OK);//delete comment with postId & commentId
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId, @PathVariable("id") long commentId){
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);//get comment with particular postId & commentId
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId, @PathVariable("id") long commentId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);//update comment with postId & commentId
    }

}
