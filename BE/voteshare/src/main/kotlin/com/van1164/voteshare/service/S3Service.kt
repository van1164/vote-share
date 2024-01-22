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

    suspend fun uploadMultipleImages(images : List<MultipartFile>){
        withContext(Dispatchers.IO) {
            val uploadJobs = images.map {
                val objectMetadata = ObjectMetadata().apply {
                    this.contentType = it.contentType
                    this.contentLength = it.size
                }
                async {
                    val putObjectRequest = PutObjectRequest(
                            "vote-share",
                            UUID.randomUUID().toString() + it.contentType,
                            it.inputStream,
                            objectMetadata,
                    )
                    amazonS3Client.putObject(putObjectRequest)
                }
            }
            uploadJobs.awaitAll()
        }
    }

}