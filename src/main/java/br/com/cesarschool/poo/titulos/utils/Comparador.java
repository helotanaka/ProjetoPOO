package br.com.cesarschool.poo.titulos.utils;

public interface Comparador {
    /**
     * Compara dois objetos do tipo Comparavel.
     *
     * @param c1 Primeiro objeto a ser comparado.
     * @param c2 Segundo objeto a ser comparado.
     * @return Um valor inteiro:
     *         - Maior que 0, se o primeiro objeto for maior que o segundo.
     *         - Menor que 0, se o primeiro objeto for menor que o segundo.
     *         - Igual a 0, se forem equivalentes.
     */
    int comparar(Comparavel c1, Comparavel c2);
}
