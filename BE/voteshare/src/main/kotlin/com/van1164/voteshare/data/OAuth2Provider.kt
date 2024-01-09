package com.van1164.voteshare.data

import lombok.Getter
import lombok.RequiredArgsConstructor


@RequiredArgsConstructor
@Getter
enum class OAuth2Provider(provider: String) {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    NAVER("NAVER"),
}