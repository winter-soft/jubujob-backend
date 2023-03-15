package com.proseed.api.config.exception.user

import com.proseed.api.config.exception.ApiException

class UserNotFoundException : ApiException {
    constructor() : super("유저 정보를 찾지 못했습니다.")
}