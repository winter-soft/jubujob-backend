package com.proseed.api.config.exception.auth.kakao

import com.proseed.api.config.exception.ApiException

class KakaoAuthorizationCodeNotFoundException
    : ApiException("AuthorizationCode를 찾을 수 없습니다.") {
}