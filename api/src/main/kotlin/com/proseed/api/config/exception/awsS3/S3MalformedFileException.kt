package com.proseed.api.config.exception.awsS3

import com.proseed.api.config.exception.ApiException

class S3MalformedFileException : ApiException("잘못된 형식의 파일 입니다.") {
}