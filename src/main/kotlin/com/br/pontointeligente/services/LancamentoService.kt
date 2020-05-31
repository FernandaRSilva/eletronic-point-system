package com.br.pontointeligente.services

import com.br.pontointeligente.documents.Lancamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.awt.print.Pageable

interface LancamentoService {

    fun buscarLancamentosPorFuncionarioId(id: String, pageRequest: PageRequest): Page<Lancamento>

    fun buscarLancamentoPorId(id: String): Lancamento?

    fun adicionarLancamento(lancamento: Lancamento): Lancamento

    fun removerLancamento(id: String)
}