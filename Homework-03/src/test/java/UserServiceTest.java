import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import org.volkov.hellotest.dao.UserDao;
import org.volkov.hellotest.entity.UserEntity;
import org.volkov.hellotest.service.UserServiceImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;

    @BeforeEach
    void setup() {
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setName("Тест");
        testUser.setEmail("test@mail.ru");
    }

    @Test
    void testCreateUser_ShouldCallDaoCreate() {
        userService.createUser(testUser);
        verify(userDaoMock).create(testUser);
    }

    @Test
    void testGetUserById_UserExists_ShouldReturnUser() {
        // Arrange
        when(userDaoMock.get(1L)).thenReturn(Optional.of(testUser));

        // Act
        Optional<UserEntity> result = userService.getUserById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Тест", result.get().getName());
        verify(userDaoMock).get(1L);
    }

    @Test
    void testGetUserById_UserDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(userDaoMock.get(999L)).thenReturn(Optional.empty());

        // Act
        Optional<UserEntity> result = userService.getUserById(999L);

        // Assert
        assertTrue(result.isEmpty());
        verify(userDaoMock).get(999L);
    }

    @Test
    void testUpdateUser_ShouldReturnUpdatedUser() {
        // Arrange
        when(userDaoMock.get(1L)).thenReturn(Optional.of(testUser));
        testUser.setName("Вася");

        // Act
        userService.updateUser(testUser);

        // Assert
        verify(userDaoMock).update(testUser);
        Optional<UserEntity> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("Вася", result.get().getName());
    }

    @Test
    void testDeleteUser_ShouldReturnEmpty() {
        // Arrange
        when(userDaoMock.get(1L)).thenReturn(Optional.empty());

        // Act
        userService.deleteUser(testUser);

        // Asert
        verify(userDaoMock).delete(testUser);
        Optional<UserEntity> result = userService.getUserById(1L);
        assertTrue(result.isEmpty());
    }
}
