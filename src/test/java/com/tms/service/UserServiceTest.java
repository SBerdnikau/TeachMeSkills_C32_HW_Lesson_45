package com.tms.service;

import com.tms.model.User;
import com.tms.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User user;


    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(3L);
        user.setFirstname("Dimas");
    }

    @Test
    public void testUpdateUser_Success(){
        Mockito.when(userRepository.updateUser(any(User.class))).thenReturn(true);
        Mockito.when(userRepository.getUserById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> result = userService.updateUser(user);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void testUpdateUser_Failed(){
        Mockito.when(userRepository.updateUser(any(User.class))).thenReturn(false);

        Optional<User> result = userService.updateUser(user);

        Assertions.assertTrue(result.isEmpty());
    }
}