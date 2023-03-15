package com.proseed.api.config.awsS3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.proseed.api.config.exception.awsS3.S3FileSizeLimitExceededException
import com.proseed.api.config.exception.awsS3.S3FileUploadFailedException
import com.proseed.api.config.exception.awsS3.S3MalformedFileException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@Service
class AwsS3Service(
    @Value("\${cloud.aws.s3.bucket}") val bucket: String,
    @Value("\${cloud.aws.region.static}") val region: String,
    private val amazonS3: AmazonS3
) {
    fun uploadImage(multipartFile: MultipartFile): String? {
        val url = "https://s3.$region.amazonaws.com/$bucket/"

        // 파일의 크기가 10MB가 넘어가면 Exception 발생
        if (multipartFile.size > 10_000_000)
            throw S3FileSizeLimitExceededException()

        val fileName = createFileName(multipartFile.originalFilename)
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = multipartFile.size
        objectMetadata.contentType = multipartFile.contentType
        try {
            multipartFile.inputStream.use { inputStream ->
                amazonS3.putObject(
                    PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
            }
        } catch (e: IOException) {
            throw S3FileUploadFailedException()
        }
        return url + fileName
    }

    fun deleteImage(fileName: String?) {
        amazonS3.deleteObject(DeleteObjectRequest(bucket, fileName))
    }

    private fun createFileName(fileName: String?): String {
        return UUID.randomUUID().toString() + getFileExtension(fileName)
    }

    private fun getFileExtension(fileName: String?): String {
        return try {
            fileName!!.substring(fileName.lastIndexOf("."))
        } catch (e: StringIndexOutOfBoundsException) {
            throw S3MalformedFileException()
        }
    }
}