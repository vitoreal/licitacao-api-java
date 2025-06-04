package com.gestao.licitacao.service;

import com.gestao.licitacao.model.Licitacao;
import com.gestao.licitacao.repository.LicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class ImportarLicitacaoServiceTest {

    @Mock
    private LicitacaoRepository repository;

    @InjectMocks
    @Spy
    private ImportarLicitacaoService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveImportarLicitacaoQuandoNaoExisteNoRepositorio() throws Exception {
        // HTML de exemplo com dados de uma licitação
        String htmlSimulado =
            "<table><tr><td>" +
            "Código da UASG: 123456<br>" +
            "Pregão Eletrônico Nº 45/2025<br>" +
            "Objeto: Pregão Eletrônico - Aquisição de material de escritório<br>" +
            "Entrega da Proposta: 02/06/2025 às 09:00Hs<br>" +
            "</td></tr></table>";

        // Força o método obterPaginaLicitacoes() a retornar HTML simulado
        doReturn(htmlSimulado).when(service).obterPaginaLicitacoes();

        when(repository.existsByCodigoUasgAndNumeroPregao("123456", "45/2025"))
            .thenReturn(false); // Simula que ainda não existe no banco

        // Executa o método a ser testado
        service.importarLicitacoes();

        // Verifica se o método save foi chamado com a licitação correta
        ArgumentCaptor<Licitacao> captor = ArgumentCaptor.forClass(Licitacao.class);
        verify(repository, times(1)).save(captor.capture());

        Licitacao salva = captor.getValue();
       
        assert salva.getCodigoUasg().equals("123456");
        assert salva.getNumeroPregao().equals("45/2025");
        assert salva.getObjeto().equals("Aquisição de material de escritório");
        assert salva.getDataProposta().equals(LocalDateTime.of(2025, 6, 2, 9, 0));
        assert !salva.isVisualizada();
    }

    @Test
    public void naoDeveSalvarLicitacaoQuandoJaExiste() throws Exception {
        String htmlSimulado =
            "<table><tr><td>" +
            "Código da UASG: 123456<br>" +
            "Pregão Eletrônico Nº 45/2025<br>" +
            "Objeto: Pregão Eletrônico - Aquisição de papel A4<br>" +
            "Entrega da Proposta: 02/06/2025 às 09:00Hs<br>" +
            "</td></tr></table>";

        doReturn(htmlSimulado).when(service).obterPaginaLicitacoes();
        when(repository.existsByCodigoUasgAndNumeroPregao("123456", "45/2025"))
            .thenReturn(true); // Simula que já existe

        service.importarLicitacoes();

        // Verifica que o save NÃO foi chamado
        verify(repository, never()).save(any());
    }
}
