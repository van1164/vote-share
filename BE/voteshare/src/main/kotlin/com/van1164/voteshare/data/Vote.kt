package com.van1164.voteshare.data

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name ="VOTE")
data class Vote(
        @Id
        @GeneratedValue
        @Column(name = "VOTE_ID")
        val id : Long? = null,

        @Column(name = "main_image_url")
        val mainImageUrl : String? =null,

        @Column(name = "title")
        val title : String,

        @Column(name = "sub_title")
        val subTitle : String,

        @Column(name= "all_vote_sum")
        val allVoteSum : Int,

        @Column(name = "vote_url")
        val voteUrl : String,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_date", nullable = false)
        val createDate : Date,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "updated_date")
        val updatedDate : Date,

        @OneToMany(mappedBy = "vote")
        val questionList : MutableList<Question> = mutableListOf<Question>(),

        @ManyToOne
        @JoinColumn(name = "USER_ID", nullable = false)
        val user: User,
)
