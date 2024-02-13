package com.van1164.voteshare.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.van1164.voteshare.util.ServiceUtil
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
        val imageUrls = images.map{ it.let {ServiceUtil.createUUID()}}
        println(images)
        println(images.size)
        withContext(Dispatchers.IO) {
            val uploadJobs = images.mapIndexed { index, it ->
                val objectMetadata = ObjectMetadata().apply {
                    this.contentType = it.contentType
                    this.contentLength = it.size
                }
                async {
                    val putObjectRequest = PutObjectRequest(
                            "vote-share",
                            imageUrls[index],
                            it.inputStream,
                            objectMetadata,
                    ).withCannedAcl(CannedAccessControlList.PublicRead)
                    amazonS3Client.putObject(putObjectRequest)
                }
            }
            uploadJobs.awaitAll()
        }
        return imageUrls.toList()
    }

    fun uploadImage(image: MultipartFile?): String? {
        if (image == null){
            return null
        }
        val key = ServiceUtil.createUUID()
        val objectMetadata = ObjectMetadata().apply {
            this.contentType = image.contentType
            this.contentLength = image.size
        }
        val putObjectRequest = PutObjectRequest(
            "vote-share",
            key,
            image.inputStream,
            objectMetadata,
        ).withCannedAcl(CannedAccessControlList.PublicRead)
        amazonS3Client.putObject(putObjectRequest)
        return key
    }

}