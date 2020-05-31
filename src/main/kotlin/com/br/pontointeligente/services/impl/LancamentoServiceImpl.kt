package com.br.pontointeligente.services.impl

import com.br.pontointeligente.documents.Lancamento
import com.br.pontointeligente.repositories.LancamentoRepository
import com.br.pontointeligente.services.LancamentoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.awt.print.Pageable

@Service
class LancamentoServiceImpl(val lancamentoRepository: LancamentoRepository): LancamentoService {

    override fun buscarLancamentosPorFuncionarioId(id: String, pageRequest: PageRequest): Page<Lancamento> =
            lancamentoRepository.findByFuncionarioId(id, pageRequest)

    override fun buscarLancamentoPorId(id: String): Lancamento? = lancamentoRepository.findByIdOrNull(id)

    override fun adicionarLancamento(lancamento: Lancamento): Lancamento = lancamentoRepository.save(lancamento)

    override fun removerLancamento(id: String) = lancamentoRepository.deleteById(id)
}