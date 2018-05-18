package com.nevreme.rolling.dto;

import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AnswerDto {
    private Long id;
    private String text;
    private int upVotes;
    private int downVotes;
    private AnswerDto replyAnswer;
    private VisitorDto visitor;
    private Set<VoteDto> votes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public AnswerDto getReplyAnswer() {
        return replyAnswer;
    }

    public void setReplyAnswer(AnswerDto replyAnswer) {
        this.replyAnswer = replyAnswer;
    }

    public VisitorDto getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorDto visitor) {
        this.visitor = visitor;
    }

    public Set<VoteDto> getVotes() {
        return votes;
    }

    public void setVotes(Set<VoteDto> votes) {
        this.votes = votes;
    }
}
