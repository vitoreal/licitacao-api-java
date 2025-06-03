package com.gestao.licitacao.controller;

import com.gestao.licitacao.model.Licitacao;
import com.gestao.licitacao.service.ImportarLicitacaoService;
import com.gestao.licitacao.service.LicitacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/licitacao")
@Tag(name = "Licitações", description = "Endpoints para consultar e importar licitações")
public class LicitacaoController {

    @Autowired
    private LicitacaoService service;

    @Autowired
    private ImportarLicitacaoService importarService;

    @Operation(summary = "Lista licitações com filtros e paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> listar(
            @Parameter(description = "Código da UASG") @RequestParam(required = false) String codigo_uasg,
            @Parameter(description = "Número do pregão") @RequestParam(required = false) String numero_pregao,
            @Parameter(description = "Página (0-index)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de registros por página") @RequestParam(defaultValue = "5") int size,
            HttpServletRequest request
    ) {
        Map<String, Object> response = service.buscarLicitacoes(codigo_uasg, numero_pregao, page, size, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Marca uma licitação como visualizada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Licitação marcada como visualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Licitação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro ao marcar como visualizada")
    })
    @PutMapping("/{id}/visualizada")
    public ResponseEntity<?> marcarComoVisualizada(
            @Parameter(description = "ID da licitação") @PathVariable Long id,
            @RequestBody Map<String, Object> payload
    ) {
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

    @Operation(summary = "Importa licitações do ComprasNet")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Importação realizada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro ao importar licitações")
    })
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
