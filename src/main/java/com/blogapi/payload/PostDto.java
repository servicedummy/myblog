package com.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotEmpty//adding validation annotations
    @Size(min = 2, message = "title should be at least 2 character")
    private String title;

    @NotEmpty(message = "description is empty")
    @Size(min = 5, message = "description should be at least 5 character")
    private String description;

    @NotEmpty(message = "content is empty")
    private String content;
}
