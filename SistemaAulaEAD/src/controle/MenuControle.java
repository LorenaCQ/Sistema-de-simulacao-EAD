package controle;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

public class MenuControle extends SelectorComposer<Window> {
	
	@Wire
	Menuitem menuItemAlunos;
	@Wire
	Menuitem menuItemCurso;
	@Wire
	Menuitem menuItemMatricula;
	
	//Neste arquivos possui 3 @Listen (ouvidor de eventos), em que cada um  possui a window que é o identificador do componente window do respectivo arquivo zul.
	@Listen("onClick = #menuItemAlunos")
	public void abrirJanelaPessoas() throws InterruptedException
	{
		String window = "AlunoVisao.zul";
		Window wAberta = (Window) Executions.createComponents(window, null, null);
		wAberta.doModal();
	}
	
	@Listen("onClick = #menuItemCurso")
	public void abrirJanelaCursos() throws InterruptedException
	{
		String window = "CursoVisao.zul";
		Window wAberta = (Window) Executions.createComponents(window, null, null);
		wAberta.doModal();
	}
	
	@Listen("onClick = #menuItemMatricula")
	public void abrirJanelaMatriculas() throws InterruptedException
	{
		String window = "MatriculaVisao.zul";
		Window wAberta = (Window) Executions.createComponents(window, null, null);
		wAberta.doModal();
	}
	
}