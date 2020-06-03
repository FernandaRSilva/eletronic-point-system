package com.br.pontointeligente.services.impl

import com.br.pontointeligente.documents.Funcionario
import com.br.pontointeligente.repositories.FuncionarioRepository
import com.br.pontointeligente.services.FuncionarioService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FuncionarioServiceImpl(val funcionarioRepository: FuncionarioRepository): FuncionarioService {
    override fun adicionarFuncionario(funcionario: Funcionario): Funcionario = funcionarioRepository.save(funcionario)

    override fun buscarFuncionarioPorCpf(cpf: String): Funcionario? = funcionarioRepository.findByCpf(cpf)

    override fun buscarFuncionarioPorEmail(email: String): Funcionario? = funcionarioRepository.findByEmail(email)

    override fun buscarFuncionarioPorId(id: String): Funcionario? = funcionarioRepository.findById(id).get()

}