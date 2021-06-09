package efs.task.todoapp.repository;

import efs.task.todoapp.util.Base64Utils;

import java.util.*;
import java.util.function.Predicate;

public class UserRepository implements Repository<String, UserEntity> {
    private final Map<String, UserEntity> usersMap = new HashMap<>();

    @Override
    public String save(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        String key = userEntity.getUsername();
        if (usersMap.containsKey(key)) {
            return null;
        }
        usersMap.put(key, userEntity);
        return key;
    }

    @Override
    public UserEntity query(String s) {
        return usersMap.get(s);
    }

    @Override
    public List<UserEntity> query(Predicate<UserEntity> condition) {
        return new ArrayList<>(usersMap.values());
    }

    @Override
    public UserEntity update(String s, UserEntity userEntity) {
        UserEntity userToUpdate = usersMap.get(s);
        String username = userEntity.getUsername();
        if (username != null && !username.isBlank()) {
            userToUpdate.setUsername(username);
        }

        String password = userEntity.getPassword();
        if (password != null && !password.isBlank()) {
            userToUpdate.setPassword(password);
        }

        return userToUpdate;
    }

    @Override
    public boolean delete(String s) {
        return (usersMap.remove(s) != null);
    }
}
