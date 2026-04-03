package org.volkov.hellotest.service;

import org.volkov.hellotest.dao.UserDao;
import org.volkov.hellotest.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public void createUser(UserEntity user) {
        userDao.create(user);
    }

    @Override
    public Optional<UserEntity> getUserById(long id) {
        return userDao.get(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userDao.getAll();
    }

    @Override
    public void updateUser(UserEntity user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(UserEntity user) {
        userDao.delete(user);
    }
}
