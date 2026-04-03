import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.volkov.hellotest.dao.UserDao;
import org.volkov.hellotest.entity.UserEntity;
import org.volkov.hellotest.service.UserService;
import org.volkov.hellotest.service.UserServiceImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDaoMock;

    private UserService userService;

    private UserEntity testUser;

    @BeforeEach
    void setup() {
        userDaoMock = mock(UserDao.class);
        userService = new UserServiceImpl(userDaoMock);

        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setName("Тест");
        testUser.setEmail("test@mail.ru");
    }

    @Test
    void testCreateUser_ShouldCallDaoCreate() {
        userService.createUser(testUser);
        verify(userDaoMock, times(1)).create(testUser);
    }

    @Test
    void testGetUserById_UserExists_ShouldReturnUser() {
        when(userDaoMock.get(1L)).thenReturn(Optional.of(testUser));
        Optional<UserEntity> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("Тест", result.get().getName());
        verify(userDaoMock).get(1L);
    }

    @Test
    void testGetUserById_UserDoesNotExist_ShouldReturnEmpty() {
        when(userDaoMock.get(999L)).thenReturn(Optional.empty());
        Optional<UserEntity> result = userService.getUserById(999L);
        assertTrue(result.isEmpty());
        verify(userDaoMock).get(999L);
    }

    @Test
    void testUpdateUser_ShouldReturnUpdatedUser() {
        when(userDaoMock.get(1L)).thenReturn(Optional.of(testUser));
        testUser.setName("Вася");
        userService.updateUser(testUser);
        verify(userDaoMock, times(1)).update(testUser);
        Optional<UserEntity> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("Вася", result.get().getName());
    }

    @Test
    void testDeleteUser_ShouldReturnEmpty() {
        when(userDaoMock.get(1L)).thenReturn(Optional.empty());
        userService.deleteUser(testUser);
        verify(userDaoMock, times(1)).delete(testUser);
        Optional<UserEntity> result = userService.getUserById(1L);
        assertTrue(result.isEmpty());
    }
}
