package com.proseed.api.job

import com.proseed.api.config.exception.JobNotFoundException
import com.proseed.api.job.dto.JobCreateRequestDto
import com.proseed.api.job.model.Job
import com.proseed.api.job.repository.JobRepository
import com.proseed.api.user.model.User
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@Transactional
class JobService(
    val jobRepository: JobRepository
) {
   fun select(job_id: Long): Any {
       return jobRepository.findById(job_id).orElseThrow {
           throw JobNotFoundException()
       }
   }

    fun selectAll(pageable: Pageable): Any {
        return jobRepository.findSearchAll(pageable)
    }

    fun preferenceJob(user: User): Any {
        if (user.preference != null && user.preference != "") {
            val jobList = user.preference!!.split(",")

            return jobRepository.findJobOnList(jobList)
        } else {
            return mutableListOf("")
        }
    }

    fun create(user: User, requestDto: JobCreateRequestDto): Any {
        val job = jobRepository.findByName(requestDto.name)
            ?: jobRepository.save(Job(name = requestDto.name))

        return job
    }
}