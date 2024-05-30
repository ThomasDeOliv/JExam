-- Create schema
CREATE SCHEMA IF NOT EXISTS items_manager_schema;

-- Set search path to user created schema
SET search_path TO items_manager_schema;

-- Create table
CREATE TABLE IF NOT EXISTS item
(
    item_id              BIGSERIAL    NOT NULL,
    item_name            VARCHAR(256) NOT NULL,
    item_description     TEXT         NULL,
    item_start_at        TIMESTAMPTZ  NOT NULL DEFAULT now(),
    item_end_at          TIMESTAMPTZ  NULL,
    item_related_item_id BIGINT       NULL     DEFAULT NULL,
    CONSTRAINT PK_item__item_id PRIMARY KEY (item_id),
    CONSTRAINT FK_item_item__item_related_item_id FOREIGN KEY (item_related_item_id) REFERENCES item (item_id) ON DELETE CASCADE
);

-- Insert datas for testing
INSERT INTO item(item_name, item_description, item_start_at)
VALUES ('Création de site internet', 'Mise en place d''un site WordPress.', '2022-10-10 11:30:30-00'),
       ('Test de fonctionnalités', 'Réalisation de tests unitaires.', '2023-10-10 11:30:30-00'),
       ('Achat licence SQL Server', NULL, '2024-10-10 11:30:30-00');