package br.com.alura.casadocodigo.loja.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component // Usamos @Component para o Spring conhecer esse objeto que não é uma controller, dao e nenhum outro tipo de objeto específico
@Scope(value=WebApplicationContext.SCOPE_SESSION) // SCOPE_APPLICATION - Desde que o servidor é iniciado, no escopo de aplicação, apenas um objeto na memória é manipulado, o que causa conflito quando temos mais de um usuário usando a nossa aplicação
public class CarrinhoCompras {
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();
	
	public void add(CarrinhoItem  item) {
		itens.put(item, getQuantidade(item) + 1);
	}

	private int getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		return itens.get(item);
	}
	
	public int getQuantidade() {
		return itens.values().stream().reduce(0, (proximo, acumulador) -> proximo + acumulador);
	}

}
