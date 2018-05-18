package com.nevreme.rolling.dto;

import com.nevreme.rolling.model.Answer;
import com.nevreme.rolling.model.Visitor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Component
public class VoteDto {
    private Long id;
    private boolean vote;
//    private VisitorDto visitor;
//    private AnswerDto answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

//    public VisitorDto getVisitor() {
//        return visitor;
//    }
//
//    public void setVisitor(VisitorDto visitor) {
//        this.visitor = visitor;
//    }
//
//    public AnswerDto getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(AnswerDto answer) {
//        this.answer = answer;
//    }
}
