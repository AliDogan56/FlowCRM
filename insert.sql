INSERT INTO app_user (USERNAME, PASSWORD, ROLE, DELETED, CREATED_AT, UPDATED_AT)
VALUES ('admin', '$2a$10$J55TWvjNxvue5ylB1wTsduABMCQWw2Jm9/QJsiDq9MgiCqmt.lGSC', 0, 0, NOW(), NOW());
SET @app_user_id = LAST_INSERT_ID();
INSERT INTO system_owner (id)
VALUES (@app_user_id);

INSERT INTO app_user (USERNAME, PASSWORD, ROLE, DELETED, CREATED_AT, UPDATED_AT)
VALUES ('systemAdmin', '$2a$10$J55TWvjNxvue5ylB1wTsduABMCQWw2Jm9/QJsiDq9MgiCqmt.lGSC', 1, 0, NOW(), NOW());
SET @app_user_id = LAST_INSERT_ID();
INSERT INTO system_admin (id)
VALUES (@app_user_id);


INSERT INTO app_user (USERNAME, PASSWORD, ROLE, DELETED, CREATED_AT, UPDATED_AT)
VALUES ('customer_ali', '$2a$10$J55TWvjNxvue5ylB1wTsduABMCQWw2Jm9/QJsiDq9MgiCqmt.lGSC', 1, 0, NOW(), NOW());
SET @app_user_id = LAST_INSERT_ID();
INSERT INTO customer_user (id, email, first_name, last_name, region)
VALUES (@app_user_id, 'customer_ali@gmail.com', 'Ali', 'Dogan', 'Istanbul');

INSERT INTO app_user (USERNAME, PASSWORD, ROLE, DELETED, CREATED_AT, UPDATED_AT)
VALUES ('customer_ebru', '$2a$10$J55TWvjNxvue5ylB1wTsduABMCQWw2Jm9/QJsiDq9MgiCqmt.lGSC', 1, 0, NOW(), NOW());
SET @app_user_id = LAST_INSERT_ID();
INSERT INTO customer_user (id, email, first_name, last_name, region)
VALUES (@app_user_id, 'customer_ebru@gmail.com', 'Ebru', 'Ozkara', 'Istanbul');

INSERT INTO app_user (USERNAME, PASSWORD, ROLE, DELETED, CREATED_AT, UPDATED_AT)
VALUES ('customer_tugba', '$2a$10$J55TWvjNxvue5ylB1wTsduABMCQWw2Jm9/QJsiDq9MgiCqmt.lGSC', 1, 0, NOW(), NOW());
SET @app_user_id = LAST_INSERT_ID();
INSERT INTO customer_user (id, email, first_name, last_name, region)
VALUES (@app_user_id, 'customer_tugba@gmail.com', 'Tugba', 'Dogan', 'Istanbul');