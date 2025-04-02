package com.deliveryfood.domain.service;

import java.util.List;

import com.deliveryfood.domain.filter.VendaDiaria;
import com.deliveryfood.domain.filter.VendaDiariaFilter;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendaDiarias(VendaDiariaFilter filtro, String timeOffset);

}
