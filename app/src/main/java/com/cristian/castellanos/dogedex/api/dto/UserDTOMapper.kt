package com.cristian.castellanos.dogedex.api.dto

import com.cristian.castellanos.dogedex.model.User

class UserDTOMapper {
    fun fromUserDTOToUserDomain(userDTO: UserDTO) = User(
        userDTO.id,
        userDTO.email,
        userDTO.authenticationToken
    )
}