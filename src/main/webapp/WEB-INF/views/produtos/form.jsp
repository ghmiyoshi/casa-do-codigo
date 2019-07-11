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

	<form:form action="${s:mvcUrl('PC#gravar').build() }" method="POST" commandName="produto">
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
		</div>
		
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div>
				<label>${tipoPreco}</label>
				<form:input path="precos[${status.index}].valor" />
				<form:input type="hidden" path="precos[${status.index}].tipo" value="${tipoPreco}" />
			</div>
		</c:forEach>		
		
		<button type="submit">Cadastrar</button>
	</form:form>

</body>
</html>