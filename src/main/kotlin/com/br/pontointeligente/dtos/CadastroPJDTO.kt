package com.br.pontointeligente.dtos

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CadastroPJDTO (

        @NotEmpty(message = "Nome não pode ser vazio.")
        @Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
        val nome: String = "",

        @NotEmpty(message = "Email não pode ser vazio.")
        @Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
        @Email(message="Email inválido.")
        val email: String = "",

        @NotEmpty(message = "Senha não pode ser vazia.")
        val senha: String = "",

        @NotEmpty(message = "CPF não pode ser vazio.")
        @CPF(message="CPF inválido.")
        val cpf: String = "",

        @NotEmpty(message = "CNPJ não pode ser vazio.")
        @CNPJ(message = "CNPJ inválido.")
        val cnpj: String = "",

        @NotEmpty(message = "Razão social não pode ser vazio.")
        @Length(min = 5, max = 200, message = "Razão social deve conter entre 5 e 200 caracteres.")
        val razaoSocial: String = "",

        val id: String? = null


)