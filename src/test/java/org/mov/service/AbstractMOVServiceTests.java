package org.mov.service;

import org.junit.Test;
import org.mov.model.User;
import org.mov.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractMOVServiceTests {
    @Autowired
    private MOVService movService;

    @Test
    public void shouldFindUserById() {
        User user = movService.findUserById(1L);

        assertNotNull(user);
        assertEquals(Long.valueOf(1L), user.getId());
    }

    @Test
    public void shouldUpdateUser() {
        User user = movService.findUserById(1L);
        user.setFirstName("Leonti");
        user.setLastName("Brechko");
        user.setEmail("leontibrechko@fake.com");
        user.setUsername("leontibrechk");
        user.setRole(UserRole.ADMIN);
        user.setPassword("12345678");

        movService.saveUser(user);

        user = movService.findUserById(1L);
        assertEquals("Leonti", user.getFirstName());
    }
}
