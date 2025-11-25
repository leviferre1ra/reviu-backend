package br.edu.fatecpg.reviu.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    public String generateCode() {
        int code = (int)(Math.random() * 900000) + 100000; // 100000..999999
        return String.valueOf(code);
    }


}
