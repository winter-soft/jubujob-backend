package com.proseed.api.config.exception.user

import com.proseed.api.config.exception.ApiException

class UserUnAuthorizationMessageCheckException : ApiException {
    constructor() : super("메시지가 인증되지 않은 사용자입니다.")
}