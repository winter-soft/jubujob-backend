package com.proseed.api.common.aop

import com.proseed.api.user.model.Role
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
annotation class RoleCheck(
    val role: Role = Role.USER
) {
}
