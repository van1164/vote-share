package com.van1164.voteshare.domain

import lombok.Getter
import lombok.RequiredArgsConstructor


@Getter
@RequiredArgsConstructor
enum class Role(key: String, title: String) {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

}