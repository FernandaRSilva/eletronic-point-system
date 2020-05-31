package com.br.pontointeligente.services

import com.br.pontointeligente.documents.Funcionario

interface FuncionarioService {

    fun adicionarFuncionario(funcionario: Funcionario): Funcionario

    fun buscarFuncionarioPorCpf(cpf: String): Funcionario?

    fun buscarFuncionarioPorEmail(email: String): Funcionario?

    fun buscarFuncionarioPorId(id: String): Funcionario?


}