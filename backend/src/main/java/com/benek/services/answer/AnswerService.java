package com.benek.services.answer;

import com.benek.dtos.AnswerDTO;
import com.benek.dtos.CommentDTO;
import com.benek.entities.Comment;

public interface AnswerService {
    AnswerDTO postAnswer(AnswerDTO answerDTO);

    AnswerDTO approveAnswer(Long answerId);

    CommentDTO postCommentToAnswer(CommentDTO commentDTO);
}
