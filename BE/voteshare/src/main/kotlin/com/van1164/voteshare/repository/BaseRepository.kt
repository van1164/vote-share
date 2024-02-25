package com.van1164.voteshare.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class BaseRepository(

) {
    @PersistenceContext
    lateinit var em : EntityManager

    @Autowired
    lateinit var queryFactory : JPAQueryFactory
}