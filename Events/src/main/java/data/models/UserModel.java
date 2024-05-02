package data.models;

import data.entity.UserRole;

import java.math.BigDecimal;
import java.util.UUID;

public class UserModel {
    private String firstName;
    private String secondName;
    private UserRole role;
    private String accountNumber;
    private BigDecimal balance;
    private String email;
    private UUID uid;

    public UserModel() {
    }

    public UserModel(String firstName, String secondName, UserRole role, String accountNumber, BigDecimal balance, String email, UUID uid) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.email = email;
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}
