package services;

import data.entity.User;
import data.exceptions.CreationException;
import data.exceptions.DBException;
import data.mappers.CreateUserModelToUserMapper;
import data.mappers.UserModelToUserMapper;
import data.mappers.UserToUserModelMapper;
import data.models.CreateUserModel;
import data.models.UserModel;
import data.repositories.UserRepository;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final UserToUserModelMapper userToUserModelMapper;
    private final UserModelToUserMapper userModelToUserMapper;
    private final CreateUserModelToUserMapper createUserModelToUserMapper;
    private static final Logger logger = Logger.getLogger(UserService.class);

    public UserService(UserRepository userRepository,
                       UserToUserModelMapper userToUserModelMapper,
                       UserModelToUserMapper userModelToUserMapper,
                       CreateUserModelToUserMapper createUserModelToUserMapper) {
        this.userRepository = userRepository;
        this.userToUserModelMapper = userToUserModelMapper;
        this.userModelToUserMapper = userModelToUserMapper;
        this.createUserModelToUserMapper = createUserModelToUserMapper;
    }

    public List<UserModel> getAll() throws DBException {
        try {
            List<User> users = userRepository.getAll();
            return users.stream().map(userToUserModelMapper::map).toList();
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public UserModel getById(UUID id) throws DBException {
        try {
            return userToUserModelMapper.map(userRepository.getById(id));
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public UserModel create(CreateUserModel createUserModel) throws DBException  {
        try {
            String login = createUserModel.getLogin();
            if (login == null) {
                logger.error("Failed to create user: login not exists");
                throw new DBException("Failed to create user: login not exists");
            }
            String password = createUserModel.getPassword();
            if (password == null) {
                logger.error("Failed to create user: password not exists");
                throw new DBException("Failed to create user: password not exists");
            }
            String firstName = createUserModel.getFirstName();
            if (firstName == null) {
                logger.error("Failed to create user: firstName not exists");
                throw new DBException("Failed to create user: firstName not exists");
            }
            String secondName = createUserModel.getSecondName();
            if (secondName == null) {
                logger.error("Failed to create user: secondName not exists");
                throw new DBException("Failed to create user: secondName not exists");
            }
            User user = createUserModelToUserMapper.map(createUserModel);
            return userToUserModelMapper.map(userRepository.create(user));
        } catch (DBException e){
            logger.error(e.getMessage());
            throw new DBException("Failed to create user: " + e.getMessage());
        }
    }

    public void update(UserModel userModel) throws DBException {
        try {
            userRepository.update(userModelToUserMapper.map(userModel));
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public void delete(UUID id) throws DBException{
        try {
            userRepository.delete(id);
        } catch (DBException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }
}
