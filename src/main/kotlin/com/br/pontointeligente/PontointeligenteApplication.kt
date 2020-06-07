package com.br.pontointeligente

import com.br.pontointeligente.documents.Empresa
import com.br.pontointeligente.documents.Funcionario
import com.br.pontointeligente.enums.PerfilEnum
import com.br.pontointeligente.repositories.EmpresaRepository
import com.br.pontointeligente.repositories.FuncionarioRepository
import com.br.pontointeligente.repositories.LancamentoRepository
import com.br.pontointeligente.utils.SenhaUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
class PontointeligenteApplication

fun main(args: Array<String>) {
	runApplication<PontointeligenteApplication>(*args)
}
