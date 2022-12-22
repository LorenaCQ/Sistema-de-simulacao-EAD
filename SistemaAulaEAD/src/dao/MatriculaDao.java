package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import persistencia.Conexao;
import modelo.Matricula;

public class MatriculaDao extends Conexao{
	
	/*O m�todo listAll permite retornar uma cole��o (ArrayList<Matricula>) de objetos Matricula de todas as
	aluno_curso cadastrados.*/
	public ArrayList<Matricula> listAll() throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Matricula> al = new ArrayList<Matricula>();
		Matricula matricula = new Matricula();
		Statement st =  null;
		
		String select = "SELECT aluno.nome_aluno, aluno_curso.codigo_curso, aluno_curso.matricula_aluno FROM aluno_curso "
				+ "JOIN aluno ON aluno.matricula = aluno_curso.matricula_aluno ORDER BY aluno_curso.matricula_aluno";
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				while (rs.next()) {
					matricula = new Matricula();
					matricula.setCodigo_curso(rs.getString("codigo_curso"));
					matricula.setMatricula_aluno(rs.getString("matricula_aluno"));
					al.add(matricula);
				}
		}catch (SQLException e) {
			al=null;
		}finally {
		}
		return al;
	}
	/*O m�todo listAllSql(String sql),recebe uma string que � um script sql de consulta. Executa esse script
	retornando uma cole��o de objetos aluno_curso.*/
	public ArrayList<Matricula> listAllSql(String sql) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList <Matricula> al = new ArrayList<Matricula>();
		Matricula matricula = new Matricula();
		Statement st =  null;
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(sql);
				
				while (rs.next()) {
					matricula = new Matricula();
					matricula.setCodigo_curso(rs.getString("codigo_curso"));
					matricula.setMatricula_aluno(rs.getString("matricula_aluno"));
					al.add(matricula);
				}
		}catch (SQLException e) {
			al=null;
		}finally {
		}
		return al;
	}
	/*O m�todo buscaPorNomeGeral(String str) recebe uma string que pode ou n�o ser vazia. Executa esse script e retorna uma
	cole��o de objetos.*/
	public ArrayList<Matricula> buscarPorNomeGeral(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Matricula> al = new ArrayList<Matricula>();
		Matricula matricula = new Matricula();
		Statement st =  null;
		
		String select = "SELECT * FROM aluno_curso WHERE nome = '"+str+"'";
		
		if (str.equals(""))
			select = "SELECT * FROM aluno_curso order by nome";
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				while (rs.next()) {
					matricula = new Matricula();
					matricula.setCodigo_curso(rs.getString("codigo_curso"));
					matricula.setMatricula_aluno(rs.getString("matricula_aluno"));
					al.add(matricula);
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
	public Matricula buscarPorNome(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn = this.getConnection();
		
		ArrayList<Matricula> al = new ArrayList<Matricula>();
		Matricula matricula = new Matricula();
		Statement st =  null;
		
		String select = "SELECT * FROM aluno_curso WHERE codigo = '"+str+"'";
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(select);
				
				if (rs.next()) {
					matricula = new Matricula();
					matricula.setCodigo_curso(rs.getString("codigo_curso"));
					matricula.setMatricula_aluno(rs.getString("matricula_aluno"));
					al.add(matricula);
				}
		}catch (SQLException e) {
			matricula=null;
		}finally {
		}
		return matricula;
	}
	
	/*O m�todo incluir(Matricula matricula) recebe um objeto aluno_curso com os dados que ser�o inclu�dos, com
	o c�digo igual a zero, pois, sendo uma inclus�o, o c�digo do objeto � inexistente e, portanto, � zero. A linha 148 cria o
	script SQL para consultar se a pessoa a ser inclu�da n�o est� cadastrada. Essa verifica��o � realizada pelo codigo. Caso
	o codigo n�o existe no banco de dados, retorna o objeto com a mensagem que deve ser
	apresentada na camada vis�o.*/
	
	public Matricula incluir(Matricula matricula) throws InterruptedException, SQLException
	{
		Connection conn = this.getConnection();
		PreparedStatement pstm = null;
		String select = "SELECT * FROM aluno_curso WHERE codigo_curso = ? AND matricula_aluno = ?";
		pstm = conn.prepareStatement(select);
		pstm.setString(1, matricula.getCodigo_curso());
		pstm.setString(2, matricula.getMatricula_aluno());
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next())
		{
			matricula.setMsgErro("Matricula j� cadastrado");
			return matricula;
		}
		
		String insert = "INSERT INTO aluno_curso (codigo_curso,matricula_aluno)";
		insert+= " VALUES(?,?)";
		
		try
		{
			pstm = conn.prepareStatement(insert);
			pstm.setString(1, matricula.getCodigo_curso());
			pstm.setString(2, matricula.getMatricula_aluno());
			
			pstm.executeUpdate();
			
			matricula.setMsgErro("Inclus�o realizada com sucesso");
		} catch (SQLException e) {
			matricula.setMsgErro("Erro de inclus�o");
		}
		return matricula;
	}
	
	/*O m�todo alterar(Matricula matricula) recebe um objeto matricula com o c�digo diferente de zero, pois �
	um objeto com dados que dever�o ser alterados no banco de dados. Ap�s a devida altera��o, o m�todo retorna o
	objeto com a mensagem espec�fica para ser apresentada pela camada vis�o.*/
	public Matricula alterar(Matricula matricula) throws ClassNotFoundException, InterruptedException, SQLException
	{
		boolean retorno = false;
		Connection conn = this.getConnection();
		PreparedStatement pstm;
		
		String update = "UPDATE aluno_curso SET nome = ?";
		update+= " WHERE codigo = ?";
		
		try
		{
			pstm = conn.prepareStatement(update);
			pstm.setString(1, matricula.getMatricula_aluno());

			pstm.execute();
			retorno = true;
			matricula.setMsgErro("Altera��o realizada com sucesso");
		} catch (SQLException e) {
			matricula.setMsgErro("Erro de altera��o");
		}
		return matricula;
	}

} 