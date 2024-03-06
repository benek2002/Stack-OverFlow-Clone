package com.benek.controllers;

import com.benek.dtos.AllQuestionsResponseDto;
import com.benek.dtos.QuestionDTO;
import com.benek.dtos.QuestionSearchResponseDTO;
import com.benek.dtos.SingleQuestionDTO;
import com.benek.services.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/question")
    public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO questionDTO){
       QuestionDTO createdQuestionDTO = questionService.addQuestion(questionDTO);
       if(createdQuestionDTO == null){
           return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
       }
       return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
    }
    @GetMapping("/questions/{pageNumber}")
    public ResponseEntity<AllQuestionsResponseDto> getAllQuestions(@PathVariable int pageNumber){
        AllQuestionsResponseDto allQuestionsResponseDto = questionService.getAllQuestions(pageNumber);
        return ResponseEntity.ok(allQuestionsResponseDto);
    }

    @GetMapping("/question/{questionId}/{userId}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId, @PathVariable Long userId){
      SingleQuestionDTO singleQuestionDTO =   questionService.getQuestionById(questionId, userId);
      if(singleQuestionDTO == null){
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(singleQuestionDTO);
    }


    @GetMapping("/questions/{userId}/{pageNumber}")
    public ResponseEntity<AllQuestionsResponseDto> getQuestionsByUserId(@PathVariable Long userId, @PathVariable int pageNumber ){
        AllQuestionsResponseDto allQuestionsResponseDto = questionService.getQuestionsByUserId(userId, pageNumber);
        return ResponseEntity.ok(allQuestionsResponseDto);
    }

    @GetMapping("/search/{title}/{pageNum}")
    public ResponseEntity<?> searchQuestionByTitle(@PathVariable String title, @PathVariable int pageNum){
       QuestionSearchResponseDTO questionSearchResponseDTO = questionService.searchQuestionByTitle(title, pageNum);
       if(questionSearchResponseDTO == null){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(questionSearchResponseDTO);
    }

    @GetMapping("/question/latest/{pageNumber}")
    public ResponseEntity<?> getLatestQuestions(@PathVariable int pageNumber){
        QuestionSearchResponseDTO questionSearchResponseDTO = questionService.getLatestQuestion(pageNumber);
        if(questionSearchResponseDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionSearchResponseDTO);

    }
}
