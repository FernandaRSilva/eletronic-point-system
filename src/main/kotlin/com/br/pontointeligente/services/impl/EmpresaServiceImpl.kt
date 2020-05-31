package com.br.pontointeligente.services.impl

import com.br.pontointeligente.documents.Empresa
import com.br.pontointeligente.repositories.EmpresaRepository
import com.br.pontointeligente.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository): EmpresaService{

    override fun buscarEmpresaPorCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj)

    override fun adicionarEmpresa(empresa: Empresa): Empresa = empresaRepository.save(empresa)

}