<%@page import="br.ufac.academico.entity.Curso"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page errorPage="erro.jsp" %>
<!DOCTYPE html>
<html>
<jsp:useBean id="cnx" scope="session" class="br.ufac.academico.db.Conexao" />
<jsp:useBean id="cll" scope="page" class="br.ufac.academico.logic.CurriculoLogic" />
<jsp:useBean id="cl" scope="page" class="br.ufac.academico.logic.CursoLogic" />
<jsp:useBean id="curso" scope="page" class="br.ufac.academico.entity.Curso" />

<head>
<meta charset="UTF-8">
<title>Sistema de Controle Acadêmico</title>
</head>
<body>

<%
	if(!cnx.estaConectado()){
%>
<jsp:forward page="index.jsp" />
<%
	}
%>
 
<%
	if(request.getParameter("cancelar") != null){
%>
<jsp:forward page="curriculoListar.jsp" />
<%
	}
%>

<%
	//PESSOAL, FALTOU LIGAR al A cnx;
	cll.setConexao(cnx);
	cl.setConexao(cnx);
	

	if(request.getParameter("confirmar") != null){
		long codigo = Long.parseLong(request.getParameter("codigo")); 
		String cod = request.getParameter("curso");
		curso = cl.recuperar(Integer.parseInt(cod));
		String descricao = request.getParameter("descricao");
		cll.adicionar(codigo, curso, descricao);
		
%>
<jsp:forward page="curriculoListar.jsp" />
<%
	}
%>
<%
	List<Curso> cursos = cl.recuperarTodosPorNome();
%>
<h1>Sistema de Controle Acadêmico</h1>
<h2>Inclusão de Curriculo</h2>
<form action="curriculoIncluir.jsp" method="post">
<p>
	Código: <input type="text" name="codigo" /> <br/>
	Descrição: <input type="text" name="descricao" /> <br/>
	Curso: <select name="curso">
<%
	for(Curso c : cursos){
%>
		<option value="<%= c.getCodigo()%>"><%= c.getNome()%></option>
<%
	}
%>				
	</select>
	
</p>
<p>	
	<input type="submit" name="confirmar" value="Confirmar" />	
	<input type="submit" name="cancelar" value="Cancelar" />
</p>
</form>
</body>
</html>