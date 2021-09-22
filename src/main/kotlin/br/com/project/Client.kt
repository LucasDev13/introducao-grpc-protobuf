package br.com.project

import io.grpc.ManagedChannelBuilder

fun main() {
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val request = FuncionarioRequest.newBuilder()
        .setNome("Lucas Pontes")
        .setCpf("000.000.000-00")
        .setSalario(2500.20)
        .setIdade(29)
        .setAtivo(true)
        .setCargo(Cargo.QA)
        .addEndereco(FuncionarioRequest.Endereco.newBuilder()
            .setLogradouro("Rua das andorinhas")
            .setCep("00000-000")
            .setComplemento("Casa 20")
            .build())
        .build()

    val client = FuncionarioServiceGrpc.newBlockingStub(channel)
    val response = client.cadastrar(request)

    println(response)
}