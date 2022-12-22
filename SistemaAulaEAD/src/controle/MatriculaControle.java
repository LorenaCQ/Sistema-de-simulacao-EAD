package controle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.MatriculaDao;
import dao.CursoDao;
import modelo.Matricula;
import modelo.Aluno;
import modelo.Curso;
import util.Utilidade;

public class MatriculaControle extends SelectorComposer<Window>{

	@Wire
	Window windowMatricula;
	@Wire
	Tab tabCad;
	@Wire
	Tab tabPesq;
	@Wire
	Textbox txtMat;
	@Wire
	Textbox txtCod;
	@Wire
	Button btnIncluir;
	@Wire
	Button btnAlterar;
	@Wire
	Button btnExcluir;
	@Wire
	Button btnLimpar;
	@Wire
	Textbox txtPesquisa;
	@Wire
	Listbox lsbPesquisa;
	@Wire
	Button btnLimparLista;
	@Wire
	Button btnAtualizarLista;
	@Wire
	Listbox lbxexemplo;
	
	public String OPCAO = "";
	
	/*As linhas 61 a 66 definem o m�todo criaJanela. Note que na linha 61 h� a anotation @Listen, indicando que � um
	ouvidor de eventos. Esse ouvidor de evento fica aguardando o evento de cria��o da tela quando o arquivo MatriculaVisao.zul
	� ativado. Quando esse evento ocorre @Listen chama o m�todo criaJanela atrav�s da express�o
	onCreate=#windowMatricula, onde windowMatricula � o identificador do componente window do respectivo arquivo zul.
	Quando esse m�todo � chamado, ele chama o m�todo limpaLsbPesquisa (para limpar os dados da tabela de pesquisa)
	e o m�todo limpaDados(para limpar os dados dos componentes da tela constru�da pelo arquivo MatriculaVisao.zul).*/
	@Listen("onCreate=#windowMatricula")
	public void criaJanela() throws SQLException, InterruptedException, IOException, ClassNotFoundException
	{
		this.limpaLsbPesquisa();
		this.limpaDados();
		
		Curso curso = new Curso();
		CursoDao cursoDao = new CursoDao();

		ArrayList<Curso> lista = new ArrayList<Curso>();

		lbxexemplo.getItems().clear();
		lista = cursoDao.listAll();
		Listitem la = (Listitem) new Listitem();
		Listcell laa = (Listcell) new Listcell("Escolha um curso");
		la.appendChild(laa);
		lbxexemplo.appendChild(la);
		
		for (int i = 0; i < lista.size(); i++) {

			Listitem li = (Listitem) new Listitem();
			Listcell lca = (Listcell) new Listcell(lista.get(i).getCodigo());
			li.appendChild(lca);
			lbxexemplo.appendChild(li);
		}
	}
	
	public void limpaLsbPesquisa()
	{
		this.lsbPesquisa.getItems().clear();
	}
	
	/*Esse m�todo � o respons�vel por preencher o componente Listbox lsbPesquisa com registros consultados no banco de dados. 
	Cada um desses registros � retornado como um objeto da classe Matricula, sendo que os objetos s�o armazenados em uma Collection ArrayList. 
	*/
	public void preencherLsbPesquisa () throws SQLException, InterruptedException, IOException, ClassNotFoundException
	{
		this.limpaLsbPesquisa();
		MatriculaDao bdDAO = new MatriculaDao();
		Matricula bd = new Matricula();
		ArrayList<Matricula> al = new ArrayList<Matricula>();
		al = bdDAO.listAll();
		
		for (int i = 0; i < al.size(); i++)
		{
			Listitem li = new Listitem();
			Listcell lc01 = new Listcell();
			lc01.setLabel(al.get(i).getCodigo_curso());
			
			Listcell lc02 = new Listcell();
			lc02.setLabel(al.get(i).getMatricula_aluno());
			
			
			li.appendChild(lc01);
			li.appendChild(lc02);
			
			this.lsbPesquisa.appendChild(li);
		}
	}
	
	/*Esse m�todo permite incluir uma nova matricula no banco de dados.*/
	@Listen("onClick = #btnIncluir")
	public void onClickbtnIncluir() throws Exception
	{
		OPCAO = "I";
		MatriculaDao matriculaDAO = new MatriculaDao();
		Matricula obj = new Matricula();
		
		if (validaDados())
		{
			obj = this.atualizaDados(obj);
			obj = matriculaDAO.incluir(obj);
			Utilidade.mensagem(obj.getMsgErro());
		}
		this.limpaDados();
	}
	
	/*Esse m�todo permite alterar uma matricula no banco de dados.*/
	@Listen("onClick = #btnAlterar")
	public void onClickbtnAlterar() throws Exception
	{
		OPCAO = "A";
		boolean retorno = false;
		MatriculaDao matriculaDAO = new MatriculaDao();
		Matricula matricula = new Matricula();
		String dataS = "";
		
		if (validaDados())
		{
			matricula = this.atualizaDados(matricula);
			MatriculaDao aDao = new MatriculaDao();
			matricula = aDao.alterar(matricula);
			Utilidade.mensagem(matricula.getMsgErro());
		}
		this.limpaDados();
	}
	
	/* Esse m�todo retorna um boolean, true ou false,
	respectivamente, caso a valida��o for correta ou n�o. Para isso, utiliza a vari�vel retorno (boolean), que � inicializada
	com false. Uma vari�vel inteira conta e a vari�vel String msg. Se conta, no final for zero, ent�o as valida��es est�o
	corretas e faz com que a vari�vel retorno receba true. Se conta for maior que zero, ser� apresentada
	a mensagem de erro que � armazenada em msg. Depois valida se o componente
	txtCod � vazio ou nulo. Se n�o for, alimenta msg com uma mensagem e incrementa a vari�vel conta. O mesmo
	ocorre com as demais.*/
	public boolean validaDados() throws InterruptedException, WrongValueException, SQLException, IOException
	{
		boolean retorno = false;
		int conta=0;
		String msg = "";
		
		if (this.txtCod.getText().toString().length() == 0 || this.txtCod.getText().toString().equals("") || this.txtCod == null)
		{
			conta++;
			msg+= "Informe o codigo\n";
		}
		
		if (this.txtMat.getText().toString().length() == 0 || this.txtMat.getText().toString().equals("") || this.txtMat == null)
		{
			conta++;
			msg+= "Informe a matricula\n";
		}
		
		if (conta > 0)
		{
			Utilidade.mensagem(msg);
			retorno = false;
		}
		
		if (conta == 0)
		{
			retorno = true;
		}
		return retorno;
	}
	
	//realiza a atualiza��o dos dados digitados
	public Matricula atualizaDados(Matricula f) throws WrongValueException, SQLException, InterruptedException, IOException
	{
		String dataS = "";
		
	    f.setCodigo_curso(this.txtCod.getValue());
		f.setMatricula_aluno(this.txtMat.getValue());
		this.txtPesquisa.setText("");
		return f;
	}
	
	//limpa a lista que � exibida
	@Listen ("onClick = #btnLimparLista") 
	public void onClickbtnLimparLista() {
		this.limpaLsbPesquisa();
	}
	
	@Listen ("onClick = #btnAtualizarLista")
	public void onClickbtnAtualizarLista() throws SQLException, InterruptedException, IOException, ClassNotFoundException
	{
		this.preencherLsbPesquisa();
	}
	
	/*Esse m�todo, �
	chamado quando ocorre o evento de perda do foco sobre a caixa de texto txtPesquisa (evento onBlur). Quando essa
	a��o ocorrer, a vari�vel aux recebe o conte�do digitado pelo usu�rio na caixa de texto txtPesquisa atrav�s de
	this.txtPesquisa.getValue(). Se o conte�do de aux for vazio , a vari�vel sql recebe o script SQL
	para pesquisar todas as matriculas. Caso contr�rio, a vari�vel sql recebe o script SQL para obter os registros cujo codigo e numero de matricula
	cont�m apenas o que foi informado pelo usu�rio e armazenado em aux. O m�todo de
	pesquisa listAllSql(sql) que executa o script SQL enviado e retorna uma cole��o de objetos para a vari�vel al. Se essa
	collection for diferente de null um loop � efetuado para preencher a listbox de
	pesquisa.*/
	@Listen ("onBlur = #txtPesquisa")
	public void onBlurtxtPesquisa() throws SQLException, InterruptedException, IOException, WrongValueException, ClassNotFoundException
	{
		this.limpaLsbPesquisa();
		MatriculaDao objDAO = new MatriculaDao();
		Matricula bd = new Matricula();
		ArrayList<Matricula> al = new ArrayList<Matricula>();
		ArrayList<Aluno> al2 = new ArrayList<Aluno>();
		String aux = "";
		aux = this.txtPesquisa.getValue();
		String sql = "";
		
		if (aux.length() == 0 || aux.isEmpty() || aux.equals("") || aux == null)
		{
			sql = "SELECT aluno.nome_aluno, aluno_matricula.codigo_matricula, aluno_matricula.matricula_aluno FROM aluno_matricula "
					+ "JOIN aluno ON aluno.matricula = aluno_matricula.matricula_aluno ORDER BY aluno_matricula.matricula_aluno";
		}
		al = objDAO.listAllSql(sql);
		if (al != null)
		{
			for (int i = 0; i < al.size(); i++)
			{
				Listitem li = new Listitem();
				Listcell lc01 = new Listcell();
				lc01.setLabel(al.get(i).getCodigo_curso());
				
				Listcell lc02 = new Listcell();
				lc02.setLabel(al.get(i).getMatricula_aluno());
				
				Listcell lc03 = new Listcell();
				lc02.setLabel(al2.get(i).getNome_aluno());
				
				li.appendChild(lc01);
				li.appendChild(lc02);
				li.appendChild(lc02);
				this.lsbPesquisa.appendChild(li);
			}
		}
		else
		{
			Utilidade.mensagem("Sem dados para visualizar");
		}
	}
	
	//obtem somente o curso selecionado
	@Listen ("onSelect = #lsbPesquisa")
	public void obtemPessoaSelecionada() throws SQLException, ClassNotFoundException, InterruptedException
	{
		int indice = this.lsbPesquisa.getSelectedIndex();
		int id = 0;
		String x;
		MatriculaDao objDAO = new MatriculaDao();
		Matricula matricula = new Matricula();
		if(indice >= 0)
		{
			Listitem li = new Listitem();
			Listcell lc01 = new Listcell();
			lc01 = (Listcell) this.lsbPesquisa.getSelectedItem().getChildren().get(0);
			x = lc01.getLabel().toString();
			matricula = objDAO.buscarPorNome(x);
			if (matricula != null)
			{
				this.txtCod.setText(matricula.getCodigo_curso());
				this.txtMat.setText(matricula.getMatricula_aluno());
				this.limpaLsbPesquisa();
				this.txtPesquisa.setText("");
				this.tabCad.setSelected(true);
			}
		}
	}
	//para limpar os dados dos componentes da tela constru�da pelo arquivo.zul
	public void limpaDados() throws SQLException,InterruptedException, IOException
	{
		OPCAO = "";
		this.txtCod.setText("");
		this.txtMat.setText("");
		this.txtPesquisa.setText("");
		this.limpaLsbPesquisa();
	}
}