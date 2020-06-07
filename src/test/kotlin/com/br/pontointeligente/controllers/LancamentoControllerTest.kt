package com.br.pontointeligente.controllers

import com.br.pontointeligente.documents.Funcionario
import com.br.pontointeligente.documents.Lancamento
import com.br.pontointeligente.dtos.LancamentoDTO
import com.br.pontointeligente.enums.PerfilEnum
import com.br.pontointeligente.enums.TipoEnum
import com.br.pontointeligente.services.FuncionarioService
import com.br.pontointeligente.services.LancamentoService
import com.br.pontointeligente.utils.SenhaUtils
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class LancamentoControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    private val urlBase: String = "/lancamentos/"
    private val idFuncionario: String = "1"
    private val idLancamento: String = "1"
    private val tipo: String = TipoEnum.INICIO_TRABALHO.name
    private val data: Date = Date()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testCadastrarLancamento() {
        val lancamento: Lancamento = lancamento()
        BDDMockito.given<Funcionario>(funcionarioService?.buscarFuncionarioPorId(idFuncionario)).willReturn(funcionario())
        BDDMockito.given(lancamentoService?.adicionarLancamento(lancamento())).willReturn(lancamento)

        //instruções da minha requisição
        mvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(jsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // a patir daqui são verificações da minha requisição
                .andExpect(jsonPath("$.data.tipo").value(tipo))
                .andExpect(jsonPath("$.data.data").value(dateFormat.format(data)))
                .andExpect(jsonPath("$.data.funcionarioId").value(idFuncionario))
                .andExpect(jsonPath("$.erros").isEmpty())

    }


    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testCadastrarLancamentoFuncionarioIdInvalido() {
        BDDMockito.given<Funcionario>(funcionarioService?.buscarFuncionarioPorId(idFuncionario)).willReturn(null)

        mvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(jsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros").value("Funcionário não encontrado. ID inexistente."))
                .andExpect(jsonPath("$.data").isEmpty())
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testBuscarLancamentoPorId() {
        BDDMockito.given<Lancamento>(lancamentoService?.buscarLancamentoPorId(idLancamento)).willReturn(lancamento())

        mvc!!.perform(MockMvcRequestBuilders.get(urlBase + idLancamento)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.erros").isEmpty())

    }

    @Test
    @Throws(Exception::class)
    @WithMockUser(username = "admin@admin.com", roles = arrayOf("ADMIN"))
    fun testRemoverLancamento() {
        BDDMockito.given<Lancamento>(lancamentoService?.buscarLancamentoPorId(idLancamento)).willReturn(lancamento())

        mvc!!.perform(MockMvcRequestBuilders.delete(urlBase + idLancamento)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
    }


    @Throws(JsonProcessingException::class)
    private fun jsonRequisicaoPost(): String {
        val lancamentoDTO: LancamentoDTO = LancamentoDTO(dateFormat.format(data), tipo, "Descrição",
                "Localização", idFuncionario)
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(lancamentoDTO)

    }

    private fun lancamento(): Lancamento = Lancamento(data, TipoEnum.valueOf(tipo), idFuncionario, "Descrição",
            "Localização", idLancamento)

    private fun funcionario(): Funcionario = Funcionario("Nome", "email@email.com",
            SenhaUtils().gerarBcrypt("123456"), "23145699876", PerfilEnum.ROLE_USUARIO, idFuncionario)
}