package com.benek.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SingleQuestionDTO {

        private QuestionDTO questionDTO;

        private List<AnswerDTO> answerDTOList;
}
