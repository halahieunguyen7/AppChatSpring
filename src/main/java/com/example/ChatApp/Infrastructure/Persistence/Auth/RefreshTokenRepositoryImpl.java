package com.example.ChatApp.Infrastructure.Persistence.Auth;

import com.example.ChatApp.Domain.Auth.Model.RefreshToken;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.Repository.RefreshTokenRepository;
import com.example.ChatApp.Infrastructure.Mapper.RefreshTokenMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RefreshTokenRepositoryImpl
        implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository jpaRepository;

    public RefreshTokenRepositoryImpl(
            RefreshTokenJpaRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(RefreshToken refreshToken) {
        jpaRepository.save(
                RefreshTokenMapper.toEntity(refreshToken)
        );
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRepository.findByToken(token)
                .map(RefreshTokenMapper::toDomain);
    }

    @Override
    public void revokeByUserId(UserId userId) {
        jpaRepository.deleteByUserId(userId.value());
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        jpaRepository.deleteById(refreshToken.getId());
    }
}