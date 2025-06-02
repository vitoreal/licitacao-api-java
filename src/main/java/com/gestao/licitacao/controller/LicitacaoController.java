package com.gestao.licitacao.controller;

import com.gestao.licitacao.dto.LicitacaoDTO;
import com.gestao.licitacao.service.LicitacaoService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/licitacao")
public class LicitacaoController {

    @Autowired
    private LicitacaoService service;
 
    @GetMapping
    public ResponseEntity<Map<String, Object>> listar(
            @RequestParam(required = false) String codigo_uasg,
            @RequestParam(required = false) String numero_pregao,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest request
    ) {
    	Map<String, Object> response = service.buscarLicitacoes(codigo_uasg, numero_pregao, page, size, request);
    	return ResponseEntity.ok(response);

    }

    @GetMapping("/importacao")
    public ResponseEntity<String> capturar() {
        try {
            service.capturarLicitacoes();
            return ResponseEntity.ok("Licitacoes capturadas com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao capturar dados.");
        }
    }

}
