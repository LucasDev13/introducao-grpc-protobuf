package br.com.project

import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {
    val server = ServerBuilder
        .forPort(50051)
        //cria o endpoint
        .addService(FuncionarioEndpoint())
        .build()

    //inicia o server
    server.start()
    //segura a thread atual para ficar em execução e o servidor rodando.
    server.awaitTermination()
}

class FuncionarioEndpoint: FuncionarioServiceGrpc.FuncionarioServiceImplBase(){
    override fun cadastrar(request: FuncionarioRequest?, responseObserver: StreamObserver<FuncionarioResponse>?) {
        println(request!!)

        var nome: String? = request.nome
        if (!request.hasField(FuncionarioRequest.getDescriptor().findFieldByName("nome"))){
            nome = "[???]"
        }


        val instant =  LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = com.google.protobuf.Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)

        val response = FuncionarioResponse.newBuilder()
            .setNome(nome)
            .setCriadoEm(criadoEm)
            .build()

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }
}