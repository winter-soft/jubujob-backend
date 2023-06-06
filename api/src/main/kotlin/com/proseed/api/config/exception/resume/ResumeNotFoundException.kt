package com.proseed.api.config.exception.resume

import com.proseed.api.config.exception.ApiException

class ResumeNotFoundException : ApiException("이력서를 찾지 못했습니다.") {
}