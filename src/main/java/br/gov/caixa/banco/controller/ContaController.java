package br.gov.caixa.banco.controller;

import br.gov.caixa.banco.dto.ContaDTO;
import br.gov.caixa.banco.service.ContaCorrentePFService;
import br.gov.caixa.banco.service.ContaInvestimentoPFService;
import br.gov.caixa.banco.usuario.Usuario;
import br.gov.caixa.banco.exception.NaoEncontradoException;
import br.gov.caixa.banco.exception.SaldoExistenteException;
import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.login.JwtService;
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
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaCorrentePFService service;
    private final ContaInvestimentoPFService serviceInvest;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    @GetMapping("/{uuid}")
    public List<ContaDTO> listarContasUsuario(@PathVariable("uuid") UUID uuid){
        return service.listarContas(uuid).stream().collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).FUNCIONARIO.name())")
    public ResponseEntity<ContaDTO> inserir(@Valid @RequestBody ContaDTO contaDTO){
        try {
            return new ResponseEntity<>(service.salvar(contaDTO), HttpStatus.CREATED);
        }catch (NaoEncontradoException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).FUNCIONARIO.name())")
    public ResponseEntity excluir(@PathVariable("uuid") UUID uuid){
        try {
            service.excluir(uuid);
            return ResponseEntity.noContent().build();
        } catch (SaldoExistenteException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/saldo/{uuid}")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).FUNCIONARIO.name(),T(Role).CLIENTE.name())")
    public ResponseEntity consultarSaldo(@PathVariable("uuid") UUID uuid){
        return new ResponseEntity<>(service.consutarSaldo(uuid), HttpStatus.OK);
    }

    @PostMapping("/sacar")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).CLIENTE.name())")
    public ResponseEntity<ContaDTO> sacar(@Valid @RequestBody ContaDTO contaDTO, @RequestHeader (name="Authorization") String bearerToken){
        try {
            service.validarUsuario(obterCpfToken(bearerToken),contaDTO);
            return new ResponseEntity<>(service.sacar(contaDTO), HttpStatus.OK);
        } catch (SaldoInsuficienteException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (ValorInvalidoException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/depositar")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).CLIENTE.name())")
    public ResponseEntity<ContaDTO> depositar(@Valid @RequestBody ContaDTO contaDTO){
        try {
            return new ResponseEntity<>(service.deposito(contaDTO), HttpStatus.OK);
        } catch (ValorInvalidoException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transferir")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).CLIENTE.name())")
    public ResponseEntity<ContaDTO> transferir(@Valid @RequestBody ContaDTO contaDTO, @RequestHeader (name="Authorization") String bearerToken){
        try {
            service.validarUsuario(obterCpfToken(bearerToken),contaDTO);
            return new ResponseEntity<>(service.transferir(contaDTO), HttpStatus.OK);
        } catch (ValorInvalidoException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (SaldoInsuficienteException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    private String obterCpfToken(String bearerToken){
        String token = bearerToken.substring(7);
        Usuario u = (Usuario) jwtService.getUserDetails(token);
        return u.getCpf();
    }

    //Novo endpoint investir
    @PostMapping("/investir")
    @PreAuthorize("hasAnyRole(T(Role).ADMIN.name(),T(Role).CLIENTE.name())")
    public ResponseEntity<ContaDTO> investir(@Valid @RequestBody ContaDTO contaDTO, @RequestHeader (name="Authorization") String bearerToken){
        try {
            return new ResponseEntity<>(serviceInvest.investir(contaDTO), HttpStatus.OK);
        } catch (ValorInvalidoException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
