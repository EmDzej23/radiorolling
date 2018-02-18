INSERT INTO `spring-security-tutorial`.`role` (`role_id`, `role`) VALUES (NULL, 'ADMIN');
INSERT INTO `spring-security-tutorial`.`user` (`user_id`, `active`, `last_name`, `name`, `password`, `username`,`image`) VALUES (NULL, '1', 'Jereminov', 'Marko', 'm', 'Klaudije','http://localhost:8080/res/images/mj1.jpg');
INSERT INTO `spring-security-tutorial`.`post` (`id`, `body`, `date`, `post_html`, `title`, `author_id`, `post_type`) VALUES (NULL, 'Tekst', '2017-11-02 00:00:00', '<div class="mj">MJ</div>', 'Title', '1', '0');
INSERT INTO `spring-security-tutorial`.`comment` (`id`, `comment_type`, `text`, `comment_id`, `comment_author`, `post_id`, `upvote_count`,`user_has_upvoted`) VALUES (NULL, '1', 'Comment 1', NULL, '1', '1','0',false);
INSERT INTO `spring-security-tutorial`.`comment` (`id`, `comment_type`, `text`, `comment_id`, `comment_author`, `post_id`, `upvote_count`,`user_has_upvoted`) VALUES (NULL, '1', 'Reply to Comment 1', '1', '1', NULL,'0',false);
INSERT INTO `spring-security-tutorial`.`user_role` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `spring-security-tutorial`.`tag` (`id`, `name`) VALUES (NULL, 'TAG1');
INSERT INTO `spring-security-tutorial`.`post_tag` (`post_id`, `tag_id`) VALUES ('1', '1');
