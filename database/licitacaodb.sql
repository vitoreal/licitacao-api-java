CREATE TABLE `licitacao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo_uasg` varchar(255) DEFAULT NULL,
  `data_proposta` datetime(6) DEFAULT NULL,
  `numero_pregao` varchar(255) DEFAULT NULL,
  `objeto` text DEFAULT NULL,
  `visualizada` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;