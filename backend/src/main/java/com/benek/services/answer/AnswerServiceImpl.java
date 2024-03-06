package com.benek.services.answer;

import com.benek.dtos.AnswerDTO;
import com.benek.dtos.CommentDTO;
import com.benek.entities.Answer;
import com.benek.entities.Comment;
import com.benek.entities.Question;
import com.benek.entities.User;
import com.benek.repositories.AnswerRepository;
import com.benek.repositories.CommentRepository;
import com.benek.repositories.QuestionRepository;
import com.benek.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService{
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;
    @Override
    public AnswerDTO postAnswer(AnswerDTO answerDTO) {
        Optional<User> optionalUser = userRepository.findById(answerDTO.getUserId());
        Optional< Question> optionalQuestion = questionRepository.findById(answerDTO.getQuestionId());
        if(optionalUser.isPresent() && optionalQuestion.isPresent()){
            Answer answer = new Answer();
            answer.setBody(answerDTO.getBody());
            answer.setCreatedDate(new Date());
            answer.setUser(optionalUser.get());
            answer.setQuestion(optionalQuestion.get());
            Answer createdAnswer = answerRepository.save(answer);
            AnswerDTO createdAnswerDTO = new AnswerDTO();
            createdAnswerDTO.setId(createdAnswer.getId());
            return createdAnswerDTO;
        }
        return null;
    }

    @Override
    public AnswerDTO approveAnswer(Long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if(optionalAnswer.isPresent()){
            Answer answer = optionalAnswer.get();
            answer.setApproved(true);
            Answer updatedAnswer =  answerRepository.save(answer);
            AnswerDTO updatedAnswerDTO = new AnswerDTO();
            updatedAnswerDTO.setId(updatedAnswer.getId());
            return updatedAnswerDTO;

        }
        return null;
    }

    @Override
    public CommentDTO postCommentToAnswer(CommentDTO commentDTO) {
        Optional<Answer> optionalAnswer = answerRepository.findById(commentDTO.getAnswerId());
        Optional<User> optionalUser = userRepository.findById(commentDTO.getUserId());
        if(optionalAnswer.isPresent() && optionalUser.isPresent()){
            Comment comment = new Comment();
            comment.setBody(commentDTO.getBody());
            comment.setCreatedDate(new Date());
            comment.setAnswer(optionalAnswer.get());
            comment.setUser(optionalUser.get());
            Comment postedComment = commentRepository.save(comment);
            CommentDTO postedCommentDTO = new CommentDTO();
            postedCommentDTO.setId(postedComment.getId());
            return postedCommentDTO;
        }
        return null;
    }
}
