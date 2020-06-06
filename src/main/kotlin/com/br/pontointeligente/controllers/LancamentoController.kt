package com.br.pontointeligente.controllers

import com.br.pontointeligente.documents.Funcionario
import com.br.pontointeligente.documents.Lancamento
import com.br.pontointeligente.dtos.LancamentoDTO
import com.br.pontointeligente.enums.TipoEnum
import com.br.pontointeligente.response.Response
import com.br.pontointeligente.services.FuncionarioService
import com.br.pontointeligente.services.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import javax.validation.Valid

@RestController
@RequestMapping("/lancamentos")
class LancamentoController (val lancamentoService: LancamentoService, val funcionarioService: FuncionarioService) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Value("\${paginacao.qtd_por_pagina}")
    val qtdPorPagina: Int = 15

    @PostMapping
    fun adicionarLancamento(@Valid @RequestBody lancamentoDTO: LancamentoDTO, result: BindingResult): ResponseEntity<Response<LancamentoDTO>> {
        val response: Response<LancamentoDTO> = Response<LancamentoDTO>()
        validarFuncionario(lancamentoDTO, result)

        if (result.hasErrors()) {
            //for (erro in result.allErrors) response.erros.add(erro.defaultMessage!!)
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.erros.add(it) } }
            return ResponseEntity.badRequest().body(response)
        }

        val lancamento: Lancamento = converterDTOParaLancamento(lancamentoDTO, result)
        lancamentoService.adicionarLancamento(lancamento)
        response.data = converterLancamentoDTO(lancamento)
        return ResponseEntity.ok(response)
    }

    private fun validarFuncionario(lancamentoDTO: LancamentoDTO, result: BindingResult) {
        if (lancamentoDTO.funcionarioId == null) {
            result.addError(ObjectError("funcionario", "Funcionário não informado."))
            return
        }
        val funcionario: Funcionario? = funcionarioService.buscarFuncionarioPorId(lancamentoDTO.funcionarioId)
        if (funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não encontrado. ID inexistente."))
        }
    }

    private fun converterDTOParaLancamento(lancamentoDTO: LancamentoDTO, result: BindingResult): Lancamento {
        if (lancamentoDTO.id != null) {
            val lanc: Lancamento? = lancamentoService.buscarLancamentoPorId(lancamentoDTO.id!!)
            if (lanc == null) result.addError(ObjectError("lancamento", "Lancamento não encontrado."))
        }

        return Lancamento(dateFormat.parse(lancamentoDTO.data), TipoEnum.valueOf(lancamentoDTO.tipo!!)
                , lancamentoDTO.funcionarioId!!, lancamentoDTO.descricao, lancamentoDTO.localizacao, lancamentoDTO.id)
    }

    private fun converterLancamentoDTO(lancamento: Lancamento): LancamentoDTO =
            LancamentoDTO(dateFormat.format(lancamento.data), lancamento.tipo.toString(), lancamento.descricao,
                    lancamento.localizacao, lancamento.funcionarioId, lancamento.id)

}