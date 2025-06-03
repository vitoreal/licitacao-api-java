package com.gestao.licitacao.service;

import com.gestao.licitacao.dto.LicitacaoDTO;
import com.gestao.licitacao.model.Licitacao;
import com.gestao.licitacao.repository.LicitacaoRepository;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.*;

@Service
public class LicitacaoService {

    private final LicitacaoRepository repository;

    public LicitacaoService(LicitacaoRepository repository) {
        this.repository = repository;
    }
    
    public Map<String, Object> buscarLicitacoes(String codigoUasg, String numeroPregao, int page, int size, HttpServletRequest request) {
        
    	int adjustedPage = Math.max(page - 1, 0); // Garante que nunca seja negativo
    	
    	Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by("id").descending());

        Page<Licitacao> pageResult = repository.findByCodigoUasgContainingAndNumeroPregaoContaining(
                        codigoUasg != null ? codigoUasg : "",
                        numeroPregao != null ? numeroPregao : "",
                        pageable
                );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<LicitacaoDTO> content = pageResult.getContent().stream().map(licitacao -> {
            LicitacaoDTO dto = new LicitacaoDTO();
            dto.setId(licitacao.getId());
            dto.setCodigo_uasg(licitacao.getCodigoUasg());
            dto.setNumero_pregao(licitacao.getNumeroPregao());
            dto.setObjeto(licitacao.getObjeto());
            dto.setVisualizada(licitacao.isVisualizada());
            dto.setData_proposta(
                    licitacao.getDataProposta() != null
                            ? licitacao.getDataProposta().format(formatter)
                            : null
            );
            return dto;
        }).toList();
        
        String baseUrl = request.getRequestURL().toString();

        Map<String, Object> response = new LinkedHashMap<>();
        int currentPage = pageResult.getNumber() + 1;
        int totalPages = pageResult.getTotalPages();
        int from = (pageResult.getNumber() * pageResult.getSize()) + 1;
        int to = Math.min(currentPage * pageResult.getSize(), (int) pageResult.getTotalElements());

        Map<String, Object> nextLink = new LinkedHashMap<>();
        nextLink.put("url", pageResult.hasNext() ? baseUrl + "?page=" + (currentPage + 1) : null);
        nextLink.put("label", "Next »");
        nextLink.put("active", pageResult.hasNext());

        response.put("current_page", currentPage);
        response.put("data", content);
        response.put("first_page_url", baseUrl + "?page=1");
        response.put("from", from);
        response.put("last_page", totalPages);
        response.put("last_page_url", baseUrl + "?page=" + totalPages);
        response.put("links", List.of(nextLink));
        response.put("next_page_url", pageResult.hasNext() ? baseUrl + "?page=" + (currentPage + 1) : null);
        response.put("path", baseUrl);
        response.put("per_page", pageResult.getSize());
        response.put("prev_page_url", pageResult.hasPrevious() ? baseUrl + "?page=" + (currentPage - 1) : null);
        response.put("to", to);
        response.put("total", pageResult.getTotalElements());

        return response;
        
    }
    
    public Optional<Licitacao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Licitacao salvar(Licitacao licitacao) {
        return repository.save(licitacao);
    }

}
