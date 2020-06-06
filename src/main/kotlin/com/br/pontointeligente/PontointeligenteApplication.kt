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
class PontointeligenteApplication(val empresaRepository: EmpresaRepository,
								  val funcionarioRepository: FuncionarioRepository,
								  val lancamentoRepository: LancamentoRepository): CommandLineRunner {

	override fun run(vararg args: String?) {
		empresaRepository.deleteAll()
		funcionarioRepository.deleteAll()
		lancamentoRepository.delete()


		val empresa: Empresa = Empresa("Empresa", "10443887000146")
		empresaRepository.save(empresa)
		val empresaId: String? = empresaRepository.findByCnpj("10443887000146")?.id

		val admin: Funcionario = Funcionario("Admin", "admin@empresa.com", SenhaUtils().gerarBcrypt("123456"), "25708317000", PerfilEnum.ROLE_ADMIN, empresaId!!)
		funcionarioRepository.save(admin)
		val adminId: String? = funcionarioRepository.findByCpf("25708317000")?.id

		val funcionario: Funcionario = Funcionario("Funcionario", "funcionario@empresa.com", SenhaUtils().gerarBcrypt("123456"), "44325441557", PerfilEnum.ROLE_USUARIO, empresaId!!)
		funcionarioRepository.save(funcionario)
		val funcionarioId: String? = funcionarioRepository.findByCpf("44325441557")?.id

		System.out.println("Empresa ID: "+ empresaId)
		System.out.println("Admin ID: "+ adminId)
		System.out.println("Funcionario ID: "+ funcionarioId)

	}

}

fun main(args: Array<String>) {
	runApplication<PontointeligenteApplication>(*args)
}
