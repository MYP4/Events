package data.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class User {
    private Long id;
    private String firstName;
    private String secondName;
    private UserRole role;
    private String accountNumber;
    private BigDecimal balance;
    private String login;
    private String password;
    private UUID uid;

    public User(Long id, String firstName, String secondName, UserRole role, String accountNumber, BigDecimal balance, String email, UUID uid) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.login = email;
        this.uid = uid;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}

