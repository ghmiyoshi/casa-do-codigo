package br.com.alura.casadocodigo.loja.daos;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.alura.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.alura.casadocodigo.loja.configuracao.JPAConfiguration;
import br.com.alura.casadocodigo.loja.models.Produto;
import br.com.alura.casadocodigo.loja.models.TipoPreco;

@RunWith(SpringSpringJUnit4ClassRunner.class)
@Configuration(classes = { JPAConfiguration.class, ProdutoDAO.class})
@ActiveProfiles("test")
public class ProdutoDAOTest {
	
	@Autowired
	ProdutoDAO produtoDao;

	@Test
	@Transactional
	public void deveSomarTodosPrecosPorTipoLivro() {
		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).mais(3)
				.buildAll();

		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN).mais(3).buildAll();

		livrosImpressos.stream().forEach(produtoDao::gravar);
		livrosEbook.stream().forEach(produtoDao::gravar);

		BigDecimal valor = produtoDao.somaPrecosPorTipo(TipoPreco.EBOOK);

		assertEquals(new BigDecimal(40).setScale(2), valor);
	}

}
