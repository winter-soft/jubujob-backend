package com.proseed.api.config.exception.auth.kakao

import com.proseed.api.config.exception.ApiException

class KakaoTokenExpiredException
    : ApiException("카카오 토큰이 만료되었습니다.") {
}