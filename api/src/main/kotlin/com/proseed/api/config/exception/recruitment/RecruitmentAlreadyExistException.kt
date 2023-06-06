package com.proseed.api.config.exception.recruitment

import com.proseed.api.config.exception.ApiException

class RecruitmentAlreadyExistException : ApiException("RECRUITMENT 정보가 이미 존재합니다.") {
}