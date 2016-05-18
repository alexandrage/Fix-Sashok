SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

INSERT IGNORE `permissions` (`id`, `name`, `type`, `permission`, `world`, `value`) VALUES
(0, 'Admin', 0, '*', ' ', ' '),
(1, 'user', 0, 'modifyworld.*', '', ''),
(2, 'vip', 0, 'modifyworld.*', '', ''),
(3, 'premium', 0, 'modifyworld.*', '', ''),
(4, 'demo', 1, 'group-premium-until', ' ', '1391024852');

INSERT IGNORE `permissions_entity` (`id`, `name`, `type`, `prefix`, `suffix`, `default`) VALUES
(1, 'user', 0, '&f[&7User&f]', '', 1),
(3, 'vip', 0, '&f[&6V.I.P&f]', '', 0),
(4, 'premium', 0, '&f[&2Premium&f]', '', 0),
(5, 'Admin', 0, '&f[&4Admin&f]', '', 0);
