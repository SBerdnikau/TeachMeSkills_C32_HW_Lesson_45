package com.tms.controller;

import com.tms.model.User;
import com.tms.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    private User user;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        System.out.println("Before Each");
        user = new User();
        user.setId(3L);
        user.setFirstname("Dimas");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After Each");
    }

    @Test
    public void testGetUserById_Success() {
        //1. Настройка перед запуском
        Mockito.when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        //2. Запуск метода
        ResponseEntity<User> response = userController.getUserById(3L);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserById_NotFound() {
        //1. Настройка перед запуском
        Mockito.when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        //2. Запуск метода
        ResponseEntity<User> response = userController.getUserById(3L);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testCreateUser_Success() {
        //1. Настройка перед запуском
        Mockito.when(userService.createUser(any(User.class))).thenReturn(Optional.of(user));
        Timestamp updated = user.getUpdated();

        //2. Запуск метода
        ResponseEntity<User> response = userController.createUser(user);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(updated, response.getBody().getUpdated()); //реализовать клонирование для других полей если нужно
    }

    @Test
    public void testCreateUser_Conflict() {
        //1. Настройка перед запуском
        Mockito.when(userService.createUser(any(User.class))).thenReturn(Optional.empty());

        //2. Запуск метода
        ResponseEntity<User> response = userController.createUser(user);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testDeleteUser_Success() {
        //1. Настройка перед запуском
        Mockito.when(userService.deleteUser(anyLong())).thenReturn(Optional.of(user));

        //2. Запуск метода
        ResponseEntity<HttpStatus> response = userController.deleteUser(1L);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testDeleteUser_Conflict() {
        //1. Настройка перед запуском
        Mockito.when(userService.deleteUser(anyLong())).thenReturn(Optional.empty());

        //2. Запуск метода
        ResponseEntity<HttpStatus> response = userController.deleteUser(1L);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testUpdateUser_Success() {
        //1. Настройка перед запуском
        Mockito.when(userService.updateUser(any(User.class))).thenReturn(Optional.of(user));

        //2. Запуск метода
        ResponseEntity<User> response = userController.updateUser(user);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}