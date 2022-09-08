package com.spring.usermanagement.mockData;

import com.spring.usermanagement.entity.Role;
import com.spring.usermanagement.entity.User;

public class UserMockData {
    public static User mockUser(){
        return new User("test", "test@xx.email", "1234567");
    }

    public static Role mockUserRole() {
        return new Role("user");
    }
    public static Role mockAdminRole() {
        return new Role("admin");
    }
}
