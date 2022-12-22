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
	
	/*O m�todo listAll permite retornar uma cole��o (ArrayList<Curso>) de objetos curso de todos os
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
	
	/*O m�todo listAllSql(String sql),recebe uma string que � um script sql de consulta. Executa esse script
	retornando uma cole��o de objetos Curso.*/
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
	
	/*O m�todo buscaPorNomeGeral(String str) recebe uma string que pode ou n�o ser vazia. Executa esse script e retorna uma
	cole��o de objetos.*/
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
	O m�todo buscaPorNome(String str) recebe uma string com um nome, cria a script SQL com esse
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
	
	/*O m�todo incluir(Curso curso) recebe um objeto curso com os dados que ser�o inclu�dos, com
	o c�digo igual a zero, pois, sendo uma inclus�o, o c�digo do objeto � inexistente e, portanto, � zero. A linha 149 cria o
	script SQL para consultar se a pessoa a ser inclu�da n�o est� cadastrada. Essa verifica��o � realizada pelo codigo. Caso
	o codigo n�o existe no banco de dados, retorna o objeto com a mensagem que deve ser
	apresentada na camada vis�o.*/
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
			curso.setMsgErro("Curso j� cadastrado");
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
			
			curso.setMsgErro("Inclus�o realizada com sucesso");
		} catch (SQLException e) {
			curso.setMsgErro("Erro de inclus�o");
		}
		return curso;
	}
	
	/*O m�todo alterar(Curso curso) recebe um objeto curso com o c�digo diferente de zero, pois �
	um objeto com dados que dever�o ser alterados no banco de dados. Ap�s a devida altera��o, o m�todo retorna o
	objeto com a mensagem espec�fica para ser apresentada pela camada vis�o.*/
	
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
			curso.setMsgErro("Altera��o realizada com sucesso");
		} catch (SQLException e) {
			curso.setMsgErro("Erro de altera��o");
		}
		return curso;
	}

} 