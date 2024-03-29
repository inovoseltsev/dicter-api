package com.novoseltsev.dicterapi.service;

import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.domain.status.UserStatus;
import java.util.List;

public interface UserService {

    User create(User user);

    User update(User user);

    void updatePassword(Long userId, String oldPassword, String newPassword);

    void updateUserStatus(Long userId, UserStatus status);

    void deleteById(Long id);

    List<User> findAll();

    User findById(Long id);
}
