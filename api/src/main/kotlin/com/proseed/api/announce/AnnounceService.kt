package com.proseed.api.announce

import com.proseed.api.announce.dto.AnnounceCreateRequestDto
import com.proseed.api.announce.dto.AnnounceCreateResponseDto
import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.company.CompanyRepository
import com.proseed.api.config.exception.company.CompanyNotFoundException
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.location.LocationRepository
import com.proseed.api.location.model.Location
import com.proseed.api.user.model.Role
import com.proseed.api.user.model.User
import org.springframework.stereotype.Service

@Service
class AnnounceService(
    val announceRepository: AnnounceRepository,
    val companyRepository: CompanyRepository,
    val locationRepository: LocationRepository
) {

    fun create(user: User, requestDto: AnnounceCreateRequestDto): Any {
        // 사장이 아니면 공지를 올릴 수 없으므로
        if (user.role != Role.ENTERPRISE) {
            throw UserNotFoundException()
        }

        // 회사사람인지 확인
        val company = companyRepository?.findByUserAndName(user, requestDto.company_name)
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
}