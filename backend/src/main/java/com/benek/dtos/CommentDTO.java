package com.benek.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {

    private Long id;

    private String body;

    private Date createdDate;

    private Long answerId;
    private Long userId;

    private String username;
}
