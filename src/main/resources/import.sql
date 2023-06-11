INSERT INTO users (id, created, updated, email, name, password_hash, recovery_code, recovery_code_expiration_date,
                   surname, role, verification_code, verification_code_expiration_date, is_verified)
VALUES ('user1', now(), now(), 'test@test.com', 'John', '$2a$10$aqvJCH43/2Nt1JmoT5nn5OrnFt05VtjE87lI/xw7mnoSKO.NejYfW',
        null, null, 'Doe', 'ADMIN', null, null, true);


INSERT INTO users (id, created, updated, email, name, password_hash, recovery_code, recovery_code_expiration_date,
                   surname, role, verification_code, verification_code_expiration_date, is_verified)
VALUES ('user2', now(), now(), 'tes@gmail.com', 'John', '$2a$10$2.wEuDQ3kHAG7Qw//s3UT.DQr7O694gSlcLx6j/ShSQkCBF6Fzz7u',
        null, null, 'Doe', 'ADMIN', null, null, true);


INSERT INTO users (id, created, updated, email, name, password_hash, recovery_code, recovery_code_expiration_date,
                   surname, role, verification_code, verification_code_expiration_date, is_verified)
VALUES ('testuser', now(), now(), 'testuser@gmail.com', 'John', '$2a$10$2.wEuDQ3kHAG7Qw//s3UT.DQr7O694gSlcLx6j/ShSQkCBF6Fzz7u',
        null, null, 'Doe', 'USER', null, null, true);

INSERT INTO categories (id , created , updated , is_super_category , name , parent_id )
VALUES ('123' , now(),now() , true , 'basecategory'  , null);

INSERT INTO categories (id , created , updated , is_super_category , name , parent_id )
VALUES ('1234' , now(),now() , false , 'Rockcategory'  , '123');
INSERT INTO subscription (id, created, updated, duration, is_active, monthly_fee, name)
VALUES   ('238', now(), now(), 3, true, 20, 'Premium');
INSERT INTO subscription (id, created, updated, duration, is_active, monthly_fee, name)
VALUES   ('234', now(), now(), 6, true, 35, 'SuperPremium');
INSERT INTO subscription (id, created, updated, duration, is_active, monthly_fee, name)
VALUES   ('244', now(), now(), 12, true, 60, 'PrimaPremium');
