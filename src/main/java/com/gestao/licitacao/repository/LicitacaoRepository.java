package com.gestao.licitacao.repository;

import com.gestao.licitacao.model.Licitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface LicitacaoRepository extends JpaRepository<Licitacao, Long> {

    Page<Licitacao> findByCodigoUasgContainingAndNumeroPregaoContaining(String codigoUasg, String numeroPregao, Pageable pageable);

    long countByCodigoUasg(String codigoUasg);
}
