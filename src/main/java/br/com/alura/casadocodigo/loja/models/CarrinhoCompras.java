package br.com.alura.casadocodigo.loja.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component // Usamos @Component para o Spring conhecer esse objeto que não é uma controller, dao e nenhum outro tipo de objeto específico
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

}
