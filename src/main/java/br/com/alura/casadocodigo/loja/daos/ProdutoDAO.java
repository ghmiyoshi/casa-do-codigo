package br.com.alura.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.casadocodigo.loja.models.Produto;

@Repository // Defini que o ProdutoDAO será gerenciado pelo Spring
@Transactional // Defini que o nosso ProdutoDAO é uma classe Transacional 
public class ProdutoDAO {

	@PersistenceContext // O EntityManager é fornecido pelo Spring
	private EntityManager manager;

	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	public List<Produto> listar() {
		return manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
	}

}
