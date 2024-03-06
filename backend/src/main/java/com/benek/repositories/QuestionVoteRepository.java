package com.benek.repositories;

import com.benek.entities.Question;
import com.benek.entities.QuestionVote;
import com.benek.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
    List<QuestionVote> findByUserAndQuestion(User user, Question question);

}
