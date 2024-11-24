package br.com.cesarschool.poo.titulos.utils;

import br.com.cesarschool.poo.titulos.entidades.Transacao;

public class ComparadorTransacaoPorNomeCredora implements Comparador {

    /**
     * Compares two Transacao objects based on the name of the crediting entity (Entidade Credora).
     *
     * @param c1 First Comparavel object (expected to be Transacao).
     * @param c2 Second Comparavel object (expected to be Transacao).
     * @return A value greater than 0 if the name of c1 is greater than c2,
     *         less than 0 if c1 is less than c2, and 0 if they are equal.
     */
    @Override
    public int comparar(Comparavel c1, Comparavel c2) {
        if (!(c1 instanceof Transacao) || !(c2 instanceof Transacao)) {
            throw new IllegalArgumentException("Os objetos devem ser do tipo Transacao.");
        }

        Transacao transacao1 = (Transacao) c1;
        Transacao transacao2 = (Transacao) c2;

        // Comparing the names of the crediting entities
        String nomeCredora1 = transacao1.getEntidadeCredito().getNome();
        String nomeCredora2 = transacao2.getEntidadeCredito().getNome();

        return nomeCredora1.compareTo(nomeCredora2);
    }
}
