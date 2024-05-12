package serviceTest;

import data.entity.User;
import data.exceptions.DBException;
import data.mappers.CreateUserModelToUserMapper;
import data.mappers.UserModelToUserMapper;
import data.mappers.UserToUserModelMapper;
import data.models.CreateUserModel;
import data.models.UserModel;
import data.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.UserService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserToUserModelMapper userToUserModelMapper;

    @Mock
    private UserModelToUserMapper userModelToUserMapper;

    @Mock
    private CreateUserModelToUserMapper createUserModelToUserMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAll() throws DBException {
        // Setup
        List<User> users = List.of(new User(), new User());
        when(userRepository.getAll()).thenReturn(users);
        when(userToUserModelMapper.map(any(User.class))).thenReturn(new UserModel());

        // Execute
        List<UserModel> result = userService.getAll();

        // Verify
        verify(userRepository, times(1)).getAll();
        verify(userToUserModelMapper, times(users.size())).map(any(User.class));
        assertEquals(users.size(), result.size());
    }

    @Test
    void testGetByLogin() throws DBException {
        // Setup
        String login = "testUser";
        User user = new User();
        user.setLogin(login);
        when(userRepository.getByLogin(login)).thenReturn(user);
        when(userToUserModelMapper.map(user)).thenReturn(new UserModel());

        // Execute
        UserModel result = userService.getByLogin(login);

        // Verify
        verify(userRepository, times(1)).getByLogin(login);
        verify(userToUserModelMapper, times(1)).map(user);
        assertNotNull(result);
    }

    @Test
    void testGetByLoginPassword() throws DBException {
        // Setup
        String login = "testUser";
        String password = "testPassword";
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        when(userRepository.getByLoginPassword(login, password)).thenReturn(user);
        when(userToUserModelMapper.map(user)).thenReturn(new UserModel());

        // Execute
        UserModel result = userService.getByLoginPassword(login, password);

        // Verify
        verify(userRepository, times(1)).getByLoginPassword(login, password);
        verify(userToUserModelMapper, times(1)).map(user);
        assertNotNull(result);
    }

    @Test
    void testGetById() throws DBException {
        // Setup
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setUid(id);
        when(userRepository.getById(id)).thenReturn(user);
        when(userToUserModelMapper.map(user)).thenReturn(new UserModel());

        // Execute
        UserModel result = userService.getById(id);

        // Verify
        verify(userRepository, times(1)).getById(id);
        verify(userToUserModelMapper, times(1)).map(user);
        assertNotNull(result);
    }

    @Test
    void testCreate() throws DBException {
        // Setup
        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setLogin("testUser");
        createUserModel.setPassword("testPassword");
        createUserModel.setFirstName("Test");
        createUserModel.setSecondName("User");
        User user = new User();
        user.setLogin(createUserModel.getLogin());
        user.setPassword(createUserModel.getPassword());
        when(userRepository.create(user)).thenReturn(user);
        when(userToUserModelMapper.map(user)).thenReturn(new UserModel());
        when(createUserModelToUserMapper.map(createUserModel)).thenReturn(user);

        // Execute
        UserModel result = userService.create(createUserModel);

        // Verify
        verify(userRepository, times(1)).create(user);
        verify(userToUserModelMapper, times(1)).map(user);
        assertNotNull(result);
    }

    @Test
    void testDelete() throws DBException {
        // Setup
        UUID id = UUID.randomUUID();
        when(userRepository.delete(id)).thenReturn(true);

        // Execute
        userService.delete(id);

        // Verify
        verify(userRepository, times(1)).delete(id);
    }
}
