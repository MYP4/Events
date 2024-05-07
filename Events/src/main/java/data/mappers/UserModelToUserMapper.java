package data.mappers;

import data.entity.User;
import data.models.UserModel;

public class UserModelToUserMapper implements Mapper<UserModel, User> {

    @Override
    public User map(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setSecondName(userModel.getSecondName());
        user.setRole(userModel.getRole());
        user.setAccountNumber(userModel.getAccountNumber());
        user.setBalance(userModel.getBalance());
        user.setLogin(userModel.getLogin());
        user.setUid(userModel.getUid());

        return user;
    }
}