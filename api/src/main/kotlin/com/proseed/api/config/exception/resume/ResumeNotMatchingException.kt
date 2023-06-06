package com.proseed.api.config.exception.resume

import com.proseed.api.config.exception.ApiException

class ResumeNotMatchingException : ApiException("이력서 중 매칭되지 않는 이력서가 존재합니다.") {
}