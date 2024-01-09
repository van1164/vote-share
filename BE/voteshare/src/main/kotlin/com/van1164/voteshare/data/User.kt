package com.van1164.voteshare.data

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*

@Entity
@Table(name = "USER")
data class User(
    @Id
    @GeneratedValue
    val id: String,

    @Column(name = "nick_name")
    val nickName: String,

    @Column(name = "access_token")
    val accessToken: String,

    @Column(name = "vote_list", columnDefinition = "json")
    val voteList: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val oAuth2Provider : OAuth2Provider
)