package com.br.pontointeligente.controllers

import com.br.pontointeligente.documents.Empresa
import com.br.pontointeligente.documents.Funcionario
import com.br.pontointeligente.dtos.CadastroPFDTO
import com.br.pontointeligente.dtos.CadastroPJDTO
import com.br.pontointeligente.enums.PerfilEnum
import com.br.pontointeligente.response.Response
import com.br.pontointeligente.services.EmpresaService
import com.br.pontointeligente.services.FuncionarioService
import com.br.pontointeligente.utils.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/cadastrar-pf")
class CadastroPFController (val funcionarioService: FuncionarioService, val empresaService: EmpresaService){

    @PostMapping
    fun cadastrarPF(@Valid @RequestBody cadastroPFDTO: CadastroPFDTO, result: BindingResult):
            ResponseEntity<Response<CadastroPFDTO>> {

        val response: Response<CadastroPFDTO> = Response<CadastroPFDTO>()

        val empresa: Empresa? = empresaService.buscarEmpresaPorCnpj(cadastroPFDTO.cnpj)
        validarDadosExistentes(cadastroPFDTO, empresa!!, result)

        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.erros.add(it)}}
            return ResponseEntity.badRequest().body(response)
        }

        val funcionario: Funcionario = converterDTOParaFuncionario(cadastroPFDTO, empresa!!)

        funcionarioService.adicionarFuncionario(funcionario)
        response.data = converterCadastroPFDTO(funcionario, empresa!!)

        return ResponseEntity.ok(response)


    }


    private fun validarDadosExistentes(cadastroPFDTO: CadastroPFDTO, empresa: Empresa, result: BindingResult) {
        if (empresa == null) {
            result.addError(ObjectError("empresa", "Empresa não cadastrada."))
        }

        val funcionarioCPF: Funcionario? = funcionarioService.buscarFuncionarioPorCpf(cadastroPFDTO.cpf)
        if (funcionarioCPF != null) {
            result.addError(ObjectError("funcionario", "CPF já existente."))
        }

        val funcionarioEmail: Funcionario? = funcionarioService.buscarFuncionarioPorEmail(cadastroPFDTO.email)
        if (funcionarioEmail != null) {
            result.addError(ObjectError("funcionario", "Email já existente."))
        }
    }

    private fun converterDTOParaFuncionario(cadastroPFDTO: CadastroPFDTO, empresa: Empresa): Funcionario =
        Funcionario(cadastroPFDTO.nome, cadastroPFDTO.email, SenhaUtils().gerarBcrypt(cadastroPFDTO.senha),
                cadastroPFDTO.cpf, PerfilEnum.ROLE_USUARIO, empresa.id.toString(), cadastroPFDTO.valorHora?.toDouble(),
                cadastroPFDTO.qtdHorasTrabalhoDia?.toFloat(), cadastroPFDTO.qtdHorasAlmoco?.toFloat(), cadastroPFDTO.id)


    private fun converterCadastroPFDTO(funcionario: Funcionario, empresa: Empresa): CadastroPFDTO? =
            CadastroPFDTO(funcionario.nome, funcionario.email, "", funcionario.cpf, empresa.cnpj,
                    empresa.id.toString(), funcionario.valorHora.toString(), funcionario.qtdHorasTrabalhoDia.toString(),
                    funcionario.qtdHorasTrabalhoDia.toString(), funcionario.id)


}