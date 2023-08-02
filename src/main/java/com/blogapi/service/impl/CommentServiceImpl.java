package com.blogapi.service.impl;

import com.blogapi.entity.Comment;
import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.CommentDto;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;
    private CommentRepository commentRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo,ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long id, CommentDto commentDto) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        Comment comment = mapToEntity(commentDto);

//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        comment.setPost(post);//setting comment with that post

        Comment savedComment = commentRepo.save(comment);

        CommentDto dto = mapToDto(savedComment);

//        CommentDto dto = new CommentDto();
//        dto.setId(savedComment.getId());
//        dto.setName(savedComment.getName());
//        dto.setEmail(savedComment.getEmail());
//        dto.setBody(savedComment.getBody());

        return dto;
    }

    @Override
    public List<CommentDto> findByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        List<Comment> comments = commentRepo.findByPostId(postId);

        return comments.stream().map(comment-> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(commentId)
        );
        commentRepo.deleteById(commentId);
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(commentId)
        );
        CommentDto dto = mapToDto(comment);

        return dto;
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException(commentId)
        );
        Comment updatedComment = mapToEntity(commentDto);
        updatedComment.setId(comment.getId());
        updatedComment.setPost(post);

        Comment savedComment = commentRepo.save(updatedComment);

        return mapToDto(savedComment);
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }
    Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

}
