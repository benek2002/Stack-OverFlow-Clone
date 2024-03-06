package com.benek.controllers;

import com.benek.dtos.AnswerDTO;
import com.benek.dtos.CommentDTO;
import com.benek.entities.Answer;
import com.benek.entities.Comment;
import com.benek.services.answer.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    private ResponseEntity<?> addAnswer(@RequestBody AnswerDTO answerDTO){
       AnswerDTO createdAnswerDto =  answerService.postAnswer(answerDTO);
       if(createdAnswerDto == null){
           return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(createdAnswerDto, HttpStatus.CREATED);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerDTO> approveAnswer(@PathVariable Long answerId){
        AnswerDTO approvedAnswerDTO = answerService.approveAnswer(answerId);
        if(approvedAnswerDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(approvedAnswerDTO);

    }

    @PostMapping("/comment")
    public ResponseEntity<?> postCommentToAnswer(@RequestBody CommentDTO commentDTO){
        CommentDTO postedCommentDTO = answerService.postCommentToAnswer(commentDTO);
        if(postedCommentDTO == null){
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postedCommentDTO);
    }
}
