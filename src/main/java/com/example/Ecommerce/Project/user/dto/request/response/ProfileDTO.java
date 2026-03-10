package com.example.Ecommerce.Project.user.dto.request.response;

import com.example.Ecommerce.Project.role.model.AppRole;
import com.example.Ecommerce.Project.role.model.Role;
import com.example.Ecommerce.Project.user.model.User;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {

    private Long userId;
    private String userName;
    private String email;
    private Set<AppRole> roles = new HashSet<>();


    public static ProfileDTO toDTO(User user)  {
        Set<AppRole> convertedRoles = new HashSet<>();
        if(user.getRoles() != null) {
            for (Role role : user.getRoles())
                convertedRoles.add(role.getRoleName());
        }

        return ProfileDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .roles(convertedRoles)
                .build();
    }
}
