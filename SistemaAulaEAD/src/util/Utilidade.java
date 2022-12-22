package util;

import org.zkoss.zul.Messagebox;

/*Para complementar o correto funcionamento da aplica��o e apresente mensagens adequadas
e padronizadas. Essas mensagens s�o estabelecias pelo controle da interface gr�fica. Para isso, foi criada a classe
que permita apresentar essas mensagens.*/
public class Utilidade 
{
	private static String titulo="Alunos";
	
	public static void mensagem(String msg) throws InterruptedException
	{
		Messagebox.show(msg,titulo,Messagebox.OK, Messagebox.INFORMATION);
	}
	
	public static void mensagemSQL(Exception e) throws InterruptedException
	{
		String msg="MSG:"+e.getMessage()+" CAUSA:"+e.getCause()+" MSGLOC:"+e.getLocalizedMessage();
		Messagebox.show(msg,titulo,Messagebox.OK, Messagebox.INFORMATION);
	}
}