package com.example.moattravel.security;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.moattravel.entity.User;
import com.example.moattravel.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("ログイン試行: " + email); // 🔍 まずはログイン試行のログを出力

        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            System.out.println("⚠️ データベースに該当するユーザーが見つかりませんでした！");
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }

        System.out.println("データベースから取得したユーザー: " + user);
        System.out.println("データベースのパスワード: " + user.getPassword());

        String userRoleName = user.getRole().getName();
        System.out.println("ユーザーの権限: " + userRoleName);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRoleName));

        return new UserDetailsImpl(user, authorities);
    }


    }

