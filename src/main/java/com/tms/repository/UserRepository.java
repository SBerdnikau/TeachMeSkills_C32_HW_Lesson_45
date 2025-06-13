package com.tms.repository;

import com.tms.config.DatabaseService;
import com.tms.config.SQLQuery;
import com.tms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final DatabaseService databaseService;

    @Autowired
    public UserRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Optional<User> getUserById(Long id) {
        Connection connection = databaseService.getConnection();

        try {
            PreparedStatement getUserStatement = connection.prepareStatement(SQLQuery.GET_USER_BY_ID);
            getUserStatement.setLong(1, id);
            ResultSet resultSet = getUserStatement.executeQuery();
            return parseUser(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
    
    public Boolean deleteUser(Long id){
        Connection connection = databaseService.getConnection();
        try {
            PreparedStatement getUserStatement = connection.prepareStatement(SQLQuery.DELETE_USER);
            getUserStatement.setLong(1, id);
            return getUserStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public Optional<Long> createUser(User user){
        Connection connection = databaseService.getConnection();
        Long userId = null;
        
        try {
            PreparedStatement createUserStatement = connection.prepareStatement(SQLQuery.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            createUserStatement.setString(1, user.getFirstname());
            createUserStatement.setString(2, user.getSecondName());
            createUserStatement.setInt(3, user.getAge());
            createUserStatement.setString(4, user.getTelephoneNumber());
            createUserStatement.setString(5, user.getEmail());
            createUserStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            createUserStatement.setString(7, user.getSex());
            createUserStatement.setBoolean(8, false);
            createUserStatement.executeUpdate();
            
            ResultSet generatedKeys = createUserStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getLong(1);
            }
            return Optional.of(userId);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean updateUser(User userRequestDto) {
        Connection connection = databaseService.getConnection();

        try {
            PreparedStatement getUserStatement = connection.prepareStatement(SQLQuery.UPDATE_USER);
            getUserStatement.setString(1, userRequestDto.getFirstname());
            getUserStatement.setString(2, userRequestDto.getSecondName());
            getUserStatement.setInt(3, userRequestDto.getAge());
            getUserStatement.setString(4, userRequestDto.getTelephoneNumber());
            getUserStatement.setString(5, userRequestDto.getEmail());
            getUserStatement.setString(6, userRequestDto.getSex());
            getUserStatement.setLong(7, userRequestDto.getId());
            return getUserStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<User> getAllUsers() {
        Connection connection = databaseService.getConnection();

        List<User> users = new ArrayList<>();
        try {
            PreparedStatement getAllUsersStatement = connection.prepareStatement(SQLQuery.GET_ALL_USERS);
            ResultSet resultSet = getAllUsersStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setSecondName(resultSet.getString("second_name"));
                user.setAge(resultSet.getInt("age"));
                user.setTelephoneNumber(resultSet.getString("telephone_number"));
                user.setEmail(resultSet.getString("email"));
                user.setCreated(resultSet.getTimestamp("created"));
                user.setUpdated(resultSet.getTimestamp("updated"));
                user.setSex(resultSet.getString("sex"));
                user.setIsDeleted(resultSet.getBoolean("is_deleted"));
                users.add(user);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    private Optional<User> parseUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirstname(resultSet.getString("firstname"));
            user.setSecondName(resultSet.getString("second_name"));
            user.setAge(resultSet.getInt("age"));
            user.setTelephoneNumber(resultSet.getString("telephone_number"));
            user.setEmail(resultSet.getString("email"));
            user.setCreated(resultSet.getTimestamp("created"));
            user.setUpdated(resultSet.getTimestamp("updated"));
            user.setSex(resultSet.getString("sex"));
            user.setIsDeleted(resultSet.getBoolean("is_deleted"));
            return Optional.of(user);
        }
        return Optional.empty();
    }
}