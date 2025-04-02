package com.deliveryfood.infrastructure.repository.service.report;

import org.springframework.stereotype.Service;

import com.deliveryfood.domain.filter.VendaDiariaFilter;
import com.deliveryfood.domain.service.VendaReportService;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        return null;
    }

}
