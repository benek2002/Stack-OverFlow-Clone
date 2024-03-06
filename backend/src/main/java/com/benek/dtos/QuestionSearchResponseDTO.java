package com.benek.dtos;

import lombok.Data;

import java.util.List;

@Data
public class QuestionSearchResponseDTO {
    private List<QuestionDTO> questionDTOList;
    private Integer totalPages;
    private Integer pageNumber;
}
