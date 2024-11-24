package br.com.cesarschool.poo.titulos.utils;

public interface Comparavel {
    /**
     * Compara o objeto atual com outro recebido como parâmetro.
     *
     * @param c Objeto a ser comparado.
     * @return Um valor inteiro:
     *         - Maior que 0, se o objeto atual for maior que o parâmetro.
     *         - Menor que 0, se o objeto atual for menor que o parâmetro.
     *         - Igual a 0, se forem equivalentes.
     */
    int comparar(Comparavel c);
}
