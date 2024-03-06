package com.benek.services.vote;

import com.benek.dtos.AnswerVoteDTO;
import com.benek.dtos.QuestionVoteDTO;

public interface VoteService {

    QuestionVoteDTO addVoteToQuestion(QuestionVoteDTO questionVoteDTO);

    AnswerVoteDTO addVoteToAnswer(AnswerVoteDTO answerVoteDTO);
}
