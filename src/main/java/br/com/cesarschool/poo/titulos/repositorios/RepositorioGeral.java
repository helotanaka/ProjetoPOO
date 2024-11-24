package br.com.cesarschool.poo.titulos.repositorios;

import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

public abstract class RepositorioGeral {

    // Atributo compartilhado pelos repositórios específicos
    protected DAOSerializadorObjetos dao;

    // Construtor vazio que inicializa o DAO
    public RepositorioGeral() {
        // O método abstrato getClasseEntidade() é chamado para inicializar o DAO
        dao = new DAOSerializadorObjetos(getClasseEntidade());
    }

    // Método abstrato a ser implementado pelos repositórios específicos
    public abstract Class<?> getClasseEntidade();

    public DAOSerializadorObjetos getDao() {
        return dao;
    }


}