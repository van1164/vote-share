package com.van1164.voteshare

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.EntityTransaction
import jakarta.persistence.Persistence
import jakarta.persistence.PersistenceUnit

object EntityManagerObject {
    val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("voteJpa")
    val em: EntityManager = emf.createEntityManager()
    val tx: EntityTransaction = em.transaction
}