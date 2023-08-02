package com.blogapi.service.impl;

import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.PostDto;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;//call
    private ModelMapper modelMapper;
    //dependency injection work in external library when @bean is present and develop a method then spring will create a bean

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {//another way to adding dependency injection
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);//convert To Entity

        Post savedPost = postRepo.save(post);//save

        PostDto dto = mapToDto(savedPost);//again convert to DTO because i want response back in postman

        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id)
        );//if id is present then showing result in postman otherwise using lambdas here for calling & throwing exception

        PostDto dto = mapToDto(post);//convert To DTO & showing result in postman

        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        //rather than using if else condition we can use here ternary operator which is also reduced our code

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepo.findAll(pageable);

        List<Post> content = posts.getContent();//again convert to List object

        List<PostDto> postDtos = content.stream().map(post->mapToDto(post)).collect(Collectors.toList());//using streamAPI convert all entity object into DTOs

        return postDtos;
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(//if id is present then call delete method otherwise throwing exception
                ()-> new ResourceNotFoundException(id)
        );

        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        Post updatedContent = mapToEntity(postDto);
        updatedContent.setId(post.getId());

        Post UpdatedPost = postRepo.save(updatedContent);

        return mapToDto(UpdatedPost);
    }

    PostDto mapToDto(Post post) {//convert Entity to DTO method
        PostDto dto = modelMapper.map(post, PostDto.class);//this one line will reduce our code
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());

        return dto;
    }

    Post mapToEntity(PostDto postDto) {//convert DTO to Entity method
        Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();//create entity class object and convert DTO to entity
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }
}