package com.van1164.voteshare.data

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.hibernate.annotations.Type

@Entity
@NoArgsConstructor
@Table(name = "USER")
data class User(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(name = "nick_name")
    val nickName: String,

    @Column(name = "access_token")
    val accessToken: String,

    @OneToMany(mappedBy = "user")
    val voteList: MutableList<Vote> = mutableListOf(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val oAuth2Provider : OAuth2Provider


)