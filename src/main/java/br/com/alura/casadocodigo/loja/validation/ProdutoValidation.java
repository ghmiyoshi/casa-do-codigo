package br.com.alura.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.alura.casadocodigo.loja.models.Produto;

public class ProdutoValidation implements Validator {

	public void valida(Produto produto, Errors errors) {

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz); // isAssignableFrom() - Verifica se a classe recebida por parâmetro é de fato um produto
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required"); // rejectIfEmpty - Erro de validação se o campo estiver vazio
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		
		Produto produto = (Produto) target;
		if (produto.getPaginas() <= 0) {
			errors.rejectValue("paginas", "field.required");
		}
	}

}
