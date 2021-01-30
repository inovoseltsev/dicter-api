package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.dto.request.ChangePasswordDto;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.domain.role.UserRole;
import com.novoseltsev.dictionaryapi.domain.status.UserStatus;
import com.novoseltsev.dictionaryapi.exception.InvalidOldPasswordException;
import com.novoseltsev.dictionaryapi.exception.LoginIsAlreadyUsedException;
import com.novoseltsev.dictionaryapi.exception.UserAccountAccessForbiddenException;
import com.novoseltsev.dictionaryapi.repository.UserRepository;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import static com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils.OBJECT_NOT_FOUND;
import static com.novoseltsev.dictionaryapi.exception.util.MessageCause.INVALID_OLD_PASSWORD;
import static com.novoseltsev.dictionaryapi.exception.util.MessageCause.LOGIN_IS_ALREADY_USED;
import static com.novoseltsev.dictionaryapi.exception.util.MessageCause.USER_HAVE_NO_ACCOUNT_ACCESS;

@Component
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @Override
    public User create(User user) {
        checkLoginUniqueness(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    private void checkLoginUniqueness(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new LoginIsAlreadyUsedException(LOGIN_IS_ALREADY_USED);
        }
    }

    @Override
    public User update(User user) {
        User savedUser = findById(user.getId());
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        return userRepository.save(savedUser);
    }

    @Override
    public void updatePassword(Long userId, ChangePasswordDto passwordDto) {
        User user = findById(userId);
        checkIfValidOldPassword(user, passwordDto.getOldPassword());
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    private void checkIfValidOldPassword(User user, String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getFirstName())) {
            throw new InvalidOldPasswordException(INVALID_OLD_PASSWORD);
        }
    }

    @Override
    public void updateUserStatus(Long userId, UserStatus status) {
        User user = findById(userId);
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().filter(user -> !user.getRole().equals(UserRole.ADMIN)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(OBJECT_NOT_FOUND);
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new UserAccountAccessForbiddenException(USER_HAVE_NO_ACCOUNT_ACCESS);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(OBJECT_NOT_FOUND);
    }
}
