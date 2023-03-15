package com.proseed.api.config.exception.awsS3

import com.proseed.api.config.exception.ApiException

class S3FileSizeLimitExceededException : ApiException("파일의 크기가 10MB를 초과합니다.") {
}