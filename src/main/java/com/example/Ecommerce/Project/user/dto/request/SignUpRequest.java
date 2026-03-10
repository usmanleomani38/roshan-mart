package com.example.Ecommerce.Project.user.dto.request;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    private String email;
    private String password;
    private Set<String> role;
    private String userName;

}


