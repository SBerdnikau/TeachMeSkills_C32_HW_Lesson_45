package com.tms.repository;

import com.tms.config.DatabaseService;
import com.tms.config.SQLQuery;
import com.tms.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Mock
    private DatabaseService databaseService;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @InjectMocks
    private UserRepository userRepository;

    User user;
    Timestamp timestampCurrent = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(databaseService.getConnection()).thenReturn(connection);
        user = new User();
        user.setId(1L);
        user.setFirstname("Dima");
        user.setSecondName("Bilan");
        user.setAge(29);
        user.setTelephoneNumber("375297867861");
        user.setEmail("dima@mail.com");
        user.setSex("M");
        user.setCreated(timestampCurrent);
        user.setUpdated(timestampCurrent);
        user.setIsDeleted(false);
    }

    @Test
    public void testGetUserById_Success() throws SQLException {
        Mockito.when(connection.prepareStatement(SQLQuery.GET_USER_BY_ID)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);

        Mockito.when(resultSet.getLong("id")).thenReturn(1L);
        Mockito.when(resultSet.getString("firstname")).thenReturn("Dima");
        Mockito.when(resultSet.getString("second_name")).thenReturn("Bilan");
        Mockito.when(resultSet.getInt("age")).thenReturn(29);
        Mockito.when(resultSet.getString("telephone_number")).thenReturn("375297867861");
        Mockito.when(resultSet.getString("email")).thenReturn("dima@mail.com");
        Mockito.when(resultSet.getTimestamp("created")).thenReturn(timestampCurrent);
        Mockito.when(resultSet.getTimestamp("updated")).thenReturn(timestampCurrent);
        Mockito.when(resultSet.getString("sex")).thenReturn("M");
        Mockito.when(resultSet.getBoolean("is_deleted")).thenReturn(false);

        Optional<User> userResult = userRepository.getUserById(1L);

        Assertions.assertTrue(userResult.isPresent());
        Assertions.assertEquals(user.getId(), userResult.get().getId());
        Assertions.assertEquals(user.getFirstname(), userResult.get().getFirstname());
        Assertions.assertEquals(user.getSecondName(), userResult.get().getSecondName());
        Assertions.assertEquals(user.getAge(), userResult.get().getAge());
        Assertions.assertEquals(user.getTelephoneNumber(), userResult.get().getTelephoneNumber());
        Assertions.assertEquals(user.getEmail(), userResult.get().getEmail());
        Assertions.assertEquals(user.getCreated(), userResult.get().getCreated());
        Assertions.assertEquals(user.getUpdated(), userResult.get().getUpdated());
        Assertions.assertEquals(user.getSex(), userResult.get().getSex());
        Assertions.assertEquals(user.getIsDeleted(), userResult.get().getIsDeleted());
    }

    @Test
    public void testGetUserById_NotFound() throws SQLException {
        Mockito.when(connection.prepareStatement(SQLQuery.GET_USER_BY_ID)).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        Optional<User> userResult = userRepository.getUserById(1L);

        Assertions.assertTrue(userResult.isEmpty());
    }

}