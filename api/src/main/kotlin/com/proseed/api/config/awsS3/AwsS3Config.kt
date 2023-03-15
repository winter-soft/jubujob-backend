package com.proseed.api.config.awsS3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsS3Config(
    @Value("\${cloud.aws.credentials.accessKey}") val accessKey: String,
    @Value("\${cloud.aws.credentials.secretKey}") val secretKey: String,
    @Value("\${cloud.aws.region.static}") val region: String
) {

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val awsCredentials = BasicAWSCredentials(accessKey, secretKey)

        return (AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build()) as AmazonS3Client
    }
}