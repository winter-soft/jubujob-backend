package com.proseed.api.config.exception.company

import com.proseed.api.config.exception.ApiException

class CompanyNoMatchingWithUserException : ApiException("유저와 일치하는 회사정보가 존재하지 않습니다.") {
}