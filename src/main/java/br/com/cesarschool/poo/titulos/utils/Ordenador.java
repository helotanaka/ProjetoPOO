package br.com.cesarschool.poo.titulos.utils;

public class Ordenador {

    public static void ordenar(Comparavel[] ents, Comparador comp) {
        if (ents == null || ents.length <= 1) {
            return;
        }

        for (int i = 0; i < ents.length - 1; i++) {
            for (int j = 0; j < ents.length - i - 1; j++) {
                if (comp.comparar(ents[j], ents[j + 1]) > 0) {
                    Comparavel temp = ents[j];
                    ents[j] = ents[j + 1];
                    ents[j + 1] = temp;
                }
            }
        }
    }

    public static void ordenar(Comparavel[] comps) {
        if (comps == null || comps.length <= 1) {
            return;
        }

        for (int i = 0; i < comps.length - 1; i++) {
            for (int j = 0; j < comps.length - i - 1; j++) {
                if (comps[j].comparar(comps[j + 1]) > 0) {
                    Comparavel temp = comps[j];
                    comps[j] = comps[j + 1];
                    comps[j + 1] = temp;
                }
            }
        }
    }
}
