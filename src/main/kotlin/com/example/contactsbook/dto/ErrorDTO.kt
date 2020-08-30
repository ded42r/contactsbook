package com.example.contactsbook.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorDTO(
        val code: ErrorCode,
        val message: String,
        val details: Map<String, Any>?
) {

    enum class ErrorCode {
        @JsonProperty("validation")
        VALIDATION,

        @JsonProperty("not_found")
        NOT_FOUND,

        @JsonProperty("bad_request")
        BAD_REQUEST,

        @JsonProperty("server")
        SERVER
    }

}