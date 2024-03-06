package com.benek.dtos;

import com.benek.entities.Image;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AnswerDTO {

    private Long id;

    private String body;

    private Long questionId;
    private Date createdDate;

    private boolean approved = false;

    private Long userId;
    private String username;
    private Image file;
    private int voted;
    private Integer voteCount;
    private List<CommentDTO> commentDTOList;
}
