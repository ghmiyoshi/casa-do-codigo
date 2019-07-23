<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livros de Java, Android, iPhone, PHP e muitos mais - Casa do C�digo</title>
</head>
<body>

	<!-- Coloca form nos inputs para n�o perder as informa��es se um campo estiver inv�lido -->
	<!-- Para isso funcionar, o m�todo form() do ProdutoController precisa receber um Produto -->
	<!-- enctype - Na hora de enviar o request, indicamos que o formulario est� enviando um arquivo de multinforma��es texto e arquivo  -->
	<form:form action="${s:mvcUrl('PC#gravar').build() }" method="POST" commandName="produto" enctype="multipart/form-data">
		<div>
			<label>T�tulo</label>
			<form:input path="titulo" />
			<form:errors path="titulo"/>
		</div>
		
		<div>
			<label>Descri��o</label>
			<form:textarea rows="10" cols="20" path="descricao" />
			<form:errors path="descricao"/>
		</div>
		
		<div>
			<label>P�ginas</label>
			<form:input path="paginas" />
			<form:errors path="paginas"/>
		</div>
		
		<div>
			<label>Data</label>
			<form:input path="dataLancamento" />
			<form:errors path="dataLancamento"/>
		</div>
		
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div>
				<label>${tipoPreco}</label>
				<form:input path="precos[${status.index}].valor" />
				<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}" />
			</div>
		</c:forEach>
		
	<div>
		<label>Sum�rio</label>
		<input name="sumario" type="file">
	</div>	
		
		<button type="submit">Cadastrar</button>
	</form:form>

</body>
</html>