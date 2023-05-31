package com.proseed.api.config.exception.user

import com.proseed.api.config.exception.ApiException

class UserForbiddenException : ApiException("접근 권한이 없습니다.") {
}