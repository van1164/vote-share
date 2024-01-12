package com.van1164.voteshare.domain

import lombok.Getter
import lombok.RequiredArgsConstructor


@RequiredArgsConstructor
@Getter
enum class OAuth2Provider(provider: String) {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    NAVER("NAVER"),
}