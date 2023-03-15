package com.proseed.api.config.awsS3

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Thread.sleep
import java.util.*

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class AwsS3ServiceTest(
    @Autowired val awsS3Service: AwsS3Service
) {
    companion object {
        var uploadImage: String? = null
    }

    @Test
    @Order(1)
    @DisplayName("AWS S3 파일 업로드 테스트")
    fun awsS3FileUpload() {
        val path = "src/test/kotlin/com/proseed/api/config/awsS3/ex_img.png"
        val fileInputStream = FileInputStream(path)

        // 가짜 MultipartFile 생성
        val mockMultipartFile = MockMultipartFile("exampleImage",
            "ex_img.png",
            "png",
            fileInputStream)

        uploadImage = awsS3Service.uploadImage(multipartFile = mockMultipartFile)
        println(uploadImage)
        assertEquals(uploadImage!!.substring(0,11), "https://s3.")
    }

    @Test
    @Order(2)
    @DisplayName("AWS S3 파일 삭제 테스트")
    fun awsS3FileDelete() {
        val list = uploadImage!!.split("/")
        val fileName = list.get(list.size - 1)

        awsS3Service.deleteImage(fileName)
    }
}