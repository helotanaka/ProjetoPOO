package br.com.cesarschool.poo.titulos.repositorios;

import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;
//RepositorioGeral é a mãe dos repositórios específicos. É
//abstrata, tem um método abstrato Class<?> getClasseEntidade()
//e um atributo dao, do tipo DAOSerializadorObjetos. Por herança,
//os repositórios específicos podem usar este atributo dao para
//realizar as operações de inclusão, exclusão, alteração e busca.
//Os repositórios específicos devem implementar o método
//abstrato getClasseEntidade(), retornado o class da entidade
//(exemplo: Transacao.class).
//O construtor desta classe deve ser vazio, e inicializar o atributo
//dao com uma instância de DAOSerializadorObjetos. Como o
//construtor de DAOSerializadorObjetos recebe um Class, a
//chamada dele deve passar o retorno do método
//getClasseEntidade().
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