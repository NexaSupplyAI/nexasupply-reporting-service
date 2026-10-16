package com.nexasupplyai.reporting.service.impl;

import com.nexasupplyai.reporting.dto.request.SnapshotPanelRequest;
import com.nexasupplyai.reporting.dto.response.SnapshotPanelResponse;
import com.nexasupplyai.reporting.entity.SnapshotPanel;
import com.nexasupplyai.reporting.mapper.SnapshotPanelMapper;
import com.nexasupplyai.reporting.repository.SnapshotPanelRepository;
import com.nexasupplyai.reporting.service.interfaces.SnapshotPanelServiceInterface;
import com.nexasupplyai.reporting.exception.SnapshotPanelNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SnapshotPanelServiceImpl implements SnapshotPanelServiceInterface {

    private final SnapshotPanelRepository repositorio;
    private final SnapshotPanelMapper mapper;

    @Override
    public List<SnapshotPanelResponse> listar() {
        return repositorio.findAllByOrderByGeneradoEnDesc()
                .stream()
                .map(mapper::aResponse)
                .toList();
    }

    @Override
    public List<SnapshotPanelResponse> listarPorPeriodo(
            OffsetDateTime inicio,
            OffsetDateTime fin) {

        return repositorio
                .findAllByInicioPeriodoGreaterThanEqualAndFinPeriodoLessThanEqualOrderByGeneradoEnDesc(
                        inicio,
                        fin
                )
                .stream()
                .map(mapper::aResponse)
                .toList();
    }

    @Override
    public SnapshotPanelResponse obtenerPorId(UUID id) {
        return mapper.aResponse(buscar(id));
    }

    @Override
    @Transactional
    public SnapshotPanelResponse crear(SnapshotPanelRequest r) {

        if (r.finPeriodo().isBefore(r.inicioPeriodo())) {
            throw new IllegalArgumentException(
                    "El fin no puede ser anterior al inicio"
            );
        }

        return mapper.aResponse(
                repositorio.save(mapper.aEntidad(r))
        );
    }

    @Override
    @Transactional
    public SnapshotPanelResponse actualizar(
            UUID id,
            SnapshotPanelRequest r) {

        SnapshotPanel snapshot = buscar(id);

        mapper.actualizarEntidad(r, snapshot);

        return mapper.aResponse(
                repositorio.save(snapshot)
        );
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        repositorio.delete(buscar(id));
    }

    private SnapshotPanel buscar(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new SnapshotPanelNoEncontradoException(id));
    }
}
