package br.com.alura.TabelaFipe.model;

public record Dados(String codigo, String nome) {
    @Override
    public String toString() {
        return "Código = " + codigo +
                ", Nome = " + nome;
    }
}
