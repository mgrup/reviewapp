package com.review.service;

import com.review.entity.User;
import com.review.exception.EmailExistsException;

public interface IUserService {
    User registerNewUserAccount(User userAccount) throws EmailExistsException;
}
