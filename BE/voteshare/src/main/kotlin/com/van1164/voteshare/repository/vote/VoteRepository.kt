package com.van1164.voteshare.repository.vote

import com.van1164.voteshare.domain.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VoteRepository : JpaRepository<Vote, Long>, VoteCustomRepository {
}