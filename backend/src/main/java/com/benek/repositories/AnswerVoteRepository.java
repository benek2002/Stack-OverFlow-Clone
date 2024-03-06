package com.benek.repositories;

import com.benek.entities.AnswerVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
}
