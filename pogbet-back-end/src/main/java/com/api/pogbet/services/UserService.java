package com.api.pogbet.services;

import com.api.pogbet.model.Aposta;
import com.api.pogbet.model.User;
import com.api.pogbet.repository.UserRepository;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Table(name = "[user]")
public class UserService {
    //CLASSE RESPONSÁVEL PELAS REGRAS DE NEGÓCIO
    private final UserRepository userRepository;

    public void replace(User user) {
        User savedUser = findById(user.getId());
        user.setId(savedUser.getId());
        userRepository.save(user);
    }
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
