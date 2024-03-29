package com.novoseltsev.dicterapi.domain.dto.termGroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dicterapi.domain.entity.TermGroup;
import com.novoseltsev.dicterapi.domain.entity.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTermGroupDto extends AbstractTermGroupDto {

    @NotNull
    @Positive
    private Long userId;

    @Override
    public TermGroup toEntity() {
        TermGroup termGroup = super.toEntity();
        termGroup.setUser(new User(userId));
        return termGroup;
    }
}
