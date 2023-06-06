package com.proseed.api.recruitment

import com.proseed.api.announce.repository.AnnounceRepository
import com.proseed.api.common.aop.RoleCheck
import com.proseed.api.company.repository.CompanyMemberRepository
import com.proseed.api.config.exception.announce.AnnounceNotFoundException
import com.proseed.api.config.exception.companyMember.CompanyMemberNotFoundException
import com.proseed.api.config.exception.recruitment.RecruitmentAlreadyExistException
import com.proseed.api.config.exception.recruitment.RecruitmentNotFoundException
import com.proseed.api.config.exception.user.UserForbiddenException
import com.proseed.api.recruitment.dto.RecruitmentCreateRequestDto
import com.proseed.api.recruitment.dto.RecruitmentDefaultDto
import com.proseed.api.recruitment.dto.RecruitmentSelectRequestDto
import com.proseed.api.recruitment.dto.RecruitmentStatusUpdateRequestDto
import com.proseed.api.recruitment.model.Recruitment
import com.proseed.api.recruitment.model.RecruitmentResume
import com.proseed.api.recruitment.model.RecruitmentStatus
import com.proseed.api.recruitment.repository.RecruitmentRepository
import com.proseed.api.recruitment.repository.RecruitmentResumeRepository
import com.proseed.api.resume.repository.ResumeRepository
import com.proseed.api.user.model.Role
import com.proseed.api.user.model.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class RecruitmentService(
    val announceRepository: AnnounceRepository,
    val resumeRepository: ResumeRepository,
    val companyMemberRepository: CompanyMemberRepository,
    val recruitmentRepository: RecruitmentRepository,
    val recruitmentResumeRepository: RecruitmentResumeRepository,
) {
    @RoleCheck(role = Role.ENTERPRISE)
    fun select(announce_id: Long, user: User, requestDto: RecruitmentSelectRequestDto): Any {
        // 공고 관리자 검증
        val announce = announceRepository.findByIdAndUser(announce_id, user.id!!)
            ?: UserForbiddenException()

        // recruitment 찾기
        val recruitment = recruitmentRepository.findById(requestDto.recruitment_id).orElseThrow {
            throw RecruitmentNotFoundException()
        }

        return RecruitmentDefaultDto(recruitment)
    }

    @RoleCheck(role = Role.ENTERPRISE)
    fun selectAllGroupBy(announce_id: Long, user: User): Any {
        // 공고 관리자 검증
        val announce = announceRepository.findByIdAndUser(announce_id, user.id!!)
            ?: UserForbiddenException()

        return recruitmentRepository.findAllByJobGroup(announce_id)
    }

    fun create(announce_id: Long, user: User, requestDto: RecruitmentCreateRequestDto): Any {
        // announce가 존재하는지 검증
        val announce = announceRepository.findById(announce_id).orElseThrow {
            throw AnnounceNotFoundException()
        }

        // recruitment가 이미 존재하는경우 예외처리
        val recruitment = recruitmentRepository.findByUserAndAnnounce(user.id!!, announce_id)
        if (recruitment != null) {
            throw RecruitmentAlreadyExistException()
        }

        // 검증 후 RESUME 가져오기
        val resumes = resumeRepository.findResumeList(user, requestDto.resumes.map { it.resume_id })

        // Recruitment 생성
        val savedRecruitment = recruitmentRepository.save(
            Recruitment(
                announce = announce,
                user = user
            )
        )

        // RecruitmentResume들 저장
        val savedRecruitmentResumes = recruitmentResumeRepository.saveAll(resumes.map {
            RecruitmentResume(
                user = user,
                resume = it,
                recruitment = savedRecruitment
            )
        })

        return RecruitmentDefaultDto(savedRecruitment)
    }

    @RoleCheck(role = Role.ENTERPRISE)
    fun update(announce_id: Long, user: User, requestDto: RecruitmentStatusUpdateRequestDto): Any {
        // 공고 관리자 검증
        val announce = announceRepository.findByIdAndUser(announce_id, user.id!!)
            ?: UserForbiddenException()

        // 상태를 바꾸려는 사용자의 recruitment를 가져옴
        val recruitment = recruitmentRepository.findByUserAndAnnounce(requestDto.user_id, announce_id)
            ?: throw RecruitmentNotFoundException()

        recruitment.status = RecruitmentStatus.customValueOf(requestDto.recruitment_status)

        return RecruitmentDefaultDto(recruitment)
    }
}