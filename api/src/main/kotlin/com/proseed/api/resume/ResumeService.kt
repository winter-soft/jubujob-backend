package com.proseed.api.resume

import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.repository.AnnounceRepository
import com.proseed.api.company.repository.CompanyRepository
import com.proseed.api.company.model.Company
import com.proseed.api.config.exception.JobNotFoundException
import com.proseed.api.config.exception.announce.AnnounceNotFoundException
import com.proseed.api.config.exception.company.CompanyNotFoundException
import com.proseed.api.config.exception.resume.ResumeNotFoundException
import com.proseed.api.job.model.Job
import com.proseed.api.job.repository.JobRepository
import com.proseed.api.resume.dto.ResumeCreateRequestDto
import com.proseed.api.resume.dto.ResumeDefaultResponseDto
import com.proseed.api.resume.dto.ResumeUpdateRequestDto
import com.proseed.api.resume.model.Resume
import com.proseed.api.resume.repository.ResumeRepository
import com.proseed.api.user.model.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ResumeService(
    val jobRepository: JobRepository,
    val companyRepository: CompanyRepository,
    val announceRepository: AnnounceRepository,
    val resumeRepository: ResumeRepository
) {
    fun select(user: User, resume_id: Long): Any {
        val resume = resumeRepository.findJoinAll(resume_id, user)
            ?: throw ResumeNotFoundException()

        return ResumeDefaultResponseDto(resume)
    }

    fun selectAllByGroup(user: User): Any {
        return resumeRepository.findAllByJobGroup(user)
    }

    fun create(user: User, requestDto: ResumeCreateRequestDto): Any {
        val job = jobRepository.findById(requestDto.job_id).orElseThrow {
            throw JobNotFoundException()
        }

        val company = companyRepository.findById(requestDto.company_id).orElseThrow {
            throw CompanyNotFoundException()
        }

        var announce: Announce? = null

        if (requestDto.announce_id != null) {
            announce = announceRepository.findById(requestDto.announce_id).orElseThrow {
                throw AnnounceNotFoundException()
            }
        }

        val resume = Resume(
            user = user,
            job = job,
            company = company,
            announce = announce,
            title = requestDto.resume_title,
            detail = requestDto.resume_detail,
            address = requestDto.resume_address,
            certificationCheck = false,
            updateCheck = false,
            startDay = requestDto.resume_startDay,
            endDay = requestDto.resume_endDay
        )

        if (resume.announce != null) {
            resume.certificationCheck = true
        }

        val savedResume = resumeRepository.save(resume)

        return ResumeDefaultResponseDto(savedResume)
    }

    fun update(user: User, requestDto: ResumeUpdateRequestDto): Any {
        var job: Job? = null
        var company: Company? = null

        val resume = resumeRepository.findJoinAll(requestDto.resume_id, user)
            ?: throw ResumeNotFoundException()

        if (requestDto.job_id != null) {
            job = jobRepository.findById(requestDto.job_id).orElseThrow {
                throw JobNotFoundException()
            }
        }
        if (requestDto.company_id != null) {
            company = companyRepository.findById(requestDto.company_id).orElseThrow {
                throw CompanyNotFoundException()
            }
        }

        resume.job = job ?: resume.job
        resume.company = company ?: resume.company
        resume.title = requestDto.resume_title ?: resume.title
        resume.detail = requestDto.resume_detail ?: resume.detail
        resume.address = requestDto.resume_address ?: resume.address
        resume.startDay = requestDto.resume_startDay
        resume.endDay = requestDto.resume_endDay

        // 업데이트 했으므로 인증 해제
        resume.certificationCheck = false
        resume.updateCheck = true

        return ResumeDefaultResponseDto(resume)
    }
}