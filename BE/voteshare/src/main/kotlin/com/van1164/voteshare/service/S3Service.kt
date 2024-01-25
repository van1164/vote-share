package com.van1164.voteshare.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Service
class S3Service(val amazonS3Client : AmazonS3) {

    suspend fun uploadMultipleImages(images : List<MultipartFile>): List<String> {
        val imageUrls = mutableListOf<String>()
        withContext(Dispatchers.IO) {
            val uploadJobs = images.map {
                val objectMetadata = ObjectMetadata().apply {
                    this.contentType = it.contentType
                    this.contentLength = it.size
                }
                val key = UUID.randomUUID().toString() + it.contentType
                imageUrls.add(key)
                async {
                    val putObjectRequest = PutObjectRequest(
                            "vote-share",
                            key,
                            it.inputStream,
                            objectMetadata,
                    )
                    amazonS3Client.putObject(putObjectRequest)
                }.await()
            }
        }
        return imageUrls.toList()
    }

    fun uploadImage(image: MultipartFile): String {
        val key = UUID.randomUUID().toString() + image.contentType
        val objectMetadata = ObjectMetadata().apply {
            this.contentType = image.contentType
            this.contentLength = image.size
        }
        val putObjectRequest = PutObjectRequest(
            "vote-share",
            key,
            image.inputStream,
            objectMetadata,
        )
        amazonS3Client.putObject(putObjectRequest)
        return key
    }

}