package com.van1164.voteshare.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(name = "QUESTION")
data class Question(
        @Id
        @GeneratedValue
        val id: Long? = null,

        @Column(name = "question", nullable = false)
        val question: String,

        @Column(name = "vote_num")
        val voteNum: Int = 0,

        @Column(name = "question_image_url")
        val voteImageUrl: String? = null,

        @ManyToOne
        @JoinColumn(name = "VOTE_ID")
        var vote: Vote? = null,
) {
    fun voteSet(vote: Vote) {
        if (this.vote != null) {
            this.vote!!.questionList.remove(this)
        }
        this.vote = vote
        vote.questionList.add(this)
    }
}
