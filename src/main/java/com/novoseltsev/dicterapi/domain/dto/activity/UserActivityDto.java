package com.novoseltsev.dicterapi.domain.dto.activity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dicterapi.domain.entity.Activity;
import com.novoseltsev.dicterapi.domain.entity.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserActivityDto extends AbstractActivityDto {

    @Positive
    @NotNull
    private Long userId;

    @Override
    public Activity toEntity() {
        Activity activity = super.toEntity();
        activity.setUser(new User(userId));
        return activity;
    }
}
