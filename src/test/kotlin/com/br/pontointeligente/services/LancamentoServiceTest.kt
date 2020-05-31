package com.br.pontointeligente.services

import com.br.pontointeligente.documents.Lancamento
import com.br.pontointeligente.enums.TipoEnum
import com.br.pontointeligente.repositories.LancamentoRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@SpringBootTest
class LancamentoServiceTest {

    @Autowired
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    private val id: String = "1"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        //BDDMockito.given(lancamentoRepository?.findByIdOrNull(id)).willReturn(lancamento())
        BDDMockito.given<Page<Lancamento>>(lancamentoRepository?.findByFuncionarioId(id, PageRequest.of(0, 10)))
                .willReturn(PageImpl(ArrayList<Lancamento>()))
        BDDMockito.given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java))).willReturn(lancamento())
    }

    @Test
    fun testBuscarLancamentosPorFuncionarioId() {
        val lancamento:Page<Lancamento>? = lancamentoService?.buscarLancamentosPorFuncionarioId(id, PageRequest.of(0,10))
        Assertions.assertNotNull(lancamento)

    }

    /*
    @Test
    fun testBuscarLancamentoPorId() {
        val lancamento:Lancamento? = lancamentoService?.buscarLancamentoPorId(id)
        Assertions.assertNotNull(lancamento)
    }
    */

    @Test
    fun testAdicionarLancamento() {
        val lancamento:Lancamento? = lancamentoService?.adicionarLancamento(lancamento())
        Assertions.assertNotNull(lancamento)
    }

    private fun lancamento(): Lancamento = Lancamento(Date(), TipoEnum.INICIO_ALMOCO, id)


}