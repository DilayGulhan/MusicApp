INSERT INTO users (id, created, updated, email, name, password_hash, recovery_code, recovery_code_expiration_date,
                   surname, role, verification_code, verification_code_expiration_date, is_verified)
VALUES ('user1', now(), now(), 'test@test.com', 'John', '$2a$10$aqvJCH43/2Nt1JmoT5nn5OrnFt05VtjE87lI/xw7mnoSKO.NejYfW',
        null, null, 'Doe', 'ADMIN', null, null, true);