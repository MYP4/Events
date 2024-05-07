package data.mappers;

import data.entity.User;
import data.models.UserModel;

public class UserToUserModelMapper implements Mapper<User, UserModel> {

    @Override
    public UserModel map(User user) {
        if (user == null) {
            return null;
        }

        UserModel userModel = new UserModel();
        userModel.setFirstName(user.getFirstName());
        userModel.setSecondName(user.getSecondName());
        userModel.setRole(user.getRole());
        userModel.setAccountNumber(user.getAccountNumber());
        userModel.setBalance(user.getBalance());
        userModel.setLogin(user.getLogin());
        userModel.setUid(user.getUid());

        return userModel;
    }
}