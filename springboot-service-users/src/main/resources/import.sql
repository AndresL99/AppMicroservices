INSERT INTO `users` (username, password, enabled, name, lastName, email) VALUES ('andres', '1234', 1, 'Andres', 'Lerner', 'andres@gmail.com');
INSERT INTO `users` (username, password, enabled, name, lastName, email) VALUES ('admin', '0001', 1, 'Admin', 'Admin', 'admin@gmail.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `user_for_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `user_for_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `user_for_roles` (user_id, role_id) VALUES (2, 1);