package com.vfutia.lurk.model

class TokenResponse (
    val accessToken: String,
    val tokenType: String,
    val deviceId: String,
    val expiresIn: Long,
    val scope: String
)