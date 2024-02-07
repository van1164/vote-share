package com.van1164.voteshare.domain

import jakarta.persistence.*
import lombok.ToString
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.util.*


@Entity
@Table(name ="VOTE")
@ToString(exclude = ["user","questionList"])
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


        @ManyToOne(fetch = FetchType.LAZY)
        @ToString.Exclude
        @JoinTable(
                name = "VOTE_USER",
                joinColumns = [JoinColumn(name = "VOTE_ID")],
                inverseJoinColumns = [JoinColumn(name = "user_id")]
        )
        val user: User,

        @Column(name = "max_select_item")
        val maxSelectItem : Int,

        @Id
        @GeneratedValue
        @Column(name = "VOTE_ID")
        val id : Long? = null,

        @Column(name = "main_image_url")
        val mainImageUrl : String? =null,

        @OneToMany(mappedBy = "vote", fetch = FetchType.LAZY)
        @ToString.Exclude
        val questionList : MutableList<Question> = mutableListOf<Question>(),

        @Column(name= "all_vote_sum")
        var allVoteSum : Int = 0,

        @Column(name = "public_share")
        val publicShare : Boolean = true,

){

        override fun toString(): String {
                return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
        }
}
