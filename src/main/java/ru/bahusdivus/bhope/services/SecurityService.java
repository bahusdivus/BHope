package ru.bahusdivus.bhope.services;

public interface SecurityService {
  String findLoggedInLogin();
  void autoLogin(String login, String password);
}
