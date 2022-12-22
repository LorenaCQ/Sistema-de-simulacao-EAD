package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistencia.Conexao;
import modelo.Usuario;

public class UsuarioDao extends Conexao{
	
	
	//utilizado para verificar se usuário possui cadastro no sistema
	public boolean fazerLogin(Usuario usr) throws InterruptedException, SQLException
	{
		Connection conn = this.getConnection();
		PreparedStatement pstm = null;
		String select = "SELECT * FROM usuarios WHERE login = ?";
		pstm = conn.prepareStatement(select);
		pstm.setString(1, usr.getLogin());
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next() == false)
		{
			usr.setMsgErro("Usuário não cadastrado");
			return true;
		}
		
		return false;
	}
	
	

} //fim da classe UsuarioDao