package com.proseed.api.config.exception.company

import com.proseed.api.config.exception.ApiException

class CompanyNotFoundException : ApiException("회사정보를 찾지 못했습니다.") {
}