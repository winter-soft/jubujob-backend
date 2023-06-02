package com.proseed.api.config.exception.location

import com.proseed.api.config.exception.ApiException

class LocationNotFoundException : ApiException("Location 정보를 찾지 못했습니다.") {
}