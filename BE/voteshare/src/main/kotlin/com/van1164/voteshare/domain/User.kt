package com.van1164.voteshare.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import lombok.Builder
import lombok.NoArgsConstructor
import lombok.ToString
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

@Entity
@NoArgsConstructor
@NamedQuery(
    name = "User.findById",
    query = "select u from User u where u.email =: email"
)
@Table(name = "USER")
data class User(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(name = "nick_name")
    val nickName: String,

    @Column(name = "e_mail")
    val email: String,

    @Column(name = "access_token")
    var accessToken: String,

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    val voteList: MutableList<Vote> = mutableListOf(),

    @Column(name="o_auth_provider",nullable = false)
    @Enumerated(EnumType.STRING)
    val oAuth2Provider : OAuth2Provider,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role : Role

){
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}