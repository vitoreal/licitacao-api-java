package com.gestao.licitacao.controller;

import com.gestao.licitacao.model.Licitacao;
import com.gestao.licitacao.service.ImportarLicitacaoService;
import com.gestao.licitacao.service.LicitacaoService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/licitacao")
public class LicitacaoController {

    @Autowired
    private LicitacaoService service;
    
    @Autowired
    private ImportarLicitacaoService importarService;
 
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
    
    @PutMapping("/{id}/visualizada")
    public ResponseEntity<?> marcarComoVisualizada(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            Optional<Licitacao> licitacaoOptional = service.buscarPorId(id);

            if (licitacaoOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Licitacao não encontrada"));
            }

            Licitacao licitacao = licitacaoOptional.get();

            boolean visualizada = Boolean.parseBoolean(payload.getOrDefault("visualizada", false).toString());
            licitacao.setVisualizada(visualizada);

            Licitacao salvo = service.salvar(licitacao);

            return ResponseEntity.ok(Map.of(
                    "message", "Solicitação realizada com sucesso.",
                    "data", salvo
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/importacao")
    public ResponseEntity<?> importar() {
        try {
        	importarService.importarLicitacoes();
            return ResponseEntity.ok(Map.of(
                    "message", "Solicitação realizada com sucesso."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Erro ao capturar dados."
            ));
        }
    }

}
