package com.van1164.voteshare.domain

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name ="VOTE")
data class Vote(

        @Column(name = "title")
        val title : String,

        @Column(name = "sub_title")
        val subTitle : String,


        @Column(name = "vote_url")
        val voteUrl : String,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_date", nullable = false)
        val createDate : Date,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "updated_date")
        val updatedDate : Date,


        @ManyToOne
        @JoinColumn(name = "USER_ID", nullable = false)
        val user: User,

        @Column(name = "max_select_item")
        val maxSelectItem : Int,

        @Id
        @GeneratedValue
        @Column(name = "VOTE_ID")
        val id : Long? = null,

        @Column(name = "main_image_url")
        val mainImageUrl : String? =null,

        @OneToMany(mappedBy = "vote")
        val questionList : MutableList<Question> = mutableListOf<Question>(),

        @Column(name= "all_vote_sum")
        var allVoteSum : Int = 0,

        @Column(name = "public_share")
        val publicShare : Boolean = true,

)
