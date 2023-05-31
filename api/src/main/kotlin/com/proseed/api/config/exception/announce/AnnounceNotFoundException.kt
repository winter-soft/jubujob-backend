package com.proseed.api.config.exception.announce

import com.proseed.api.config.exception.company.ApiException

class AnnounceNotFoundException : ApiException("공고를 찾지 못했습니다.") {
}