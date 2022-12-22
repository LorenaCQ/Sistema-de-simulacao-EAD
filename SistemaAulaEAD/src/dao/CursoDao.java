package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import persistencia.Conexao;
import modelo.Curso;

public class CursoDao extends Conexao{
	
	/*O método listAll permite retornar uma coleção (ArrayList<Curso>) de objetos curso de todos os
	cursos cadastrados.*/
	public ArrayList<Curso> listAll() throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Curso> al = new ArrayList<Curso>();
		Curso curso = new Curso();
		Statement st =  null;
		
		String select = "SELECT * FROM curso order by codigo";
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				while (rs.next()) {
					curso = new Curso();
					curso.setCodigo(rs.getString("codigo"));
					curso.setNome(rs.getString("nome"));
					al.add(curso);
				}
		}catch (SQLException e) {
			al=null;
		}finally {
		}
		return al;
	}
	
	/*O método listAllSql(String sql),recebe uma string que é um script sql de consulta. Executa esse script
	retornando uma coleção de objetos Curso.*/
	public ArrayList<Curso> listAllSql(String sql) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Curso> al = new ArrayList<Curso>();
		Curso curso = new Curso();
		Statement st =  null;
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(sql);
				
				while (rs.next()) {
					curso = new Curso();
					curso.setCodigo(rs.getString("codigo"));
					curso.setNome(rs.getString("nome"));
					al.add(curso);
				}
		}catch (SQLException e) {
			al=null;
		}finally {
		}
		return al;
	}
	
	/*O método buscaPorNomeGeral(String str) recebe uma string que pode ou não ser vazia. Executa esse script e retorna uma
	coleção de objetos.*/
	public ArrayList<Curso> buscarPorNomeGeral(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Curso> al = new ArrayList<Curso>();
		Curso curso = new Curso();
		Statement st =  null;
		
		String select = "SELECT * FROM curso WHERE nome = '"+str+"'";
		
		if (str.equals(""))
			select = "SELECT * FROM curso order by nome";
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				while (rs.next()) {
					curso = new Curso();
					curso.setCodigo(rs.getString("codigo"));
					curso.setNome(rs.getString("nome"));
					al.add(curso);
				}
		}catch (SQLException e) {
			al=null;
		}finally {
		}
		return al;
	}
	/*
	O método buscaPorNome(String str) recebe uma string com um nome, cria a script SQL com esse
	nome, efetua a busca, retornando um objeto com dados ou apenas com uma mensagem para a classe de controle.
	*/
	public Curso buscarPorNome(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Curso> al = new ArrayList<Curso>();
		Curso curso = new Curso();
		Statement st =  null;
		
		String select = "SELECT * FROM curso WHERE codigo = '"+str+"'";
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				if (rs.next()) {
					curso = new Curso();
					curso.setCodigo(rs.getString("codigo"));
					curso.setNome(rs.getString("nome"));
					al.add(curso);
				}
		}catch (SQLException e) {
			curso=null;
		}finally {
		}
		return curso;
	}
	
	/*O método incluir(Curso curso) recebe um objeto curso com os dados que serão incluídos, com
	o código igual a zero, pois, sendo uma inclusão, o código do objeto é inexistente e, portanto, é zero. A linha 149 cria o
	script SQL para consultar se a pessoa a ser incluída não está cadastrada. Essa verificação é realizada pelo codigo. Caso
	o codigo não existe no banco de dados, retorna o objeto com a mensagem que deve ser
	apresentada na camada visão.*/
	public Curso incluir(Curso curso) throws InterruptedException, SQLException
	{
		Connection conn = this.getConnection();
		PreparedStatement pstm = null;
		String select = "SELECT * FROM curso WHERE codigo = ?";
		pstm = conn.prepareStatement(select);
		pstm.setString(1, curso.getCodigo());
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next())
		{
			curso.setMsgErro("Curso já cadastrado");
			return curso;
		}
		
		String insert = "INSERT INTO curso (codigo,nome)";
		insert+= " VALUES(?,?)";
		
		try
		{
			pstm = conn.prepareStatement(insert);
			pstm.setString(1, curso.getCodigo());
			pstm.setString(2, curso.getNome());
			
			pstm.executeUpdate();
			
			curso.setMsgErro("Inclusão realizada com sucesso");
		} catch (SQLException e) {
			curso.setMsgErro("Erro de inclusão");
		}
		return curso;
	}
	
	/*O método alterar(Curso curso) recebe um objeto curso com o código diferente de zero, pois é
	um objeto com dados que deverão ser alterados no banco de dados. Após a devida alteração, o método retorna o
	objeto com a mensagem específica para ser apresentada pela camada visão.*/
	
	public Curso alterar(Curso curso) throws ClassNotFoundException, InterruptedException, SQLException
	{
		boolean retorno = false;
		Connection conn = this.getConnection();
		PreparedStatement pstm;
		
		String update = "UPDATE curso SET nome = ?";
		update+= " WHERE codigo = ?";
		
		try
		{
			pstm = conn.prepareStatement(update);
			pstm.setString(1, curso.getNome());

			pstm.execute();
			retorno = true;
			curso.setMsgErro("Alteração realizada com sucesso");
		} catch (SQLException e) {
			curso.setMsgErro("Erro de alteração");
		}
		return curso;
	}

} 