package com.br.pontointeligente.services

import com.br.pontointeligente.documents.Funcionario
import com.br.pontointeligente.enums.PerfilEnum
import com.br.pontointeligente.repositories.FuncionarioRepository
import com.br.pontointeligente.utils.SenhaUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@SpringBootTest
class FuncionarioServiceTest {

    @MockBean
    private val funcionarioRepository: FuncionarioRepository? = null

    @Autowired
    private val funcionarioService: FuncionarioService? = null

    private val email: String = "fernanda@email.com.br"
    private val cpf: String = "44499627311"
    private val id: String = "1"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(funcionarioRepository?.findByCpf(cpf)).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findByEmail(email)).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.save(Mockito.any(Funcionario::class.java))).willReturn(funcionario())
        //BDDMockito.given(funcionarioRepository?.findByIdOrNull(id)).willReturn(funcionario())

    }

    @Test
    fun testBuscarFuncionarioPorCpf() {
        val funcionario: Funcionario? = funcionarioService?.buscarFuncionarioPorCpf(cpf)
        Assertions.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorEmail() {
        val funcionario: Funcionario? = funcionarioService?.buscarFuncionarioPorEmail(email)
        Assertions.assertNotNull(funcionario)
    }

    /*
    @Test
    fun testBuscarFuncionarioPorId() {
        val funcionario: Funcionario? = funcionarioService?.buscarFuncionarioPorId(id)
        Assertions.assertNotNull(funcionario)
    }
    */

    @Test
    fun testAdicionarFuncionario() {
        val funcionario: Funcionario? = funcionarioService?.adicionarFuncionario(funcionario())
        Assertions.assertNotNull(funcionario)
    }

    private fun funcionario(): Funcionario = Funcionario("Fernanda", email,
            SenhaUtils().gerarBcrypt("123456"), cpf, PerfilEnum.ROLE_USUARIO, id)



}