package com.benek.repositories;

import com.benek.entities.Answer;
import com.benek.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByAnswer(Answer answer);

}
