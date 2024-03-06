package com.benek.controllers;


import com.benek.dtos.AnswerVoteDTO;
import com.benek.dtos.QuestionVoteDTO;
import com.benek.services.vote.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<?> addVoteToQuestion(@RequestBody QuestionVoteDTO questionVoteDTO){
        QuestionVoteDTO questionVotedDTO = voteService.addVoteToQuestion(questionVoteDTO);
        if(questionVotedDTO == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(questionVotedDTO);
    }

    @PostMapping("/answer-vote")
    public ResponseEntity<?> addVoteToAnswer(@RequestBody AnswerVoteDTO answerVoteDTO){
           AnswerVoteDTO createdAnswerVoteDTO = voteService.addVoteToAnswer(answerVoteDTO);
           if(createdAnswerVoteDTO == null){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
           }
           return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerVoteDTO);
    }
}
