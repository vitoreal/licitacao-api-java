package com.gestao.licitacao.service;

import com.gestao.licitacao.model.Licitacao;
import com.gestao.licitacao.repository.LicitacaoRepository;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ImportarLicitacaoService {

    private final LicitacaoRepository repository;

    public ImportarLicitacaoService(LicitacaoRepository repository) {
        this.repository = repository;
    }
    
    public void importarLicitacoes() throws Exception {
        String html = obterPaginaLicitacoes();
        Document doc = Jsoup.parse(html);

        Elements tds = doc.select("table td");

        for (Element td : tds) {
            String innerHtml = td.html();

            if (!innerHtml.contains("Código da UASG:")) continue;

            try {
                Licitacao licitacao = parseBlocoHtml(innerHtml);

                if (licitacao == null || licitacao.getCodigoUasg() == null) continue;

                boolean existe = repository.existsByCodigoUasgAndNumeroPregao(
                    licitacao.getCodigoUasg(), licitacao.getNumeroPregao()
                );

                if (!existe) {
                    repository.save(licitacao);
                }

            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar bloco: " + e.getMessage(), e);
            }
        }
    }

    
    private String obterPaginaLicitacoes() throws IOException, ParseException {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://comprasnet.gov.br/ConsultaLicitacoes/ConsLicitacaoDia.asp");

        try (CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request)) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.ISO_8859_1);
        }
    }
    
    private Licitacao parseBlocoHtml(String html) {
       
        html = html.replaceAll("<br>", "\n").replace("&nbsp;", " ");
        html = Jsoup.parse(html).text();
        System.out.println(html);
        Pattern uasgPattern = Pattern.compile("Código da UASG:\\s*(\\d+)");
        Pattern pregaoPattern = Pattern.compile("Pregão Eletrônico Nº\\s*([0-9/]+)");
        Pattern objetoPattern = Pattern.compile("Objeto:\\s*Pregão Eletrônico\\s*-\\s*(.+?)(\\r?\\n|$)");
        Pattern entregaPattern = Pattern.compile("Entrega da Proposta:\\s*([0-9/]+)\\s+às\\s+([0-9]{2}:[0-9]{2})Hs");

        Matcher mUasg = uasgPattern.matcher(html);
        Matcher mPregao = pregaoPattern.matcher(html);
        Matcher mObjeto = objetoPattern.matcher(html);
        Matcher mEntrega = entregaPattern.matcher(html);

        String codigoUasg = mUasg.find() ? mUasg.group(1) : null;
        String numeroPregao = mPregao.find() ? mPregao.group(1) : null;
        String objeto = mObjeto.find() ? mObjeto.group(1) : null;

        LocalDateTime dataProposta = null;
        if (mEntrega.find()) {
            String data = mEntrega.group(1); // 02/06/2025
            String hora = mEntrega.group(2); // 09:00
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            dataProposta = LocalDateTime.parse(data + " " + hora, formatter);
        }

        if (codigoUasg == null && numeroPregao == null) return null;

        Licitacao licitacao = new Licitacao();
        licitacao.setCodigoUasg(codigoUasg);
        licitacao.setNumeroPregao(numeroPregao);
        licitacao.setObjeto(objeto);
        licitacao.setDataProposta(dataProposta);
        licitacao.setVisualizada(false);

        return licitacao;
    }


}
