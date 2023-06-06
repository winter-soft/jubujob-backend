package com.proseed.api.config.exception.companyMember

import com.proseed.api.config.exception.ApiException

class CompanyMemberNotFoundException : ApiException("입력하신 회사와 매칭되는 회원정보가 없습니다.") {
}