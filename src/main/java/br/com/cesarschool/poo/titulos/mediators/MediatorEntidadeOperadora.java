package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class MediatorEntidadeOperadora {

    private static MediatorEntidadeOperadora instance;
    private RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();

    private MediatorEntidadeOperadora() {}

    public static synchronized MediatorEntidadeOperadora getInstance() {
        if (instance == null) {
            instance = new MediatorEntidadeOperadora();
        }
        return instance;
    }

    private String validar(EntidadeOperadora entidade) {
        if (entidade.getIdentificador() <= 100 || entidade.getIdentificador() >= 1000000) {
            return "Identificador deve estar entre 100 e 1000000.";
        }
        if (entidade.getNome() == null || entidade.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }
        if (entidade.getNome().length() < 10 || entidade.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        return null;
    }

    public String incluir(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao != null) {
            return validacao;
        }
        if (repositorioEntidadeOperadora.incluir(entidade)) {
            return null;
        } else {
            return "Entidade já existente.";
        }
    }

    public String alterar(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao != null) {
            return validacao;
        }
        if (repositorioEntidadeOperadora.alterar(entidade)) {
            return null;
        } else {
            return "Entidade inexistente.";
        }
    }

    public String excluir(int identificador) {
        if (identificador <= 100 || identificador >= 1000000) {
            return "Identificador inválido.";
        }
        if (repositorioEntidadeOperadora.excluir(identificador)) {
            return null;
        } else {
            return "Entidade inexistente.";
        }
    }

    public EntidadeOperadora buscar(int identificador) {
        if (identificador <= 100 || identificador >= 1000000) {
            return null;
        }
        return repositorioEntidadeOperadora.buscar(identificador);
    }
}

