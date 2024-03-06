package com.benek.entities;

import com.benek.dtos.QuestionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(name="body", length = 512)
    private String body;

    private Integer voteCount = 0;


    private Date createdDate;
    @ElementCollection
    private List<String> tags;

    @ManyToOne(fetch = FetchType.LAZY,optional = false )
    @JoinColumn(name= "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<QuestionVote> questionVoteList;

    public QuestionDTO getQuestionDto(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(getId());
        questionDTO.setTitle(getTitle());
        questionDTO.setBody(getBody());
        questionDTO.setTags(getTags());
        questionDTO.setUserId(getUser().getId());
        questionDTO.setUsername(getUser().getName());
        questionDTO.setDate(getCreatedDate());
        questionDTO.setVoteCount(getVoteCount());
        return questionDTO;

    }
}
