package com.example.jwtsecurity.service;

import com.example.jwtsecurity.dto.UserResponse;
import com.example.jwtsecurity.entity.Role;
import com.example.jwtsecurity.entity.User;
import com.example.jwtsecurity.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Spy
    @InjectMocks
    private UserService userService;

    @BeforeAll
    static void m4() {
        System.out.println("========== BeforeAll ==========");
    }

    @BeforeEach
    void m2() {
        System.out.println("========== BeforeEach ==========");
    }

    @Test
    void testGetUserById() {

        System.out.println("========== testGetUserById ==========");

        User user = new User();

        user.setId(1L);
        user.setName("Pranav");
        user.setEmail("pranav@gmail.com");
        user.setRole(Role.USER);

        when(repo.findById(1L)).thenReturn(Optional.of(user));

        UserResponse result = userService.byId(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Pranav", result.name());
        assertEquals("pranav@gmail.com", result.email());
        assertEquals(Role.USER, result.role());
        assertNotEquals(2L, result.id());

        verify(repo).findById(1L);
    }

    @Test
    void testUserNotFound() {

        System.out.println("========== testUserNotFound ==========");

        when(repo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.byId(999L));

        verify(repo).findById(999L);
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
    void adminCanDeleteUser() {

        System.out.println("========== adminCanDeleteUser ==========");

        verify(repo).deleteById(1L);
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = "USER")
    void normalUserCannotDeleteUser() {

        System.out.println("========== normalUserCannotDeleteUser ==========");

        verify(repo, never()).deleteById(1L);
    }

    @Test
    void testBooleanAssertions() {

        System.out.println("========== testBooleanAssertions ==========");

        boolean isAdult = true;
        boolean isChild = false;

        assertTrue(isAdult);
        assertFalse(isChild);
    }

    @Test
    void testNullAssertions() {

        System.out.println("========== testNullAssertions ==========");

        String address = null;
        String name = "Pranav";

        assertNull(address);
        assertNotNull(name);
    }

    @Test
    void testSameObject() {

        System.out.println("========== testSameObject ==========");

        String name1 = new String("Pranav");
        String name2 = name1;

        assertSame(name1, name2);
    }

    @Test
    void testDifferentObjects() {

        System.out.println("========== testDifferentObjects ==========");

        String name1 = new String("Pranav");
        String name2 = new String("Pranav");

        assertNotSame(name1, name2);
    }

    @Test
    void testArray() {

        System.out.println("========== testArray ==========");

        int[] expected = {1, 2, 3};
        int[] actual = {1, 2, 3};

        assertArrayEquals(expected, actual);
    }

    @Test
    void testUserUsingAssertAll() {

        System.out.println("========== testUserUsingAssertAll ==========");

        User user = new User();

        user.setId(1L);
        user.setName("Pranav");
        user.setEmail("pranav@gmail.com");
        user.setRole(Role.USER);

        assertAll(
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals("Pranav", user.getName()),
                () -> assertEquals("pranav@gmail.com", user.getEmail()),
                () -> assertEquals(Role.USER, user.getRole()),
                () -> assertNotNull(user.getName()),
                () -> assertNotNull(user.getEmail())
        );
    }

    @Test
    void testDoesNotThrow() {

        System.out.println("========== testDoesNotThrow ==========");

        assertDoesNotThrow(() -> {
            int result = 10 / 2;
            assertEquals(5, result);
        });
    }

    @Test
    void testThrows() {

        System.out.println("========== testThrows ==========");

        assertThrows(ArithmeticException.class, () -> {

            int result = 10 / 0;

        });
    }

    @Test
    void testVerifyTimes() {

        System.out.println("========== testVerifyTimes ==========");

        User user = new User();

        user.setId(1L);
        user.setName("Pranav");
        user.setEmail("pranav@gmail.com");
        user.setRole(Role.USER);

        when(repo.findById(1L)).thenReturn(Optional.of(user));

        userService.byId(1L);

        verify(repo, times(1)).findById(1L);
    }

    @Test
    void testVerifyNever() {

        System.out.println("========== testVerifyNever ==========");

        verify(repo, never()).deleteById(1L);
    }

    @AfterEach
    void m3() {

        System.out.println("========== AfterEach ==========");
    }

    @AfterAll
    static void m5() {

        System.out.println("========== AfterAll ==========");
    }
}