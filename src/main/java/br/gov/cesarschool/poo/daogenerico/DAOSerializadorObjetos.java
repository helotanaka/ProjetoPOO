package br.gov.cesarschool.poo.daogenerico;
/*
 * Esta classe representa um DAO genérico, que inclui, exclui, altera, busca por identificador 
 * único e busca todos, qualquer objeto(s) cujo tipo é subtipo de Entidade.
 * 
 * Sugerimos o uso da API de serialização do JAVA, que grava e lê objetos em arquvos. 
 * Lembrar sempre de fechar (em qualquer circunstância) streams JAVA abertas.
 * 
 * As nuances mais detalhadas do funcionamento desta classe está especificada na classe de testes
 * automatizados br.gov.cesarschool.poo.testes.TestesDAOSerializador.    
 * 
 * A classe deve ter a estrutura (métodos e construtores) dada abaixo.
 */
public class DAOSerializadorObjetos {
	private String nomeDiretorio; 
	public DAOSerializadorObjetos(Class<?> tipoEntidade) {
	}	
	public boolean incluir(Entidade entidade) {
	}
	public boolean alterar(Entidade entidade) {
	}
	public boolean excluir(String idUnico) {
	}
	public Entidade buscar(String idUnico) {
	}
	public Entidade[] buscarTodos() {
	}
}
