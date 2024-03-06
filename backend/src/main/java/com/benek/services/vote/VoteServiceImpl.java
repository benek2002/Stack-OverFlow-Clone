package com.benek.services.vote;

import com.benek.dtos.AnswerVoteDTO;
import com.benek.dtos.QuestionVoteDTO;
import com.benek.entities.*;
import com.benek.enums.VoteType;
import com.benek.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService{

    private final QuestionVoteRepository questionVoteRepository;

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final AnswerVoteRepository answerVoteRepository;

    @Override
    public QuestionVoteDTO addVoteToQuestion(QuestionVoteDTO questionVoteDTO) {

        Optional<User> optionalUser = userRepository.findById(questionVoteDTO.getUserId());
        Optional<Question> optionalQuestion = questionRepository.findById(questionVoteDTO.getQuestionId());

        if(optionalQuestion.isPresent() && optionalUser.isPresent()){
            Question question = optionalQuestion.get();
            QuestionVote questionVote = new QuestionVote();
            questionVote.setVoteType(questionVoteDTO.getVoteType());
            if(questionVoteDTO.getVoteType() == VoteType.UPVOTE){
                question.setVoteCount(question.getVoteCount() + 1);
            }else if(questionVoteDTO.getVoteType() == VoteType.DOWNVOTE){
                question.setVoteCount(question.getVoteCount() - 1);
            }
            questionRepository.save(question);
            questionVote.setQuestion(optionalQuestion.get());
            questionVote.setUser(optionalUser.get());
//            List<QuestionVote> existingVotes = questionVoteRepository.findByUserAndQuestion(optionalUser.get(), optionalQuestion.get());
//            questionVoteRepository.deleteAll(existingVotes);
            QuestionVote questionVoteDB = questionVoteRepository.save(questionVote);
            QuestionVoteDTO newQuestionVoteDTO = new QuestionVoteDTO();
            newQuestionVoteDTO.setId(questionVoteDB.getId());
            return  newQuestionVoteDTO;
        }
        return null;
    }

    @Override
    public AnswerVoteDTO addVoteToAnswer(AnswerVoteDTO answerVoteDTO) {
        Optional<User> optionalUser = userRepository.findById(answerVoteDTO.getUserId());
        Optional<Answer> optionalAnswer = answerRepository.findById(answerVoteDTO.getAnswerId());
        if(optionalUser.isPresent() && optionalAnswer.isPresent()){
            Answer existingAnswer = optionalAnswer.get();
            User existingUser = optionalUser.get();
            AnswerVote answerVote = new AnswerVote();
            answerVote.setVoteType(answerVoteDTO.getVoteType());
            answerVote.setAnswer(existingAnswer);
            answerVote.setUser(existingUser);
            if(answerVoteDTO.getVoteType().equals(VoteType.UPVOTE)){
                existingAnswer.setVoteCount(existingAnswer.getVoteCount() + 1);
            }else if(answerVoteDTO.getVoteType().equals(VoteType.UPVOTE)){
                existingAnswer.setVoteCount(existingAnswer.getVoteCount() - 1);
            }
            answerRepository.save(existingAnswer);
            AnswerVote answerVoteDB = answerVoteRepository.save(answerVote);
            AnswerVoteDTO newAnswerVoteDTO = new AnswerVoteDTO();
            newAnswerVoteDTO.setId(answerVoteDB.getId());
            return newAnswerVoteDTO;

        }
        return null;
    }
}
