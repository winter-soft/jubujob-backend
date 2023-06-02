package com.proseed.api.announce

import com.proseed.api.announce.dto.AnnounceCreateRequestDto
import com.proseed.api.announce.dto.AnnounceCreateResponseDto
import com.proseed.api.announce.dto.AnnounceUpdateRequestDto
import com.proseed.api.announce.dto.AnnounceUpdateResponseDto
import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.announce.repository.AnnounceRepository
import com.proseed.api.announce.repository.AnnounceRepositoryCustomImpl
import com.proseed.api.common.aop.RoleCheck
import com.proseed.api.company.CompanyRepository
import com.proseed.api.config.exception.announce.AnnounceNotFoundException
import com.proseed.api.config.exception.company.CompanyNoMatchingWithUserException
import com.proseed.api.config.exception.company.CompanyNotFoundException
import com.proseed.api.config.exception.user.UserForbiddenException
import com.proseed.api.location.LocationRepository
import com.proseed.api.location.model.Location
import com.proseed.api.user.model.Role
import com.proseed.api.user.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AnnounceService(
    val announceRepository: AnnounceRepository,
    val companyRepository: CompanyRepository,
    val locationRepository: LocationRepository
) {

    @RoleCheck(role = Role.ENTERPRISE)
    @Transactional
    fun create(user: User, requestDto: AnnounceCreateRequestDto): Any {

        // 회사사람인지 확인
        val company = companyRepository?.findByUserAndName(user.id!!, requestDto.company_name)
            ?: throw CompanyNotFoundException()

        // 일치하는 주소가 있는지 확인 없다면 생성
        val location = locationRepository?.findByLatitudeAndLongitudeAndAddress(
            requestDto.location_latitude,
            requestDto.location_longitude,
            requestDto.location_address
        ) ?: locationRepository.save(Location(
            address = requestDto.location_address,
            latitude = requestDto.location_latitude,
            longitude = requestDto.location_longitude
        ))

        // Announce 생성
        val announce = announceRepository.save(
            Announce(
                user = user,
                location = location,
                company = company,
                type = AnnounceType.valueOf(requestDto.announce_type),
                title = requestDto.announce_title,
                detail = requestDto.announce_detail,
                imageUrl = requestDto.announce_imageUrl
            )
        )

        return AnnounceCreateResponseDto(announce)
    }

    @RoleCheck(role = Role.ENTERPRISE)
    @Transactional
    fun update(user: User, requestDto: AnnounceUpdateRequestDto, announce_id: Long): Any {
        // announce_id를 통해 ANNOUNCE 조회
        val announce = announceRepository?.findByIdJoinAll(announce_id)
            ?: throw AnnounceNotFoundException()

        // 요청한 유저가 이 회사인지 확인
        if (announce.user!!.id != user.id) {
            throw UserForbiddenException()
        }

        // announce 정보 수정
        if (requestDto.location_address != null &&
                requestDto.location_latitude != null &&
                requestDto.location_longitude != null) {
            val location = locationRepository.save(Location(
                address = requestDto.location_address,
                latitude = requestDto.location_latitude,
                longitude = requestDto.location_longitude
            ))
            announce.location = location
        }

        announce.title = requestDto?.announce_title ?: announce.title
        announce.detail = requestDto?.announce_detail ?: announce.detail
        announce.imageUrl = requestDto?.announce_imageUrl ?: announce.imageUrl

        // announce 정보 저장
        announceRepository.saveAndFlush(announce)

        // 결과 반환
        return AnnounceUpdateResponseDto(announce)
    }
}