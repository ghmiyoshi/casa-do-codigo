<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

	<header id="layout-header">
		<div class="clearfix container">
			<a href="${s: mvcUrl('HC#index').build() }" id="logo"><img src="/casa-do-codigo/resources/imagens/cdc-logo.svg" style="padding-top:0px;"/></a>
			<div id="header-content">
				<nav id="main-nav">
					<ul class="clearfix">
						<li><a href="${s:mvcUrl('CCC#itens').build() }" rel="nofollow">
							<!--<fmt:message key="menu.carrinho">
								<fmt:param value="${carrinhoCompras.quantidade }"></fmt:param>
								</fmt:message> Outra forma de fazer internacionalização -->
							<s:message code="menu.carrinho" arguments="${carrinhoCompras.quantidade }"></s:message> 
						</a></li>
						<li><a href="/pages/sobre-a-casa-do-codigo" rel="nofollow">
							<fmt:message key="menu.sobre" />
						</a></li>
						<li><a href="?locale=pt" rel="nofollow">
						<fmt:message key="menu.pt"></fmt:message></a></li>
						<li><a href="?locale=en_US" rel="nofollow">
						<fmt:message key="menu.en"></fmt:message></a></li>
					</ul>
				</nav>
			</div>
		</div>
	</header>
	
	<nav class="categories-nav">
		<ul class="container">
		<li class="category">
		<a href="${s:mvcUrl('HC#index').build() }">
		<fmt:message key="navegacao.categoria.home"></fmt:message></a>
			<li class="category"><a href="/collections/livros-de-agile">
			<fmt:message key="navegacao.categoria.agile"></fmt:message></a>
			<li class="category"><a href="/collections/livros-de-front-end">
			<fmt:message key="navegacao.categoria.fron_end"></fmt:message></a>
			<li class="category"><a href="/collections/livros-de-games">
			<fmt:message key="navegacao.categoria.games"></fmt:message></a>
			<li class="category"><a href="/collections/livros-de-java">
			<fmt:message key="navegacao.categoria.java"></fmt:message></a>
			<li class="category"><a href="/collections/livros-de-mobile">
			<fmt:message key="navegacao.categoria.mobile"></fmt:message></a>
			<li class="category"><a href="/collections/livros-desenvolvimento-web">
			<fmt:message key="navegacao.categoria.web"></fmt:message></a>
			<li class="category"><a href="/collections/outros"> 
			<fmt:message key="navegacao.categoria.outros"></fmt:message></a>
		</ul>
	</nav>