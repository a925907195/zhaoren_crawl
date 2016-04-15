CREATE TABLE `t_boqii` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id编号',
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `company_addr` varchar(200) NOT NULL COMMENT '公司地址',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `url` varchar(200) NOT NULL COMMENT '页面url地址',
  `catalog` varchar(200) NOT NULL COMMENT '目录信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `other_info` varchar(200) NOT NULL COMMENT '其他信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抓取的数据波奇服务信息';