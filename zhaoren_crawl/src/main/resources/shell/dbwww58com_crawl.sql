CREATE TABLE `t_boqii` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id���',
  `company_name` varchar(100) NOT NULL COMMENT '��˾����',
  `company_addr` varchar(200) NOT NULL COMMENT '��˾��ַ',
  `mobile` varchar(20) NOT NULL COMMENT '�ֻ���',
  `url` varchar(200) NOT NULL COMMENT 'ҳ��url��ַ',
  `catalog` varchar(200) NOT NULL COMMENT 'Ŀ¼��Ϣ',
  `create_time` datetime NOT NULL COMMENT '����ʱ��',
  `update_time` datetime NOT NULL COMMENT '����ʱ��',
  `other_info` varchar(200) NOT NULL COMMENT '������Ϣ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ץȡ�����ݲ��������Ϣ';