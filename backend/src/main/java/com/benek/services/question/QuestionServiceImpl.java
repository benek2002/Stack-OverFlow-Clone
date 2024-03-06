package com.benek.services.question;

import com.benek.dtos.*;
import com.benek.entities.*;
import com.benek.enums.VoteType;
import com.benek.repositories.AnswerRepository;
import com.benek.repositories.ImageRepository;
import com.benek.repositories.QuestionRepository;
import com.benek.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService{
    public static final int SEARCH_RESULT_PER_PAGE = 5;

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final ImageRepository imageRepository;


    @Override
    public QuestionDTO addQuestion(QuestionDTO questionDTO) {
        Optional<User> optionalUser = userRepository.findById(questionDTO.getUserId());
        if( optionalUser.isPresent()){
            Question question = new Question();
            question.setTitle(questionDTO.getTitle());
            question.setBody(questionDTO.getBody());
            question.setTags(questionDTO.getTags());
            question.setCreatedDate(new Date());
            question.setUser(optionalUser.get());
            Question createdQuestion = questionRepository.save(question);
            QuestionDTO createdQuestionDTO = new QuestionDTO();
            createdQuestionDTO.setId(createdQuestion.getId());
            createdQuestionDTO.setTitle(createdQuestion.getTitle());
            return createdQuestionDTO;
        }
        return null;
    }

    @Override
    public AllQuestionsResponseDto getAllQuestions(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Question> questionsPage = questionRepository.findAll(paging);
        AllQuestionsResponseDto allQuestionsResponseDto = new AllQuestionsResponseDto();
        allQuestionsResponseDto.setQuestionDTOList(questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()));
        allQuestionsResponseDto.setPageNumber(questionsPage.getPageable().getPageNumber());
        allQuestionsResponseDto.setTotalPages(questionsPage.getTotalPages());
        return allQuestionsResponseDto;
    }

    @Override
    public SingleQuestionDTO getQuestionById(Long id, Long userId) {
       Optional<Question> optionalQuestion =  questionRepository.findById(id);
       if(optionalQuestion.isPresent()){
           SingleQuestionDTO singleQuestionDTO = new SingleQuestionDTO();
           List<AnswerDTO> answerDTOList = new ArrayList<>();
           Question existingQuestion = optionalQuestion.get();
           Optional<QuestionVote> optionalQuestionVote = existingQuestion.getQuestionVoteList().stream().filter(questionVote -> questionVote.getUser().getId().equals(userId)).findFirst();
           QuestionDTO questionDTO = optionalQuestion.get().getQuestionDto();
           questionDTO.setVoted(0);
           if(optionalQuestionVote.isPresent()){
               if(optionalQuestionVote.get().getVoteType().equals(VoteType.UPVOTE)) {
                   questionDTO.setVoted(1);
               }else{
                   questionDTO.setVoted(-1);
               }
           }
           singleQuestionDTO.setQuestionDTO(questionDTO);
           List<Answer> answerList = answerRepository.findAllByQuestionId(id);
            for(Answer answer : answerList){
                if(answer.isApproved()){
                    singleQuestionDTO.getQuestionDTO().setHasApprovedAnswer(true);
                }
                AnswerDTO answerDTO = answer.getAnswerDTO();
                Optional<AnswerVote> optionalAnswerVote = answer.getAnswerVoteList().stream().filter(answerVote -> answerVote.getUser().getId().equals(userId)).findFirst();
                answerDTO.setVoted(0);
                if(optionalAnswerVote.isPresent()){
                    if(optionalAnswerVote.get().getVoteType().equals(VoteType.UPVOTE)){
                        answerDTO.setVoted(1);
                    }else{
                        answerDTO.setVoted(-1);
                    }
                }
                answerDTO.setFile(imageRepository.findByAnswer(answer));
                answerDTOList.add(answerDTO);
                List<CommentDTO> commentDTOList = new ArrayList<>();
                answer.getCommentList().forEach(comment -> {
                    CommentDTO commentDTO = comment.getCommentDTO();
                    commentDTOList.add(commentDTO);
                });
                answerDTO.setCommentDTOList(commentDTOList);
            }
            singleQuestionDTO.setAnswerDTOList(answerDTOList);
            return singleQuestionDTO;
       }
       return null;



    }

    @Override
    public AllQuestionsResponseDto getQuestionsByUserId(Long userId, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Question> questionsPage = questionRepository.findAllByUserId(userId,paging);
        AllQuestionsResponseDto allQuestionsResponseDto = new AllQuestionsResponseDto();
        allQuestionsResponseDto.setQuestionDTOList(questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()));
        allQuestionsResponseDto.setPageNumber(questionsPage.getPageable().getPageNumber());
        allQuestionsResponseDto.setTotalPages(questionsPage.getTotalPages());
        return allQuestionsResponseDto;
    }

    @Override
    public QuestionSearchResponseDTO searchQuestionByTitle(String title, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, SEARCH_RESULT_PER_PAGE);
        Page<Question> questionPage;
        if ( title == null || title.equals("null")) {
            questionPage = questionRepository.findAll(pageable);
        }else {
           questionPage =  questionRepository.findAllByTitleContaining(title, pageable);
           QuestionSearchResponseDTO questionSearchResponseDTO = new QuestionSearchResponseDTO();
           questionSearchResponseDTO.setQuestionDTOList(questionPage.stream().map(Question::getQuestionDto).collect(Collectors.toList()));
           questionSearchResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
           questionSearchResponseDTO.setTotalPages(questionPage.getTotalPages());
           return questionSearchResponseDTO;
        }
        return null;
    }

    @Override
    public QuestionSearchResponseDTO getLatestQuestion(int pageNum) {
        Pageable paging = PageRequest.of(pageNum, SEARCH_RESULT_PER_PAGE);
        Page<Question> questionPage;
        questionPage = questionRepository.findAllByOrderByCreatedDateDesc(paging);
        QuestionSearchResponseDTO questionSearchResponseDTO = new QuestionSearchResponseDTO();
        questionSearchResponseDTO.setQuestionDTOList(questionPage.stream().map(Question::getQuestionDto).collect(Collectors.toList()));
        questionSearchResponseDTO.setTotalPages(questionPage.getTotalPages());
        questionSearchResponseDTO.setPageNumber(questionPage.getPageable().getPageNumber());
        return questionSearchResponseDTO;
    }
}
