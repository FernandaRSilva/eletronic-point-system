package com.br.pontointeligente.repositories

import com.br.pontointeligente.documents.Empresa
import org.springframework.data.mongodb.repository.MongoRepository

interface EmpresaRepository: MongoRepository<Empresa, String>{

    fun findByCnpj(cnpj: String): Empresa?

}