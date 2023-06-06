package com.proseed.api.config.exception.recruitment

import com.proseed.api.config.exception.ApiException

class RecruitmentNotFoundException : ApiException("RECRUITMENT 정보가 존재하지 않습니다.") {
}