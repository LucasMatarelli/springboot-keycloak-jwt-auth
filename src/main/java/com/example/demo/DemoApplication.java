package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // Importação necessária

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo") // Garante que o Spring encontre todos os seus arquivos (Controllers e Configurações)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}