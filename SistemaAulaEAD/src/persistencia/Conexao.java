package persistencia;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

//classe para realizar a conex�o com o banco de dados
public class Conexao 
{
	private String login = "Lorena";
	private String senha = "123";
	private String host = "localhost";
	private String dbName = "aulaead";
	private String url = "jdbc:mysql://"+host+"/"+dbName;
	
	public Connection conexao = null;
	public Conexao() {}
	
	/*a classe  possui o m�todo getConnection() que � o respons�vel por efetuar a conex�o com o banco de
	dados. H� tamb�m o m�todo main, mas este foi criado apenas para se validar a execu��o da classe e ver se a conex�o
	est� sendo efetuada corretamente */
	
	public Connection getConnection()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			return null;
		}
		try
		{
			this.conexao = DriverManager.getConnection(url,login,senha);
		}
		catch (SQLException ex)
		{
			return null;
		}
		return this.conexao;
	}
	
	public static void main(String[] args)
	{
		Conexao conexao = new Conexao();
		Connection conn = conexao.getConnection();
		System.out.println(conn);
	}
}
