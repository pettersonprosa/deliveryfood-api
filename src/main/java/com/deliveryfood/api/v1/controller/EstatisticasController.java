package com.deliveryfood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryfood.api.v1.DeliveryLinks;
import com.deliveryfood.domain.filter.VendaDiaria;
import com.deliveryfood.domain.filter.VendaDiariaFilter;
import com.deliveryfood.domain.service.VendaQueryService;
import com.deliveryfood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @Autowired
    private DeliveryLinks deliveryLinks;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EstatisticasModel estatisticas() {
        var estatisticasModel = new EstatisticasModel();

        estatisticasModel.add(deliveryLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));

        return estatisticasModel;
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendaDiarias(filtro, timeOffset);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasPdf(VendaDiariaFilter filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
        // attachment -> conteudo que estamos retornando na resposta da requisitcao é para ser baixado pelo cliente

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
    }

}
