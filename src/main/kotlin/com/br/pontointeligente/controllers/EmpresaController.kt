package com.br.pontointeligente.controllers

import com.br.pontointeligente.documents.Empresa
import com.br.pontointeligente.dtos.EmpresaDTO
import com.br.pontointeligente.response.Response
import com.br.pontointeligente.services.EmpresaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/empresas")
class EmpresaController(val empresaService: EmpresaService) {

    @GetMapping("/cnpj/{cnpj}")
    fun buscarEmpresaPorCNPJ(@PathVariable("cnpj") cnpj: String): ResponseEntity<Response<EmpresaDTO>> {

        val response: Response<EmpresaDTO> = Response<EmpresaDTO>()
        val empresa: Empresa? = empresaService.buscarEmpresaPorCnpj(cnpj)

        if (empresa == null) {
            response.erros.add("Empresa não encontrada para o CNPJ $cnpj")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = converterEmpresaDTO(empresa)
        return ResponseEntity.ok(response)
    }

    private fun converterEmpresaDTO(empresa: Empresa): EmpresaDTO =
            EmpresaDTO(empresa.razaoSocial, empresa.cnpj, empresa.id)

}