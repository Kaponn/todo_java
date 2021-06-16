package efs.task.todoapp.service;

import com.google.gson.Gson;
import efs.task.todoapp.constants.HttpStatus;
import efs.task.todoapp.repository.UserEntity;
import efs.task.todoapp.repository.UserRepository;
import efs.task.todoapp.util.HttpException;

import java.util.Map;
import java.util.logging.Logger;

public class UserService {
  private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String addUser(Map<String, String> userData) {
      UserEntity user = new UserEntity();

      String username = userData.get("username");
      String password = userData.get("password");

      if (username == null || username.isBlank() || password == null || password.isBlank()) {
        throw new HttpException(HttpStatus.BAD_REQUEST, "Username or password is missing");
      }

      user.setUsername(username);
      user.setPassword(password);

      String savedUser = userRepository.save(user);

      if (savedUser == null) {
        throw new HttpException(HttpStatus.CONFLICT, "User already exists");
      }

      LOGGER.info("Added new user of username: " + username);
      return savedUser;
    }

  public UserEntity getUser(String username) {
    return userRepository.query(username);
  }

  public UserEntity updateUser(String username, UserEntity user) {
    return userRepository.update(username, user);
  }

  public void deleteUser(String username) {
    userRepository.delete(username);
  }
}
