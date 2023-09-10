package com.estudosdev.YoutubePlaylistAPI.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class TratadorExcecao {

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    private record RetornoErro (String mensagem, List<DadosErroValidacao> errosValidacao) {}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        RetornoErro retornoErro = new RetornoErro("Não foi possivel criar um registro, " +
                "os dados enviados a API não passaram nas validações.",
                (erros.stream().map(DadosErroValidacao::new).toList()));
        return ResponseEntity.badRequest().body(retornoErro);
    }
}

