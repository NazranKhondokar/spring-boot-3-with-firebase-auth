-- V7__add_new_column_to_users_table.sql
    ALTER TABLE users
    ADD COLUMN avatar BYTEA NULL;

    ALTER TABLE users
    ADD COLUMN avatar_type INT NOT NULL DEFAULT 0;

    ALTER TABLE users
    ADD COLUMN application_theme INT NOT NULL DEFAULT 0;
