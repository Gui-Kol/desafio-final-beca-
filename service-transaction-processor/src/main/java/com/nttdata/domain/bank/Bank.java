package com.nttdata.domain.bank;

public class Bank {
    private Long account;
    private Long clientId;
    private double balance;
    private double credit;
    private Long id;

    public Bank(Long account, Long clientId, double balance, double credit, Long id) {
        this.account = account;
        this.clientId = clientId;
        this.balance = balance;
        this.credit = credit;
        this.id = id;
    }
    public Long getAccount() {
        return account;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getId() {
        return id;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "account=" + account +
                ", clientId=" + clientId +
                ", balance=" + balance +
                ", credit=" + credit +
                ", id=" + id +
                '}';
    }
}
