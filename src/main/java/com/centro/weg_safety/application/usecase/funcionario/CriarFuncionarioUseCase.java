package com.centro.weg_safety.application.usecase.funcionario;

import com.centro.weg_safety.application.dto.request.funcionario.CriarFuncionarioRequest;
import com.centro.weg_safety.application.dto.response.funcionario.CriarFuncionarioResponse;
import com.centro.weg_safety.domain.exception.ConflitoException;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.Cargo;
import com.centro.weg_safety.domain.model.Cracha;
import com.centro.weg_safety.domain.model.Funcionario;
import com.centro.weg_safety.domain.model.Setor;
import com.centro.weg_safety.domain.model.enums.cracha.StatusCracha;
import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import com.centro.weg_safety.infra.persistence.jpa.CargoRepository;
import com.centro.weg_safety.infra.persistence.jpa.CrachaRepository;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import com.centro.weg_safety.infra.persistence.jpa.SetorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CriarFuncionarioUseCase {

    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;
    private final SetorRepository setorRepository;
    private final CrachaRepository crachaRepository;

    public CriarFuncionarioUseCase(FuncionarioRepository funcionarioRepository,
                                   CargoRepository cargoRepository,
                                   SetorRepository setorRepository,
                                   CrachaRepository crachaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.setorRepository = setorRepository;
        this.crachaRepository = crachaRepository;
    }

    @Transactional
    public CriarFuncionarioResponse execute(CriarFuncionarioRequest request) {
        if (funcionarioRepository.findByCpf(request.cpf()).isPresent()) {
            throw new ConflitoException("CPF já cadastrado");
        }
        if (request.email() != null && funcionarioRepository.findByEmail(request.email()).isPresent()) {
            throw new ConflitoException("Email já cadastrado");
        }
        if (crachaRepository.findByUidRfid(request.cracha().uidRfid()).isPresent()) {
            throw new ConflitoException("UID RFID já cadastrado");
        }

        Cargo cargo = cargoRepository.findById(request.cargoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cargo não encontrado"));
        Setor setor = setorRepository.findById(request.setorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado"));

        String matricula = gerarMatricula();

        Funcionario funcionario = Funcionario.builder()
                .nome(request.nome())
                .cpf(request.cpf())
                .rg(request.rg())
                .matricula(matricula)
                .email(request.email())
                .dataNascimento(request.dataNascimento())
                .genero(request.genero())
                .cargo(cargo)
                .setor(setor)
                .dataAdmissao(request.dataAdmissao())
                .turno(request.turno())
                .status(StatusFuncionario.ATIVO)
                .dataCriacao(LocalDateTime.now())
                .build();

        funcionario = funcionarioRepository.save(funcionario);

        Cracha cracha = Cracha.builder()
                .funcionario(funcionario)
                .uidRfid(request.cracha().uidRfid())
                .validade(request.cracha().validade())
                .perfilAcesso(request.cracha().perfilAcesso())
                .status(StatusCracha.ATIVO)
                .dataEmissao(LocalDateTime.now())
                .build();
        crachaRepository.save(cracha);

        return new CriarFuncionarioResponse(funcionario.getId(), matricula, "Funcionário cadastrado com sucesso");
    }

    private String gerarMatricula() {
        return "MAT" + System.currentTimeMillis();
    }
}
