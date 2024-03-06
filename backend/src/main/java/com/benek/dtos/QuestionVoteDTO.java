package com.benek.dtos;

import com.benek.enums.VoteType;
import lombok.Data;

@Data
public class QuestionVoteDTO {

    private Long id;

    private VoteType voteType;

    private Long userId;

    private Long questionId;
}
