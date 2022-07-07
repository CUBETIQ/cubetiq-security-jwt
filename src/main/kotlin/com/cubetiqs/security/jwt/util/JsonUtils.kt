package com.cubetiqs.security.jwt.util

import com.cubetiqs.security.jwt.extension.toJson
import com.cubetiqs.security.jwt.util.StringUtils.isNullObject
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JsonUtils {
    private var objectMapper: ObjectMapper? = null
    fun getObjectMapper(renew: Boolean = false): ObjectMapper {
        if (objectMapper == null || renew) {
            objectMapper = jacksonObjectMapper()
        }
        return objectMapper!!
    }

    fun parseObjectToString(ob: Any?): String? {
        if (ob.isNullObject()) return null
        return when (ob) {
            is String -> ob
            else -> try {
                getObjectMapper()
                    .writeValueAsString(ob)
            } catch (ex: Exception) {
                null
            }
        }
    }

    fun toJsonNode(any: Any?): JsonNode? {
        if (any.isNullObject()) return null
        return when (any) {
            is JsonNode -> any
            else -> getObjectMapper().readTree(any.toJson())
        }
    }
}