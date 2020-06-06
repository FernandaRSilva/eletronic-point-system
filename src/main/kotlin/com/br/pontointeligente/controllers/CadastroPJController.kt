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
@RequestMapping("/cadastrar-pj")
class CadastroPJController (val empresaService: EmpresaService, val funcionarioService: FuncionarioService){

    @PostMapping
    fun cadastrarPJ(@Valid @RequestBody cadastroPJDTO: CadastroPJDTO, result: BindingResult):
            ResponseEntity<Response<CadastroPJDTO>> {
        val response: Response<CadastroPJDTO> = Response<CadastroPJDTO>()

        validarDadosExistentes(cadastroPJDTO, result)
        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.erros.add(it)}}
            return ResponseEntity.badRequest().body(response)
        }

        val empresa: Empresa = converterDTOParaEmpresa(cadastroPJDTO)
        empresaService.adicionarEmpresa(empresa)

        val empresaAdicionada: Empresa? = empresaService.buscarEmpresaPorCnpj(cadastroPJDTO.cnpj)
        var funcionario: Funcionario = converterDTOParaFuncionario(cadastroPJDTO, empresaAdicionada!!)
        funcionarioService.adicionarFuncionario(funcionario)
        response.data = converterCadastroPJDTO(funcionario, empresa)
        return ResponseEntity.ok(response)


    }

    private fun validarDadosExistentes(cadastroPJDTO: CadastroPJDTO, result: BindingResult) {
        val empresa: Empresa? = empresaService.buscarEmpresaPorCnpj(cadastroPJDTO.cnpj)
        if (empresa != null) {
            result.addError(ObjectError("empresa", "Empresa já existente."))
        }

        val funcionarioCPF: Funcionario? = funcionarioService.buscarFuncionarioPorCpf(cadastroPJDTO.cpf)
        if (funcionarioCPF != null) {
            result.addError(ObjectError("funcionario", "CPF já existente."))
        }

        val funcionarioEmail: Funcionario? = funcionarioService.buscarFuncionarioPorEmail(cadastroPJDTO.email)
        if (funcionarioEmail != null) {
            result.addError(ObjectError("funcionario", "Email já existente."))
        }
    }

    private fun converterDTOParaEmpresa(cadastroPJDTO: CadastroPJDTO): Empresa =
            Empresa(cadastroPJDTO.razaoSocial, cadastroPJDTO.cnpj)

    private fun converterDTOParaFuncionario(cadastroPJDTO: CadastroPJDTO, empresa: Empresa): Funcionario =
            Funcionario(cadastroPJDTO.nome, cadastroPJDTO.email, SenhaUtils().gerarBcrypt(cadastroPJDTO.senha),
                    cadastroPJDTO.cpf, PerfilEnum.ROLE_ADMIN, empresa.id.toString())

    private fun converterCadastroPJDTO(funcionario: Funcionario, empresa: Empresa): CadastroPJDTO =
            CadastroPJDTO(funcionario.nome, funcionario.email, "", funcionario.cpf, empresa.cnpj,
                    empresa.razaoSocial, funcionario.id)
}