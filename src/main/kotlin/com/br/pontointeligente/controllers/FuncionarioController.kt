package com.br.pontointeligente.controllers

import com.br.pontointeligente.documents.Funcionario
import com.br.pontointeligente.dtos.FuncionarioDTO
import com.br.pontointeligente.response.Response
import com.br.pontointeligente.services.FuncionarioService
import com.br.pontointeligente.utils.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/funcionarios")
class FuncionarioController (val funcionarioService: FuncionarioService){

    @PutMapping("/{funcionarioId}")
    fun atualizarFuncionario(@PathVariable("funcionarioId") funcionarioId: String,
                             @RequestBody funcionarioDTO: FuncionarioDTO, result: BindingResult):
            ResponseEntity<Response<FuncionarioDTO>> {

        val response: Response<FuncionarioDTO> = Response<FuncionarioDTO>()
        val funcionario: Funcionario? = funcionarioService.buscarFuncionarioPorId(funcionarioId)

        if (funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não encontrado."))
        }

        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.erros.add(it)}}
            return ResponseEntity.badRequest().body(response)
        }

        val funcAtualizar: Funcionario = atualizarDadosFuncionario(funcionario!!, funcionarioDTO)
        funcionarioService.adicionarFuncionario(funcAtualizar)
        response.data = converterFuncionarioDTO(funcAtualizar)

        return ResponseEntity.ok(response)

    }

    private fun atualizarDadosFuncionario(funcionario: Funcionario, funcionarioDTO: FuncionarioDTO): Funcionario {
        var senha: String
        if (funcionarioDTO.senha == null) {
            senha = funcionario.senha
        } else {
            senha = SenhaUtils().gerarBcrypt(funcionarioDTO.senha)
        }
        return Funcionario(funcionarioDTO.nome, funcionario.email, senha,
                funcionario.cpf, funcionario.perfil, funcionario.empresaId,
                funcionarioDTO.valorHora?.toDouble(),
                funcionarioDTO.qtdHorasTrabalhoDia?.toFloat(),
                funcionarioDTO.qtdHorasAlmoco?.toFloat(),
                funcionario.id)

    }

    private fun converterFuncionarioDTO(funcionario: Funcionario): FuncionarioDTO = FuncionarioDTO(funcionario.nome,
            funcionario.email, "", funcionario.valorHora.toString(), funcionario.qtdHorasTrabalhoDia.toString(),
            funcionario.qtdHorasAlmoco.toString(), funcionario.id)
}