package diagram.menu;

import Teste.GUI;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import diagram.Grafo;

import diagram.componente.Aresta;

import diagram.editor.AreaAplicativo;
import diagram.editor.EditorAplicativoGrafo;
import diagram.editor.EditorAppletGrafo;

import diagram.graphdrawing.SpringModel;

/**
 * A classe JMenuMouseArea utiliza as classes do pacote swing para exibir um menu
 * sempre que o usu�rio final clicar com o bot�o direito do mouse sobre o editor e
 * fora da �rea dos componentes v�rtice, aresta e r�tulo.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas op��es genericas que afetam o contexto do grafo total. Um exemplo de 
 * reutiliza��o da classe JMenuMouseArea � mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o m�todo setMenuMouse 
 * da classe EditorAplicativoGrafo ou EditorAppletGrafo para que seja atualizado 
 * o novo menu popup.<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class MenuNovo extends JMenuMouseArea<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public MenuMovo()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(); // Chama o construtor para instanciar os menus da classe JMenuMouseArea<br>
 *<br> 			
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o c�digo para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public vid menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das op��es do JMenuMouseArea<br>
 *<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//c�digo para exibir o novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see JMenuMouse
 * @see JMenuMouseAresta
 * @see JMenuMouseRotulo
 * @see JMenuMouseVertice
 */
public class JMenuMouseArea extends JMenuMouse
{
        private Grafo grafo;
	/**
	 * Cria um novo menu popup utilizado quando o usu�rio clicar no editor
	 * e fora da �rea dos componentes do grafo.
	 *
	 * @param origem a AreaAplicativo onde ser� exibido o menu popup
	 */
	public JMenuMouseArea(AreaAplicativo origem, GUI frame) 
	{
		super(origem, frame);
		
		int 		i;
		JMenuItem	menuTemp;
		
		criarMenuPadrao();
		
		for (i = 0; i < getNumeroMenus(); i++)
		{
			menuTemp = getMenu(i);
			
			if (menuTemp != null)
				add(menuTemp);
			else
				addSeparator();
		}
                
                grafo = getAreaAplicativo().getEditorAplicativoGrafo().getGrafo();
	}
	
	/**
	 * M�todo invocado pela classe AreaAplicativo sempre que o usu�rio final clicar
	 * com o bot�o direito do mouse sobre o editor e fora da �rea dos componentes.
	 * Neste caso o objeto passado pelo par�metro � nulo, pois o clique dever� ser
	 * fora da �rea de qualquer objeto e x e y s�o as coordenadas do mouse no momento
	 * do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o bot�o 
	 * direito do mouse
	 * @param x a coordenada do mouse no eixo x
	 * @param y a coordenada do mouse no eixo y
	 * @see JMenuMouseAresta#menu
	 * @see JMenuMouseRotulo#menu
 	 * @see JMenuMouseVertice#menu
	 */
	public void menu(Object objeto, int x, int y)
	{	
		JMenuItem	menus[];
		
		menus = new JMenuItem[2];
		menus[0] = getMenu(0);
		menus[1] = getMenu(1);
		
		EditorAplicativoGrafo editorGrafo = getAreaAplicativo().getEditorAplicativoGrafo();
		EditorAppletGrafo editorAppletGrafo = getAreaAplicativo().getEditorAppletGrafo();
		//Identifica se o menu bloquear ou desbloquear fica habilitado...
		//Para o bloquear
		if (editorGrafo != null)
		{
			//Aplicativo
			if (editorGrafo.getEditar() == EditorAplicativoGrafo.NAO_EDITAR)
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
		}	
		else if (editorAppletGrafo != null)
		{
			//Applet
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
		}
		
		show(getAreaAplicativo(), x, y);
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
		JMenuItem	menuTemp[], menuItemSelecionar[];
		JMenu		menuAux;
		
		menuTemp = new JMenuItem[getNumeroMenus()];
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
			menuAux = (JMenu) menuTemp[4];
			
			menuItemSelecionar = new JMenuItem[menuAux.getItemCount()];
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
	}

	//Metodos herados das interfaces de evento do mouse
	
	/**
	 * M�todo invocado sempre que houver um arraste do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see JMenuMouseAresta#mouseDragged
	 * @see JMenuMouseRotulo#mouseDragged
	 * @see JMenuMouseVertice#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver um movimento do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see JMenuMouseAresta#mouseMoved
	 * @see JMenuMouseRotulo#mouseMoved
	 * @see JMenuMouseVertice#mouseMoved
	 */
	public void mouseMoved(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver um clique do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum clique com bot�o do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see JMenuMouseAresta#mouseClicked
	 * @see JMenuMouseRotulo#mouseClicked
	 * @see JMenuMouseVertice#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver uma entrada do mouse na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see JMenuMouseAresta#mouseEntered
	 * @see JMenuMouseRotulo#mouseEntered
	 * @see JMenuMouseVertice#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver uma sa�da do mouse na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum movimento de sa�da do mouse no editor.
	 *
	 * @param e o evento de sa�da do mouse
	 * @see JMenuMouseAresta#mouseExited
	 * @see JMenuMouseRotulo#mouseExited
	 * @see JMenuMouseVertice#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for pressionado na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando um pressionamento de algum bot�o do mouse.
	 *
	 * @param e o evento pressionamento do bot�o do mouse
	 * @see JMenuMouseAresta#mousePressed
	 * @see JMenuMouseRotulo#mousePressed
	 * @see JMenuMouseVertice#mousePressed
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for liberado na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseArea esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando uma libera��o de algum bot�o do mouse.
	 *
	 * @param e o evento libera��o do bot�o do mouse
	 * @see JMenuMouseAresta#mouseReleased
	 * @see JMenuMouseRotulo#mouseReleased
	 * @see JMenuMouseVertice#mouseReleased
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
		subTituloMenuSelecionar[1] = new String("Todos os rótulos");
		subTituloMenuSelecionar[2] = new String("Todos os rótulos das arestas");
		subTituloMenuSelecionar[3] = new String("Todos os rótulos dos vértices");
		subTituloMenuSelecionar[4] = new String("Todos os vértices");
		
		//Cria os menus
		JMenuItem menus[] = new JMenuItem[3];
		menus[0] = new JMenuItem(titulosMenus[0]);
		menus[1] = new JMenuItem(titulosMenus[1]);
		menus[2] = new JMenuItem(titulosMenus[2]);
				
		JMenu menuSelecionar = new JMenu(titulosMenus[3]);
				
		//Cria os submenus do menu selecionar
		JMenuItem subMenus[] = new JMenuItem[5];
		subMenus[0] = new JMenuItem(subTituloMenuSelecionar[0]);
		subMenus[1] = new JMenuItem(subTituloMenuSelecionar[1]);
		subMenus[2] = new JMenuItem(subTituloMenuSelecionar[2]);
		subMenus[3] = new JMenuItem(subTituloMenuSelecionar[3]);
		subMenus[4] = new JMenuItem(subTituloMenuSelecionar[4]);
		
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
		EditorAplicativoGrafo editorGrafo = getAreaAplicativo().getEditorAplicativoGrafo();
		EditorAppletGrafo editorAppletGrafo = getAreaAplicativo().getEditorAppletGrafo();
		
		if (editorGrafo != null)
			//Aplicativo
			editorGrafo.setEditar(EditorAplicativoGrafo.NAO_EDITAR);
		else if (editorAppletGrafo != null)
			//Applet
			editorAppletGrafo.setEditar(EditorAppletGrafo.NAO_EDITAR);
	}
	
	private void desbloquearDiagrama()
	{
		EditorAplicativoGrafo editorGrafo = getAreaAplicativo().getEditorAplicativoGrafo();
		EditorAppletGrafo editorAppletGrafo = getAreaAplicativo().getEditorAppletGrafo();
		
		if (editorGrafo != null)
			//Aplicativo
			editorGrafo.setEditar(EditorAplicativoGrafo.EDITAR);
		else if (editorAppletGrafo != null)
			//Applet
			editorAppletGrafo.setEditar(EditorAppletGrafo.EDITAR);
	}
	
	private void organizarDiagrama()
	{
		EditorAplicativoGrafo editorGrafo = getAreaAplicativo().getEditorAplicativoGrafo();
		EditorAppletGrafo editorAppletGrafo = getAreaAplicativo().getEditorAppletGrafo();

		SpringModel organizarDiagrama = new SpringModel(getAreaAplicativo().getGrafo());
		organizarDiagrama.setTamanhoAresta(130);
		organizarDiagrama.desenharGrafo();
		getAreaAplicativo().repaint();
		
		if (editorGrafo != null)
			//Aplicativo
			editorGrafo.setBarrasRolagem();
		else if (editorAppletGrafo != null)
			//Applet
			editorAppletGrafo.setBarrasRolagem();
	}
	
	private void selecionarArestas()
	{
		int 	i;
		Grafo 	gr;
		Aresta	arestas[];
		
		gr = getAreaAplicativo().getGrafo();
		arestas = gr.getTodasArestas();
		
		for (i = 0; i < gr.getNumeroTotalArestas(); i++)
			grafo.SelecionarComponente(arestas[i]);
		
		getAreaAplicativo().repaint();
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
		
		gr = getAreaAplicativo().getGrafo();
		arestas = gr.getTodasArestas();
		
		//Seleciona os rotulos das arestas
		for (i = 0; i < gr.getNumeroTotalArestas(); i++)
			arestas[i].getRotulo().setRotuloSelecionado(true);
		
		getAreaAplicativo().repaint();
	}
	
	private void selecionarRotulosVertices()
	{
		int 	i;
		Grafo	gr;
		
		gr = getAreaAplicativo().getGrafo();
		
		//Seleciona os rotulos dos vertices
		for (i = 0; i < gr.getNumeroTotalVertices(); i++)
			gr.getVertice(i).getRotulo().setRotuloSelecionado(true);
		
		getAreaAplicativo().repaint();
	}
	
	private void selecionarVertices()
	{
		int 	i;
		Grafo	gr;
		
		gr = getAreaAplicativo().getGrafo();
		
		for (i = 0; i < gr.getNumeroTotalVertices(); i++)
			grafo.SelecionarComponente(gr.getVertice(i));
		
		getAreaAplicativo().repaint();
	}
}