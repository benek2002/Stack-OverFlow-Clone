package com.benek.entities;


import com.benek.dtos.AnswerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name="body", length = 512)
    private String body;

    private Date createdDate;

    private boolean approved = false;

    private Integer voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AnswerVote> answerVoteList;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentList;

    public AnswerDTO getAnswerDTO() {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(id);
        answerDTO.setBody(body);
        answerDTO.setQuestionId(question.getId());
        answerDTO.setApproved(approved);
        answerDTO.setUserId(user.getId());
        answerDTO.setUsername(user.getName());
        answerDTO.setCreatedDate(createdDate);
        answerDTO.setVoteCount(voteCount);
        return answerDTO;

    }
}
