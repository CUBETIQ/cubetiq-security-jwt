package com.cubetiqs.security.jwt.extension

import com.cubetiqs.security.jwt.util.JsonUtils

fun Any?.toJsonNode() = JsonUtils.toJsonNode(this)
fun Any?.toJson() = JsonUtils.parseObjectToSting(this)