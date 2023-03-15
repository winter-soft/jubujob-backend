package com.proseed.api.config.exception

open class ApiException : RuntimeException {

    constructor(message: String) : super(message)
}