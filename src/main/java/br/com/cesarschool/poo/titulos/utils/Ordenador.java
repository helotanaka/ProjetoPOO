package br.com.cesarschool.poo.titulos.utils;

public class Ordenador {

    /**
     * Ordena um array de objetos do tipo Comparavel usando a lógica de comparação definida na própria interface Comparavel.
     *
     * @param comps Array de objetos do tipo Comparavel.
     */
    public static void ordenar(Comparavel[] comps) {
        for (int i = 0; i < comps.length - 1; i++) {
            for (int j = 0; j < comps.length - i - 1; j++) {
                if (comps[j].comparar(comps[j + 1]) > 0) {
                    // Troca os objetos se estiverem fora de ordem
                    Comparavel temp = comps[j];
                    comps[j] = comps[j + 1];
                    comps[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Ordena um array de objetos do tipo Comparavel usando a lógica de comparação fornecida por um Comparador.
     *
     * @param comps Array de objetos do tipo Comparavel.
     * @param comparador Comparador que define a lógica de comparação entre dois objetos.
     */
    public static void ordenar(Comparavel[] comps, Comparador comparador) {
        for (int i = 0; i < comps.length - 1; i++) {
            for (int j = 0; j < comps.length - i - 1; j++) {
                if (comparador.comparar(comps[j], comps[j + 1]) > 0) {
                    // Troca os objetos se estiverem fora de ordem
                    Comparavel temp = comps[j];
                    comps[j] = comps[j + 1];
                    comps[j + 1] = temp;
                }
            }
        }
    }
}
