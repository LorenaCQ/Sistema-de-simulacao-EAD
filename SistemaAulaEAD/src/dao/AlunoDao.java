package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import persistencia.Conexao;
import modelo.Aluno;

public class AlunoDao extends Conexao{
	
	/*O m�todo listAll permite retornar uma cole��o (ArrayList<Aluno>) de objetos Aluno de todas os
	alunos cadastrados.*/
	
	public ArrayList<Aluno> listAll() throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Aluno> al = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		Statement st =  null;
		
		String select = "SELECT * FROM aluno order by nome_aluno";
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				while (rs.next()) {
					aluno = new Aluno();
					aluno.setMatricula(rs.getInt("matricula"));
					aluno.setNome_aluno(rs.getString("nome_aluno"));
					aluno.setCelular(rs.getString("celular"));
					al.add(aluno);
				}
		}catch (SQLException e) {
			al=null;
		}finally {
		}
		return al;
	}
	
	/*O m�todo listAllSql(String sql),recebe uma string que � um script sql de consulta. Executa esse script
	retornando uma cole��o de objetos Aluno.*/
	
	public ArrayList<Aluno> listAllSql(String sql) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Aluno> al = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		Statement st =  null;
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(sql);
				
				while (rs.next()) {
					aluno = new Aluno();
					aluno.setMatricula(rs.getInt("matricula"));
					aluno.setNome_aluno(rs.getString("nome_aluno"));
					aluno.setCelular(rs.getString("celular"));
					al.add(aluno);
				}
		}catch (SQLException e) {
			al=null;
		}finally {
		}
		return al;
	}
	
	/*O m�todo buscaPorNomeGeral(String str) recebe uma string que pode ou n�o ser vazia. Executa esse script e retorna uma
	cole��o de objetos.*/
	
	public ArrayList<Aluno> buscarPorNomeGeral(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Aluno> al = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		Statement st =  null;
		
		String select = "SELECT * FROM aluno WHERE nome_aluno = '"+str+"'";
		
		if (str.equals(""))
			select = "SELECT * FROM aluno order by nome_aluno";
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				while (rs.next()) {
					aluno = new Aluno();
					aluno.setMatricula(rs.getInt("matricula"));
					aluno.setNome_aluno(rs.getString("nome_aluno"));
					aluno.setCelular(rs.getString("celular"));
					al.add(aluno);
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
	public Aluno buscarPorNome(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Aluno> al = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		Statement st =  null;
		
		String select = "SELECT * FROM aluno WHERE nome_aluno = '"+str+"'";
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				if (rs.next()) {
					aluno = new Aluno();
					aluno.setMatricula(rs.getInt("matricula"));
					aluno.setNome_aluno(rs.getString("nome_aluno"));
					aluno.setCelular(rs.getString("celular"));
					al.add(aluno);
				}
		}catch (SQLException e) {
			aluno=null;
		}finally {
		}
		return aluno;
	}
	
	/*O m�todo buscaPorId(int id)  recebe um valor inteiro, correspondente a um c�digo de um aluno.
	Cria a string do script de consulta. Efetua a pesquisa e retorna um objeto com os dados encontrados. Se o
	objeto possuir c�digo igual a zero � porque n�o encontrou a pessoa desejada.*/
	
	public Aluno buscarPorId(int id) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		Aluno aluno = new Aluno();
		Statement st =  null;
		
		String select = "SELECT * FROM aluno WHERE matricula = "+id;
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				if (rs.next()) {
					aluno = new Aluno();
					aluno.setMatricula(rs.getInt("matricula"));
					aluno.setNome_aluno(rs.getString("nome_aluno"));
					aluno.setCelular(rs.getString("celular"));
				}
		}catch (SQLException e) {
			aluno=null;
		}finally {
		}
		return aluno;
	}
	
	/*O m�todo incluir(Aluno aluno) recebe um objeto aluno com os dados que ser�o inclu�dos, com
	o c�digo igual a zero, pois, sendo uma inclus�o, o c�digo do objeto � inexistente e, portanto, � zero. A linha 189 cria o
	script SQL para consultar se a pessoa a ser inclu�da n�o est� cadastrada. Essa verifica��o � realizada pelo nome_aluno. Caso
	o nome n�o existe no banco de dados, retorna o objeto com a mensagem que deve ser
	apresentada na camada vis�o.*/
	
	public Aluno incluir(Aluno aluno) throws InterruptedException, SQLException
	{
		Connection conn = this.getConnection();
		PreparedStatement pstm = null;
		String select = "SELECT * FROM aluno WHERE nome_aluno = ?";
		pstm = conn.prepareStatement(select);
		pstm.setString(1, aluno.getNome_aluno());
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next())
		{
			aluno.setMsgErro("Aluno j� cadastrado");
			return aluno;
		}
		
		String insert = "INSERT INTO aluno (nome_aluno, celular)";
		insert+= " VALUES(?,?)";
		
		try
		{
			pstm = conn.prepareStatement(insert);
			pstm.setString(1, aluno.getNome_aluno());
			pstm.setString(2, aluno.getCelular());
			
			pstm.executeUpdate();
			
			aluno.setMsgErro("Inclus�o realizada com sucesso");
		} catch (SQLException e) {
			aluno.setMsgErro("Erro de inclus�o");
		}
		return aluno;
	}
	
	/*O m�todo alterar(Aluno aluno) recebe um objeto aluno com o c�digo diferente de zero, pois �
	um objeto com dados que dever�o ser alterados no banco de dados. Ap�s a devida altera��o, o m�todo retorna o
	objeto com a mensagem espec�fica para ser apresentada pela camada vis�o.*/
	
	public Aluno alterar(Aluno aluno) throws ClassNotFoundException, InterruptedException, SQLException
	{
		boolean retorno = false;
		Connection conn = this.getConnection();
		PreparedStatement pstm;
		
		String update = "UPDATE aluno SET nome_aluno = ?, celular = ?";
		update+= " WHERE matricula = ?";
		
		try
		{
			pstm = conn.prepareStatement(update);
			pstm.setString(1, aluno.getNome_aluno());
			pstm.setString(2, aluno.getCelular());
			pstm.setInt(3, aluno.getMatricula());

			pstm.execute();
			retorno = true;
			aluno.setMsgErro("Altera��o realizada com sucesso");
		} catch (SQLException e) {
			aluno.setMsgErro("Erro de altera��o");
		}
		return aluno;
	}
	
	public Aluno excluir(Aluno aluno) throws ClassNotFoundException, InterruptedException, SQLException
	{
		boolean retorno = false;
		Connection conn = this.getConnection();
		PreparedStatement pstm;
		
		String delete = "DELETE FROM aluno";
		delete+= " WHERE matricula = ?";
		
		try
		{
			pstm = conn.prepareStatement(delete);
			pstm.setString(1, aluno.getNome_aluno());
			pstm.setString(2, aluno.getCelular());
			pstm.setInt(3, aluno.getMatricula());

			pstm.execute();
			retorno = true;
			aluno.setMsgErro("Exclus�o realizada com sucesso");
		} catch (SQLException e) {
			aluno.setMsgErro("Erro de exclus�o");
		}
		return aluno;
	}
	

} //fim da classe AlunoDao