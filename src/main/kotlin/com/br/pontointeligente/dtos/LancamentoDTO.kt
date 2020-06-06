package com.br.pontointeligente.dtos

import javax.validation.constraints.NotEmpty

data class LancamentoDTO (

        @NotEmpty(message = "Data não pode ser vazia.")
        val data: String? = null,

        @NotEmpty(message = "Tipo não pode ser vazio.")
        val tipo:String? = null,

        val descricao: String? = null,
        val localizacao: String? = null,
        val funcionarioId: String? = null,
        var id: String? = null

)