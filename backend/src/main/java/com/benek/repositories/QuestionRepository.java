package com.benek.repositories;

import com.benek.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByUserId(Long userId, Pageable paging);


    Page<Question> findAllByTitleContaining(String title, Pageable pageable);

    Page<Question> findAllByOrderByCreatedDateDesc(Pageable paging);
}


