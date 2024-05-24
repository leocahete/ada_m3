package br.gov.caixa.banco.service;

import br.gov.caixa.banco.dto.ClientePJDTO;
import br.gov.caixa.banco.enums.StatusEnum;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.ClientePJ;
import br.gov.caixa.banco.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientePJService {

    private final ClienteRepository<ClientePJ> clienteRepository;

    private final ModelMapper modelMapper;

    private ClientePJDTO convertDto(ClientePJ cliente){
        return modelMapper.map(cliente, ClientePJDTO.class);
    }

    private ClientePJ convertFromDto(ClientePJDTO ClientePJDTO){
        return modelMapper.map(ClientePJDTO, ClientePJ.class);
    }

    public List<ClientePJDTO> listarClientes(){
        return clienteRepository.findAll().stream().map(this::convertDto).collect(Collectors.toList());
    }

    public ClientePJDTO salvar(ClientePJDTO clientePJDTO) throws ValorInvalidoException {

        var cliente = convertFromDto(clientePJDTO);
        cliente.setUuid(UUID.randomUUID());
        cliente.setDataCadastro(LocalDate.now());
        cliente.setStatus(StatusEnum.ATIVO);
        return convertDto(clienteRepository.save(cliente));
    }

    public Optional<ClientePJDTO> buscarPorUuid(UUID uuid){
        return clienteRepository.findByUuid(uuid).map(this::convertDto);
    }

    public void excluir(UUID uuid){
        clienteRepository.delete(clienteRepository.findByUuid(uuid).orElseThrow());
    }

    public ClientePJDTO atualizar(ClientePJDTO clientePJDTO){
        var cliente = clienteRepository.findByUuid(clientePJDTO.getUuid()).orElseThrow();
        cliente.setNome(clientePJDTO.getNome());
        return convertDto(clienteRepository.save(cliente));
    }
}
