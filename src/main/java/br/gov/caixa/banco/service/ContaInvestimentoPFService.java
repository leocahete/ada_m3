package br.gov.caixa.banco.service;

import br.gov.caixa.banco.dto.ContaDTO;
import br.gov.caixa.banco.exception.NaoEncontradoException;
import br.gov.caixa.banco.exception.SaldoExistenteException;
import br.gov.caixa.banco.exception.SaldoInsuficienteException;
import br.gov.caixa.banco.exception.ValorInvalidoException;
import br.gov.caixa.banco.model.ClientePF;
import br.gov.caixa.banco.model.ContaInvestimento;
import br.gov.caixa.banco.repository.ClienteRepository;
import br.gov.caixa.banco.repository.ContaRepository;
import br.gov.caixa.banco.service.consultaSaldo.ConsultaSaldo;
import br.gov.caixa.banco.service.deposito.Deposito;
import br.gov.caixa.banco.service.investimento.InvestirPFImpl;
import br.gov.caixa.banco.service.saque.SaquePFImpl;
import br.gov.caixa.banco.service.transferencia.TransferenciaPFImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class ContaInvestimentoPFService implements ConsultaSaldo<ContaInvestimento>, Deposito<ContaInvestimento>,
            SaquePFImpl<ContaInvestimento>, TransferenciaPFImpl<ContaInvestimento>, InvestirPFImpl<ContaInvestimento> {

        private final ContaRepository contaRepository;
        private final ClienteRepository<ClientePF> clienteRepository;
        private final ModelMapper modelMapper;

        private ContaDTO convertDto(ContaInvestimento conta){
            return modelMapper.map(conta, ContaDTO.class);
        }

        private ContaInvestimento convertFromDto(ContaDTO contaDTO){
            return modelMapper.map(contaDTO, ContaInvestimento.class);
        }

        public List<ContaDTO> listarContas(UUID clienteUuid){
            var cliente = clienteRepository.findByUuid(clienteUuid);
            return contaRepository.findByCliente(cliente).stream().map(this::convertDto).collect(Collectors.toList());
        }

        public ContaDTO salvar(ContaDTO contaDTO) throws NaoEncontradoException {
            var cliente = clienteRepository.findByUuid(contaDTO.getClienteUuid()).orElseThrow();
            var conta = convertFromDto(contaDTO);
            conta.setCliente(cliente);
            conta.setUuid(UUID.randomUUID());
            conta.setDataCriacao(LocalDate.now());
            return convertDto(contaRepository.save(conta));
        }

        public Optional<ContaDTO> buscarPorUuid(UUID uuid){
            return contaRepository.findByUuid(uuid).map(this::convertDto);
        }

        public void excluir(UUID uuid) throws SaldoExistenteException {
            var conta = contaRepository.findByUuid(uuid).orElseThrow();
            if(conta.getSaldo().compareTo(BigDecimal.ZERO)==0){
                contaRepository.delete(conta);
            }else{
                throw new SaldoExistenteException();
            }
        }

        public ContaDTO sacar(ContaDTO contaDTO) throws SaldoInsuficienteException, ValorInvalidoException {
            var conta = contaRepository.findByUuid(contaDTO.getUuid()).orElseThrow();
            sacar((ClientePF) conta.getCliente(), (ContaInvestimento) conta, contaDTO.getValorOperacao());
            return convertDto(contaRepository.save(conta));
        }

        public ContaDTO consutarSaldo(UUID uuid)  {
            var conta = contaRepository.findByUuid(uuid).orElseThrow();
            return convertDto(conta);
        }

        public ContaDTO transferir(ContaDTO contaDTO) throws SaldoInsuficienteException, ValorInvalidoException {
            var conta = contaRepository.findByUuid(contaDTO.getUuid()).orElseThrow();
            var contaDestino = contaRepository.findByUuid(contaDTO.getContaDestinoUuid()).orElseThrow();
            transferir((ClientePF) conta.getCliente(), (ContaInvestimento) conta, contaDTO.getValorOperacao(), contaDestino);
            contaRepository.save(conta);
            contaRepository.save(contaDestino);
            return convertDto(conta);
        }

        public void validarUsuario(String cpf, ContaDTO contaDTO){
            var conta = contaRepository.findByUuid(contaDTO.getUuid()).orElseThrow();
            if(!conta.getCliente().getCpf().equals(cpf)) {
                throw new AccessDeniedException("Apenas o dono da conta pode realizar esta operação.");
            }
        }

        // NOVO ENDPOINT INVESTIR
        public ContaDTO investir(ContaDTO contaDTO) throws ValorInvalidoException {
            var conta = contaRepository.findByUuid(contaDTO.getUuid()).orElseThrow();
            investir((ContaInvestimento) conta, contaDTO.getValorOperacao());
            return convertDto(contaRepository.save(conta));
        }
}
