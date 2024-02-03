package com.van1164.voteshare.controller

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID


@RestController
@RequestMapping("/")
@PreAuthorize("isAuthenticated()")
class S3TestController(val amazonS3Client : AmazonS3) {



    @PostMapping("/multipart-files")
    suspend fun uploadMultipleFilesWithCoroutine(
            @RequestPart("uploadFiles") multipartFiles: List<MultipartFile>,
            @RequestParam type: String,
    ) = withContext(Dispatchers.IO) {
        val uploadJobs = multipartFiles.map {
            val objectMetadata = ObjectMetadata().apply {
                this.contentType = it.contentType
                this.contentLength = it.size
            }
            async {
                val putObjectRequest = PutObjectRequest(
                        "vote-share",
                        UUID.randomUUID().toString() + type,
                        it.inputStream,
                        objectMetadata,
                )
                amazonS3Client.putObject(putObjectRequest)
            }
        }
        uploadJobs.awaitAll()
        return@withContext "test Complete"
    }
}