package com.proseed.api.config.exception.awsS3

import com.proseed.api.config.exception.ApiException

class S3FileUploadFailedException : ApiException("파일 업로드에 실패하였습니다.") {
}