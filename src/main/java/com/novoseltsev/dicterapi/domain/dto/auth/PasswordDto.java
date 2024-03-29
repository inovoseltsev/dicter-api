package com.novoseltsev.dicterapi.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;


import static com.novoseltsev.dicterapi.validation.Pattern.PASSWORD_PATTERN;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.PASSWORD_ERROR;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordDto {

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String oldPassword;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String newPassword;
}
