package br.gov.caixa.banco.controller;

import br.gov.caixa.banco.dto.ClientePJDTO;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.service.ClientePJService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientesPJ")
@RequiredArgsConstructor
public class ClientePJController {

    private final ClientePJService service;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).FUNCIONARIO.name())")
    public List<ClientePJDTO> listarTodos(){
        return service.listarClientes().stream().collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).FUNCIONARIO.name())")
    public ResponseEntity<ClientePJDTO> buscarPorUuid(@PathVariable("uuid")UUID uuid){
        var cliente = service.buscarPorUuid(uuid);
        return cliente.map(ClientePJDTO -> ResponseEntity.ok(modelMapper.map(ClientePJDTO, ClientePJDTO.class))).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).FUNCIONARIO.name())")
    public ResponseEntity excluir(@PathVariable("uuid") UUID uuid){
        service.excluir(uuid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).FUNCIONARIO.name())")
    public ResponseEntity<ClientePJDTO> atualizar(@PathVariable("uuid") UUID uuid, @RequestBody ClientePJDTO clientePJDTO){
        clientePJDTO.setUuid(uuid);
        return new ResponseEntity(service.atualizar(clientePJDTO), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).FUNCIONARIO.name())")
    public ResponseEntity<ClientePJDTO> inserir(@Valid @RequestBody ClientePJDTO clientePJDTO){
        try {
            return new ResponseEntity(service.salvar(clientePJDTO), HttpStatus.OK);
        } catch (ValorInvalidoException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
