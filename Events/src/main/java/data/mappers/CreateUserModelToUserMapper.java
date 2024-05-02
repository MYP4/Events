package data.mappers;

import data.entity.Event;
import data.entity.User;
import data.entity.UserRole;
import data.models.CreateUserModel;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateUserModelToUserMapper implements Mapper<CreateUserModel, User> {
    @Override
    public User map(CreateUserModel userModel) {
        if (userModel == null) {
            return null;
        }

        User user = new User();
        UUID uid = UUID.randomUUID();

        user.setUid(uid);
        user.setFirstName(userModel.getFirstName());
        user.setSecondName(userModel.getSecondName());
        user.setLogin(userModel.getLogin());
        user.setPassword(userModel.getPassword());
        user.setAccountNumber(uid.toString().toUpperCase().replaceAll("[0-9-]", ""));
        user.setBalance(new BigDecimal(0));
        user.setRole(UserRole.REGULAR);

        return user;
    }
}
