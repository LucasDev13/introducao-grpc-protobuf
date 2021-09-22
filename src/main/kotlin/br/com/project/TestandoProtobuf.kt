package br.com.project

import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    val request = FuncionarioRequest.newBuilder()
        .setNome("Lucas Pontes")
        .setCpf("000.000.000-00")
        .setSalario(2500.20)
        .setAtivo(true)
        .setCargo(Cargo.QA)
        .addEndereco(FuncionarioRequest.Endereco.newBuilder()
                    .setLogradouro("Rua das andorinhas")
                    .setCep("00000-000")
                    .setComplemento("Casa 20")
                    .build())
        .build()
    println(request)

    //Serializando o objeto e salvando em disco
    request.writeTo(FileOutputStream("funcionario-request.bin"))

    //Deserializando objeto, carregar essa informação que está em disco, vou altera-la e vou imprimir o resultado
    val request2 = FuncionarioRequest.newBuilder()
        .mergeFrom(FileInputStream("funcionario-request.bin"))

    request2.setCargo(Cargo.GERENTE)
        .build()

    println(request2)

}