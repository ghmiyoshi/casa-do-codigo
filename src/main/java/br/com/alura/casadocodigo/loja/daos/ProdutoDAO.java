package br.com.alura.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.casadocodigo.loja.models.Produto;
import br.com.alura.casadocodigo.loja.models.TipoPreco;

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
	
	public Produto find(Integer id) {
		return manager.createQuery("SELECT DISTINCT(p) FROM Produto p JOIN FETCH p.precos preco WHERE p.id = :id", Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	public BigDecimal somaPrecosPorTipo(TipoPreco tipoPreco) {
		TypedQuery<BigDecimal> query = manager.createQuery("SELECT SUM(preco.valor) FROM Produto p "
				+ "JOIN p.precos preco WHERE preco.tipo = :tipoPreco", BigDecimal.class);
		query.setParameter("tipoPreco", tipoPreco);
		return query.getSingleResult();		
	}

}
