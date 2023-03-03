package com.veinsmoke.myrhbackend;

import com.veinsmoke.myrhbackend.entity.Agent;
import com.veinsmoke.myrhbackend.entity.Company;
import com.veinsmoke.myrhbackend.repository.AgentRepository;
import com.veinsmoke.myrhbackend.repository.CompanyRepository;
import com.veinsmoke.myrhbackend.security.CostumeUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @InjectMocks
    private CostumeUserDetailsService userDetailsService;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private AgentRepository agentRepository;

    @Test
    void should_return_valid_company_matching_email_and_type() {

        // Arrange
        String email = "company@test.com";
        Company company = new Company();
        company.setEmail(email);
        company.setPassword("password");

        String emailAndType = email+":company";
        when(companyRepository.findByEmail(email)).thenReturn(Optional.of(company));

        // Act
        UserDetails user = userDetailsService.loadUserByUsername(emailAndType);

        // Assert
        assertNotNull(user);
        assertEquals(user.getUsername(), email);
        assertEquals(user.getAuthorities(), Collections.singleton(new SimpleGrantedAuthority("COMPANY")));

    }

    @Test
    void should_return_valid_agent_matching_email_and_type() {

        // Arrange
        String email = "agent@test.com";
        Agent agent = new Agent();
        agent.setEmail(email);
        agent.setPassword("password");

        String emailAndType = email+":agent";
        when(agentRepository.findByEmail(email)).thenReturn(Optional.of(agent));

        // Act
        UserDetails user = userDetailsService.loadUserByUsername(emailAndType);

        // Assert
        assertNotNull(user);
        assertEquals(user.getUsername(), email);
        assertEquals(user.getAuthorities(), Collections.singleton(new SimpleGrantedAuthority("AGENT")));

    }

    @Test
    void should_throw_UsernameNotFoundException() {
        // Arrange
        String emailAndType = "test@email.com:invalid_type";

        // Act && Assert
        Throwable exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(emailAndType));
        assertEquals("User not found", exception.getMessage());
    }


}
