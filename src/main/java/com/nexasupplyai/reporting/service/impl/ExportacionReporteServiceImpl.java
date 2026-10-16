package com.nexasupplyai.reporting.service.impl;

import com.nexasupplyai.reporting.dto.request.ExportacionReporteRequest;
import com.nexasupplyai.reporting.dto.response.ExportacionReporteResponse;
import com.nexasupplyai.reporting.entity.ExportacionReporte;
import com.nexasupplyai.reporting.mapper.ExportacionReporteMapper;
import com.nexasupplyai.reporting.repository.ExportacionReporteRepository;
import com.nexasupplyai.reporting.service.interfaces.ExportacionReporteServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExportacionReporteServiceImpl implements ExportacionReporteServiceInterface {

    private final ExportacionReporteRepository repositorio;
    private final ExportacionReporteMapper mapper;

    @Override
    public List<ExportacionReporteResponse> listar() {
        return repositorio.findAll()
                .stream()
                .map(mapper::aResponse)
                .toList();
    }

    @Override
    public ExportacionReporteResponse obtenerPorId(UUID id) {
        return mapper.aResponse(buscar(id));
    }

    @Override
    @Transactional
    public ExportacionReporteResponse crear(ExportacionReporteRequest r) {
        return mapper.aResponse(
                repositorio.save(mapper.aEntidad(r))
        );
    }

    @Override
    @Transactional
    public ExportacionReporteResponse actualizar(
            UUID id,
            ExportacionReporteRequest r) {

        ExportacionReporte exportacion = buscar(id);

        mapper.actualizarEntidad(r, exportacion);

        return mapper.aResponse(
                repositorio.save(exportacion)
        );
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        repositorio.delete(buscar(id));
    }

    private ExportacionReporte buscar(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Exportación no encontrada: " + id
                ));
    }
}