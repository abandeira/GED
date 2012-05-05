package diagram.menu;

import java.awt.MenuItem;
import java.awt.Menu;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import diagram.Grafo;

import diagram.componente.Aresta;
import diagram.componente.Vertice;

import diagram.editor.AreaApplet;
import diagram.editor.EditorAppletGrafo;

import diagram.graphdrawing.SpringModel;

/**
 * A classe MenuMouseArea utiliza as classes do pacote awt para exibir um menu
 * sempre que o usu�rio final clicar com o bot�o direito do mouse sobre o editor e
 * fora da �rea dos componentes v�rtice, aresta e r�tulo.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas op��es genericas que afetam o contexto do grafo total. Um exemplo de 
 * reutiliza��o da classe JMenuMouseArea � mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o m�todo setMenuMouse 
 * da classe EditorAppletGrafo para que seja atualizado o novo menu popup.<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class MenuNovo extends MenuMouseArea<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public MenuMovo()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(); // Chama o construtor para instanciar os menus da classe MenuMouseArea<br>
 *<br> 			
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o c�digo para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public vid menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das op��es do MenuMouseArea<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//c�digo para exibir o novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see MenuMouse
 * @see MenuMouseAresta
 * @see MenuMouseRotulo
 * @see MenuMouseVertice
 */
public class MenuMouseArea extends MenuMouse
{
        private Grafo grafo;
	/**
	 * Cria um novo menu popup utilizado quando o usu�rio clicar no editor
	 * e fora da �rea dos componentes do grafo.
	 *
	 * @param origem a AreaApplet onde ser� exibido o menu popup
	 */
	public MenuMouseArea(AreaApplet origem) 
	{
		super(origem);
		
		int 		i;
		MenuItem	menuTemp;
		
		criarMenuPadrao();
		
		for (i = 0; i < getNumeroMenus(); i++)
		{
			menuTemp = getMenu(i);
			
			if (menuTemp != null)
				add(menuTemp);
			else
				addSeparator();
		}
                
                grafo = getAreaApplet().getEditorAppletGrafo().getGrafo();
	}
	
	/**
	 * M�todo invocado pela classe AreaApplet sempre que o usu�rio final clicar
	 * com o bot�o direito do mouse sobre o editor e fora da �rea dos componentes.
	 * Neste caso o objeto passado pelo par�metro � nulo, pois o clique dever� ser
	 * fora da �rea de qualquer objeto e x e y s�o as coordenadas do mouse no momento
	 * do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o bot�o 
	 * direito do mouse
	 * @param x a coordenada do mouse no eixo x
	 * @param y a coordenada do mouse no eixo y
	 * @see MenuMouseAresta#menu
	 * @see MenuMouseRotulo#menu
 	 * @see MenuMouseVertice#menu
	 */
	public void menu(Object objeto, int x, int y)
	{	
		MenuItem	menus[];
		
		menus = new MenuItem[2];
		menus[0] = getMenu(0);
		menus[1] = getMenu(1);
		
		EditorAppletGrafo editorAppletGrafo = getAreaApplet().getEditorAppletGrafo();
		//Identifica se o menu bloquear ou desbloquear fica habilitado...
		if (editorAppletGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
		{
			//N�o deve editar
			menus[0].setEnabled(false);
			menus[1].setEnabled(true);
		}
		else
		{
			//Deve editar
			menus[0].setEnabled(true);
			menus[1].setEnabled(false);
		}
				
		show(getAreaApplet(), x, y);
	}
	
	/**
	 * M�todo invocado pela pr�pria classe sempre que o usu�rio final clicar 
	 * com o bot�o do mouse sobre algum menu do menu popup.
	 *
	 * @param e o evento gerado pelo clique do mouse sobre o menu
	 */
	public void actionPerformed(ActionEvent e)
	{
		int 		i;
		MenuItem	menuTemp[], menuItemSelecionar[];
		Menu		menuAux;
		
		menuTemp = new MenuItem[getNumeroMenus()];
		for (i = 0; i < getNumeroMenus(); i++)
			menuTemp[i] = getMenu(i);
			
		if (e.getSource() == menuTemp[0])
			bloquearDiagrama();
		else if (e.getSource() == menuTemp[1])
			desbloquearDiagrama();
		else if (e.getSource() == menuTemp[3])
			organizarDiagrama();
		else
		{
			//Para os submenus no menu selecionar
			menuAux = (Menu) menuTemp[4];
			
			menuItemSelecionar = new MenuItem[menuAux.getItemCount()];
			for (i = 0; i < menuAux.getItemCount(); i++)
				menuItemSelecionar[i] = menuAux.getItem(i);
				
			//Identifica qual das op��es foi clicada
			if (e.getSource() == menuItemSelecionar[0])
				selecionarArestas();
			else if (e.getSource() == menuItemSelecionar[1])
				selecionarRotulos();
			else if (e.getSource() == menuItemSelecionar[2])
				selecionarRotulosArestas();
			else if (e.getSource() == menuItemSelecionar[3])
				selecionarRotulosVertices();
			else if (e.getSource() == menuItemSelecionar[4])
				selecionarVertices();
		}
		
		getAreaApplet().repaint();
	}
	
	//Metodos herados das interfaces de evento do mouse
	
	/**
	 * M�todo invocado sempre que houver um arraste do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see MenuMouseAresta#mouseDragged
	 * @see MenuMouseRotulo#mouseDragged
	 * @see MenuMouseVertice#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver um movimento do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see MenuMouseAresta#mouseMoved
	 * @see MenuMouseRotulo#mouseMoved
	 * @see MenuMouseVertice#mouseMoved
	 */
	public void mouseMoved(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver um clique do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum clique com bot�o do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see MenuMouseAresta#mouseClicked
	 * @see MenuMouseRotulo#mouseClicked
	 * @see MenuMouseVertice#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver uma entrada do mouse na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see MenuMouseAresta#mouseEntered
	 * @see MenuMouseRotulo#mouseEntered
	 * @see MenuMouseVertice#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver uma sa�da do mouse na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum movimento de sa�da do mouse no editor.
	 *
	 * @param e o evento de sa�da do mouse
	 * @see MenuMouseAresta#mouseExited
	 * @see MenuMouseRotulo#mouseExited
	 * @see MenuMouseVertice#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for pressionado na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando um pressionamento de algum bot�o do mouse.
	 *
	 * @param e o evento pressionamento do bot�o do mouse
	 * @see MenuMouseAresta#mousePressed
	 * @see MenuMouseRotulo#mousePressed
	 * @see MenuMouseVertice#mousePressed
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for liberado na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando uma libera��o de algum bot�o do mouse.
	 *
	 * @param e o evento libera��o do bot�o do mouse
	 * @see MenuMouseAresta#mouseReleased
	 * @see MenuMouseRotulo#mouseReleased
	 * @see MenuMouseVertice#mouseReleased
	 */
	public void mouseReleased(MouseEvent e){}
	
	//Metodos privados da classe	
	private void criarMenuPadrao()
	{
		//Define os titulos dos menus
		String titulosMenus[] = new String[4];
		titulosMenus[0] = new String("Bloquear diagrama");
		titulosMenus[1] = new String("Desbloquear diagrama");
		titulosMenus[2] = new String("Organizar diagrama automaticamente");
		titulosMenus[3] = new String("Selecionar diagrama");
		
		//Define os subtitulos do titulo selecionar
		String subTituloMenuSelecionar[] = new String[5];
		subTituloMenuSelecionar[0] = new String("Todas as arestas");
		subTituloMenuSelecionar[1] = new String("Todos os r�tulos");
		subTituloMenuSelecionar[2] = new String("Todos os r�tulos das arestas");
		subTituloMenuSelecionar[3] = new String("Todos os r�tulos dos v�rtices");
		subTituloMenuSelecionar[4] = new String("Todos os v�rtices");
		
		//Cria os menus
		MenuItem menus[] = new MenuItem[3];
		menus[0] = new MenuItem(titulosMenus[0]);
		menus[1] = new MenuItem(titulosMenus[1]);
		menus[2] = new MenuItem(titulosMenus[2]);
		
		Menu menuSelecionar = new Menu(titulosMenus[3]);
				
		//Cria os submenus do menu selecionar
		MenuItem subMenus[] = new MenuItem[5];
		subMenus[0] = new MenuItem(subTituloMenuSelecionar[0]);
		subMenus[1] = new MenuItem(subTituloMenuSelecionar[1]);
		subMenus[2] = new MenuItem(subTituloMenuSelecionar[2]);
		subMenus[3] = new MenuItem(subTituloMenuSelecionar[3]);
		subMenus[4] = new MenuItem(subTituloMenuSelecionar[4]);
		
		//Adiciona os submenus no menu selecionar
		menuSelecionar.add(subMenus[0]);
		menuSelecionar.add(subMenus[1]);
		menuSelecionar.add(subMenus[2]);
		menuSelecionar.add(subMenus[3]);
		menuSelecionar.add(subMenus[4]);
		
		//Adiciono os menus na lista
		addNovoMenu(menus[0]);
		addNovoMenu(menus[1]);
		addSeparadorMenu();
		addNovoMenu(menus[2]);
		addNovoMenu(menuSelecionar);
	}
	
	private void bloquearDiagrama()
	{
		EditorAppletGrafo editorAppletGrafo = getAreaApplet().getEditorAppletGrafo();
		editorAppletGrafo.setEditar(EditorAppletGrafo.NAO_EDITAR);
	}
	
	private void desbloquearDiagrama()
	{
		EditorAppletGrafo editorAppletGrafo = getAreaApplet().getEditorAppletGrafo();
		editorAppletGrafo.setEditar(EditorAppletGrafo.EDITAR);
	}
	
	private void organizarDiagrama()
	{
		int x, y, largura, altura;
		
		largura = 210;
		altura = 80;
		x = (getAreaApplet().getX() + getAreaApplet().getWidth()/2);
		y = (getAreaApplet().getY() + getAreaApplet().getHeight()/2);
		
		Label msg = new Label("Aguarde um momento.");
		msg.setBounds(20, 35, 140, 15);
		
		Dialog cxDialog = new Dialog(new Frame(), false);
		cxDialog.setLayout(null);
		cxDialog.setTitle("Caixa de Dialogo");
		cxDialog.setResizable(false);
		cxDialog.setSize(largura, altura);
		cxDialog.setLocation(x, y);
		cxDialog.add(msg);
		cxDialog.show();		
		
		EditorAppletGrafo editorAppletGrafo = getAreaApplet().getEditorAppletGrafo();
		
		SpringModel organizarDiagrama = new SpringModel(getAreaApplet().getGrafo());
		organizarDiagrama.setTamanhoAresta(130);
		organizarDiagrama.desenharGrafo();
		getAreaApplet().repaint();
		editorAppletGrafo.setBarrasRolagem();
			
		cxDialog.dispose();
	}
	
	private void selecionarArestas()
	{
		int 	i;
		Grafo 	gr;
		Aresta	arestas[];
		
		gr = getAreaApplet().getGrafo();
		arestas = gr.getTodasArestas();
		
		for (i = 0; i < gr.getNumeroTotalArestas(); i++)
			grafo.SelecionarComponente(arestas[i]);
		
	}
	
	private void selecionarRotulos()
	{
		selecionarRotulosArestas();
		selecionarRotulosVertices();
	}
	
	private void selecionarRotulosArestas()
	{
		int 	i;
		Grafo 	gr;
		Aresta	arestas[];
		
		gr = getAreaApplet().getGrafo();
		arestas = gr.getTodasArestas();
		
		//Seleciona os rotulos das arestas
		for (i = 0; i < gr.getNumeroTotalArestas(); i++)
			arestas[i].getRotulo().setRotuloSelecionado(true);
	}
	
	private void selecionarRotulosVertices()
	{
		int 	i;
		Grafo	gr;
		
		gr = getAreaApplet().getGrafo();
		
		//Seleciona os rotulos dos vertices
		for (i = 0; i < gr.getNumeroTotalVertices(); i++)
			gr.getVertice(i).getRotulo().setRotuloSelecionado(true);
	}
	
	private void selecionarVertices()
	{
		int 	i;
		Grafo	gr;
		
		gr = getAreaApplet().getGrafo();
		
		//Seleciona todos os vertices
		for (i = 0; i < gr.getNumeroTotalVertices(); i++)
			grafo.SelecionarComponente(gr.getVertice(i));
	}	
}