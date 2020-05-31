package com.br.pontointeligente.services

import com.br.pontointeligente.documents.Empresa
import com.br.pontointeligente.repositories.EmpresaRepository
import org.springframework.beans.factory.annotation.Autowired

interface EmpresaService {

    fun buscarEmpresaPorCnpj(cnpj: String): Empresa?

    fun adicionarEmpresa(empresa: Empresa): Empresa
}