package data.repositories.services;

import data.entity.User;
import data.exceptions.CreationException;
import data.mappers.CreateUserModelToUserMapper;
import data.mappers.UserModelToUserMapper;
import data.mappers.UserToUserModelMapper;
import data.models.CreateUserModel;
import data.models.UserModel;
import data.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final UserToUserModelMapper userToUserModelMapper;
    private final UserModelToUserMapper userModelToUserMapper;
    private final CreateUserModelToUserMapper createUserModelToUserMapper;

    public UserService(UserRepository userRepository,
                       UserToUserModelMapper userToUserModelMapper,
                       UserModelToUserMapper userModelToUserMapper,
                       CreateUserModelToUserMapper createUserModelToUserMapper) {
        this.userRepository = userRepository;
        this.userToUserModelMapper = userToUserModelMapper;
        this.userModelToUserMapper = userModelToUserMapper;
        this.createUserModelToUserMapper = createUserModelToUserMapper;
    }

    public List<UserModel> getAll() {
        List<User> users = userRepository.getAll();
        return users.stream().map(userToUserModelMapper::map).toList();
    }

    public UserModel getById(UUID id) {
        return userToUserModelMapper.map(userRepository.getById(id));
    }

    public UserModel create(CreateUserModel createUserModel) throws CreationException {
        try {
            String login = createUserModel.getLogin();
            if (login == null) {
                throw new CreationException("Failed to create user: login not exists");
            }
            String password = createUserModel.getPassword();
            if (password == null) {
                throw new CreationException("Failed to create user: password not exists");
            }
            String firstName = createUserModel.getFirstName();
            if (firstName == null) {
                throw new CreationException("Failed to create user: firstName not exists");
            }
            String secondName = createUserModel.getSecondName();
            if (secondName == null) {
                throw new CreationException("Failed to create user: secondName not exists");
            }

            User user = createUserModelToUserMapper.map(createUserModel);
            return userToUserModelMapper.map(userRepository.create(user));
        } catch (Exception e){
            throw new CreationException("Failed to create user: " + e.getMessage());
        }
    }

    public void update(UserModel userModel) {
        userRepository.update(userModelToUserMapper.map(userModel));}
    public void delete(UUID id) {
        userRepository.delete(id);
    }
}
