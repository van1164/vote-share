package com.van1164.voteshare.domain

import jakarta.persistence.*
import lombok.ToString
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle


@Entity
@Table(name = "QUESTION")
data class Question(
        @Id
        @GeneratedValue
        val id: Long? = null,

        @Column(name = "question", nullable = false)
        val question: String,

        @Column(name = "vote_num")
        var voteNum: Int = 0,

        @Column(name = "question_image_url")
        val voteImageUrl: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "VOTE_ID")
        @ToString.Exclude
        var vote: Vote? = null,
) {
    fun voteSet(vote: Vote) {
        if (this.vote != null) {
            this.vote!!.questionList.remove(this)
        }
        this.vote = vote
        vote.questionList.add(this)
    }

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
