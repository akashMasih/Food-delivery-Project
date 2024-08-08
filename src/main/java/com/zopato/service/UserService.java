package com.zopato.service;

import com.zopato.model.User;

public interface UserService {

    public User getUserByJwtToken(String jwtToken) throws Exception;

    public User getUserByEmail(String email) throws Exception;

}
