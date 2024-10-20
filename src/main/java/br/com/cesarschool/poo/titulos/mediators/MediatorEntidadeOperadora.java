package br.com.cesarschool.poo.titulos.mediators;
/*
 * Deve ser um singleton.
 * 
 * Deve ter um atributo repositorioEntidadeOperadora, do tipo RepositorioEntidadeOperadora, que deve 
 * ser inicializado na sua declara��o. Este atributo ser� usado exclusivamente
 * pela classe, n�o tendo, portanto, m�todos set e get.
 * 
 * M�todos: 
 * 
 * pivate String validar(EntidadeOperadora): deve validar os dados do objeto recebido nos seguintes termos: 
 * identificador: deve ser maior que zero e menor que 100000 (1)
 * nome: deve ser preenchido, diferente de branco e de null (2). deve ter entre 5 e 60 caracteres (3).
 * data de validade: deve ser maior do que a data atual mais 180 dias (4). 
 * valorUnitario: deve ser maior que zero (5). 
 * O m�todo validar deve retornar null se o objeto estiver v�lido, e uma mensagem pertinente (ver abaixo)
 * se algum valor de atributo estiver inv�lido.
 * 
 * (1) - Identificador deve estar entre 100 e 1000000.
 * (2) - Nome deve ser preenchido.
 * (3) - Nome deve ter entre 10 e 100 caracteres.
 *
 * public String incluir(EntidadeOperadora entidade): deve chamar o m�todo validar. Se ele 
 * retornar null, deve incluir entidade no reposit�rio. Retornos poss�veis:
 * (1) null, se o retorno do validar for null e o retorno do incluir do 
 * reposit�rio for true.
 * (2) a mensagem retornada pelo validar, se o retorno deste for diferente
 * de null.
 * (3) A mensagem "Entidade j� existente", se o retorno do validar for null
 * e o retorno do reposit�rio for false. 
 *
 * public String alterar(EntidadeOperadora entidade): deve chamar o m�todo validar. Se ele 
 * retornar null, deve alterar entidade no reposit�rio. Retornos poss�veis:
 * (1) null, se o retorno do validar for null e o retorno do alterar do 
 * reposit�rio for true.
 * (2) a mensagem retornada pelo validar, se o retorno deste for diferente
 * de null.
 * (3) A mensagem "Entidade inexistente", se o retorno do validar for null
 * e o retorno do reposit�rio for false.
 * 
 * public String excluir(int identificador): deve validar o identificador. 
 * Se este for v�lido, deve chamar o excluir do reposit�rio. Retornos poss�veis:
 * (1) null, se o retorno do excluir do reposit�rio for true.
 * (2) A mensagem "Entidade inexistente", se o retorno do reposit�rio for false
 * ou se o identificador for inv�lido.
 * 
 * public EntidadeOperadora buscar(int identificador): deve validar o identificador.
 * Se este for v�lido, deve chamar o buscar do reposit�rio, retornando o 
 * que ele retornar. Se o identificador for inv�lido, retornar null. 
 */


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
            return "Entidade j� existente.";
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
            return "Identificador inv�lido.";
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
