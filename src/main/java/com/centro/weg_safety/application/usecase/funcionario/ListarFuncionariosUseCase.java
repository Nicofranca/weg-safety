package com.centro.weg_safety.application.usecase.funcionario;

import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResumoResponse;
import com.centro.weg_safety.application.mapper.FuncionarioMapper;
import com.centro.weg_safety.domain.model.Funcionario;
import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarFuncionariosUseCase {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    public ListarFuncionariosUseCase(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    public List<FuncionarioResumoResponse> execute(StatusFuncionario status, UUID setorId, UUID cargoId) {
        List<Funcionario> funcionarios;

        if (status != null) {
            funcionarios = funcionarioRepository.findByStatus(status);
        } else if (setorId != null) {
            funcionarios = funcionarioRepository.findBySetorId(setorId);
        } else if (cargoId != null) {
            funcionarios = funcionarioRepository.findByCargoId(cargoId);
        } else {
            funcionarios = funcionarioRepository.findAll();
        }

        return funcionarios.stream().map(funcionarioMapper::toResumoResponse).toList();
    }
}
