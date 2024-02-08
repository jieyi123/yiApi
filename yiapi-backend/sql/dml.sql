-- user
INSERT INTO `user` VALUES (1, 'pjieyi', 'pjieyi', '13222222222', '132', 'https://pjieyi.oss-cn-chengdu.aliyuncs.com/big-event-heima/8f3036ad-aa46-4ffa-bf2c-7de3b6129451.jpg', 1, 'admin', '0b6f76a5e18b654393f186d4ed28a072', '4ad918cf5ef7a74d9a71dbe836a3bc81', 'c23e79e9432490b53af052e207f9a8495235b9e0', '2024-01-24 17:45:46', '2024-02-02 10:34:46', 0);
INSERT INTO `user` VALUES (2, 'zs1234', 'zhangsan', '13212312313', '12345@qq.com', 'https://pjieyi.oss-cn-chengdu.aliyuncs.com/public/ffd16276-422b-43f0-b1d1-a83473322ce4.jpg', 0, 'admin', '56191e5f69f082ef3e10280f2ad31672', '666a4edfe377a499ba364abeb1985138', 'e6e7531f540fcef07c2e1e6988ae3ad93ff0201c', '2024-01-25 20:57:19', '2024-02-07 20:39:37', 0);

-- interface_info
INSERT INTO `interface_info` VALUES (1, '接口', '11', 'http:wwww.baidu.com', 'header', 'json', '', NULL, 0, 'get', 2, '2024-01-27 20:33:30', '2024-02-02 14:51:36', 0);
INSERT INTO `interface_info` VALUES (2, 'getRandomImageUrl', '随机获取动漫图片URL', 'http://localhost:8081/api/interface/random/image', '无', '无', '无', NULL, 1, 'post', 2, '2024-01-30 19:04:29', '2024-02-08 19:40:38', 0);
INSERT INTO `interface_info` VALUES (3, 'getBaiduHot', '百度每日热点', 'http://localhost:8081/api/interface/baiduHot', '无', '无', '无', NULL, 1, 'delete', 2, '2024-01-30 19:50:29', '2024-02-08 19:03:25', 0);
INSERT INTO `interface_info` VALUES (9, 'getNameByPostRestful', '获取用户名', 'http://localhost:8081/api/name/user', '{\n  \"Content-Type\": \"application/json\"\n}', '{\n  \"Content-Type\": \"application/json\"\n}', '[\n  {\n    \"name\": \"name\",\n    \"type\": \"string\"\n  }\n]', '{ \"name\": \"czq\"}', 1, 'post', 2, '2024-02-02 17:19:09', '2024-02-08 16:50:32', 0);
INSERT INTO `interface_info` VALUES (10, 'getRandomWork', '获取随机文本', 'http://localhost:8081/api/interface/random/word', '无', '无', '无', NULL, 0, 'get', 2, '2024-02-06 19:57:04', '2024-02-08 16:59:11', 0);
INSERT INTO `interface_info` VALUES (11, 'getDayWallpaperUrl', '每日壁纸URL', 'http://localhost:8081/api/interface/day/wallpaper', '无', '无', '无', NULL, 1, 'get', 2, '2024-02-06 19:58:10', '2024-02-08 19:40:32', 0);

-- user_interface_info
INSERT INTO `user_interface_info` VALUES (1, 1, 9, 5, 1, 0, '2024-02-03 18:41:02', '2024-02-06 15:30:18', 0);
INSERT INTO `user_interface_info` VALUES (8, 2, 9, 4, 7, 0, '2024-02-07 18:12:31', '2024-02-08 19:43:07', 0);
INSERT INTO `user_interface_info` VALUES (9, 2, 10, 1, 49, 0, '2024-02-08 16:58:15', '2024-02-08 16:58:22', 0);
INSERT INTO `user_interface_info` VALUES (10, 2, 2, 2, 48, 0, '2024-02-08 17:27:31', '2024-02-08 17:45:10', 0);
INSERT INTO `user_interface_info` VALUES (11, 2, 11, 7, 43, 0, '2024-02-08 17:29:57', '2024-02-08 18:50:53', 0);
INSERT INTO `user_interface_info` VALUES (12, 2, 3, 13, 37, 0, '2024-02-08 19:03:12', '2024-02-08 19:33:03', 0);