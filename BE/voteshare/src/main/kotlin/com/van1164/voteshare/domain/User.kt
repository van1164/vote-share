package com.van1164.voteshare.domain

import jakarta.persistence.*
import lombok.Builder
import lombok.NoArgsConstructor

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
    val accessToken: String,

    @OneToMany(mappedBy = "user")
    val voteList: MutableList<Vote> = mutableListOf(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val oAuth2Provider : OAuth2Provider,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role : Role
)