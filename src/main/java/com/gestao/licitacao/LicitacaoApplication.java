package com.gestao.licitacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.gestao.licitacao"})
public class LicitacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicitacaoApplication.class, args);
    }
}
