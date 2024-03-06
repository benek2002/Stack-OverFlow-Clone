package com.benek.services.question;

import com.benek.dtos.AllQuestionsResponseDto;
import com.benek.dtos.QuestionDTO;
import com.benek.dtos.QuestionSearchResponseDTO;
import com.benek.dtos.SingleQuestionDTO;

public interface QuestionService {
    QuestionDTO addQuestion(QuestionDTO questionDTO);

    AllQuestionsResponseDto getAllQuestions(int pageNumber);

    SingleQuestionDTO getQuestionById(Long id, Long userId);

    AllQuestionsResponseDto getQuestionsByUserId(Long userId, int pageNumber);

    QuestionSearchResponseDTO searchQuestionByTitle(String title, int pageNum);

    QuestionSearchResponseDTO getLatestQuestion(int pageNum);
}
