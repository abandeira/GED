package diagram.menu;

import java.awt.MenuItem;
import java.awt.PopupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import diagram.componente.Vertice;

import diagram.editor.AreaApplet;
import diagram.editor.CaixaDialogo;

/**
 * A classe MenuMouseVertice utiliza as classes do pacote awt para exibir um menu
 * sempre que o usu�rio final clicar com o bot�o direito do mouse sobre um v�rtice 
 * do editor.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas op��es de v�rtice que afetam seu contexto. Um exemplo de reutiliza��o da 
 * classe MenuMouseVertice � mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o m�todo setMenuMouseVertice
 * da classe EditorAppletGrafo para que seja atualizado o novo menu popup.<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class MenuNovo extends MenuMouseVertice<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public MenuMovo()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(); // Chama o construtor para instanciar os menus da classe MenuMouseArea<br>
 *<br> 			
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o c�digo para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das op��es do MenuMouseArea<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//c�digo para exibir o novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see MenuMouse
 * @see MenuMouseArea
 * @see MenuMouseAresta
 * @see MenuMouseRotulo
 */
public class MenuMouseVertice extends MenuMouse
{
	private Vertice		vertice;
	
	/**
	 * Cria um novo menu popup utilizado quando o usu�rio clicar em um v�rtice
	 * contido dentro do editor.
	 *
	 * @param origem a AreaApplet onde ser� exibido o menu popup
	 */
	public MenuMouseVertice(AreaApplet origem) 
	{
		super(origem);
		
		int 		i;
		MenuItem	menuTemp;
		
		vertice = null;	
		criarMenuVerticePadrao();
		
		for (i = 0; i < getNumeroMenus(); i++)
		{
			menuTemp = getMenu(i);
			
			if (menuTemp != null)
				add(menuTemp);
			else
				addSeparator();
		}		
	}
	
	/**
	 * M�todo invocado pela classe AreaApplet sempre que o usu�rio final clicar
	 * com o bot�o direito do mouse sobre um v�rtice contido no editor.
	 *
	 * As informa��es passadas pelos par�metros s�o o objeto r�tulo que obteve o 
	 * clique do usu�rio final e as respectivas coordenadas no momento do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o bot�o 
	 * direito do mouse
	 * @param x a coordenada do mouse no eixo x
	 * @param y a coordenada do mouse no eixo y
	 * @see MenuMouseArea#menu
	 * @see MenuMouseAresta#menu
 	 * @see MenuMouseRotulo#menu
	 */
	public void menu(Object objeto, int x, int y)
	{	
		vertice = (Vertice) objeto;
		show(getAreaApplet(), x, y);
	}
	
	/**
	 * M�todo invocado pela pr�pria classe para tratar os eventos de clique 
	 * gerado pelo usu�rio final na escolha das op��es do menu popup.
	 *
	 * @param e o evento gerado pelo clique do mouse sobre o menu
	 */
	public void actionPerformed(ActionEvent e)
	{
		int 		i;
		MenuItem	menuTemp[];
		
		menuTemp = new MenuItem[getNumeroMenus()];
		for (i = 0; i < getNumeroMenus(); i++)
			menuTemp[i] = getMenu(i);
			
		if (e.getSource() == menuTemp[0])
			criarNovoRotulo();
		else if (e.getSource() == menuTemp[2])
			definirNovaAltura();
		else if (e.getSource() == menuTemp[3])
			definirNovaLargura();
		else if (e.getSource() == menuTemp[5])
			removeVertice();
		
		getAreaApplet().repaint();
	}
	
	//Metodos herados das interfaces de evento do mouse
	
	/**
	 * M�todo invocado sempre que houver um arraste do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see MenuMouseArea#mouseDragged
	 * @see MenuMouseAresta#mouseDragged
	 * @see MenuMouseRotulo#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver um movimento do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see MenuMouseArea#mouseMoved
	 * @see MenuMouseAresta#mouseMoved
	 * @see MenuMouseRotulo#mouseMoved
	 */
	public void mouseMoved(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver um clique do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum clique com bot�o do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see MenuMouseArea#mouseClicked
	 * @see MenuMouseAresta#mouseClicked
	 * @see MenuMouseRotulo#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver uma entrada do mouse na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see MenuMouseArea#mouseEntered
	 * @see MenuMouseAresta#mouseEntered
	 * @see MenuMouseRotulo#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver uma sa�da do mouse na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum movimento de sa�da do mouse no editor.
	 *
	 * @param e o evento de sa�da do mouse
	 * @see MenuMouseArea#mouseExited
	 * @see MenuMouseAresta#mouseExited
	 * @see MenuMouseRotulo#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for pressionado na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando um pressionamento de algum bot�o do mouse.
	 *
	 * @param e o evento pressionamento do bot�o do mouse
	 * @see MenuMouseArea#mousePressed
	 * @see MenuMouseAresta#mousePressed
	 * @see MenuMouseRotulo#mousePressed
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for liberado na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando uma libera��o de algum bot�o do mouse.
	 *
	 * @param e o evento libera��o do bot�o do mouse
	 * @see MenuMouseArea#mouseReleased
	 * @see MenuMouseAresta#mouseReleased
	 * @see MenuMouseRotulo#mouseReleased
	 */
	public void mouseReleased(MouseEvent e){}
	
	//Metodos privados da classe
	private void criarMenuVerticePadrao()
	{
		//Define os titulos dos menus
		String titulosMenus[] = new String[4];
		titulosMenus[0] = new String("Cria novo r�tulo");
		titulosMenus[1] = new String("Definir nova altura");
		titulosMenus[2] = new String("Definir nova largura");
		titulosMenus[3] = new String("Remover v�rtice");
		
		//Cria os menus
		MenuItem menus[] = new MenuItem[4];
		menus[0] = new MenuItem(titulosMenus[0]);
		menus[1] = new MenuItem(titulosMenus[1]);
		menus[2] = new MenuItem(titulosMenus[2]);
		menus[3] = new MenuItem(titulosMenus[3]);
		
		//Adiciono os menus na lista
		addNovoMenu(menus[0]);
		addSeparadorMenu();
		addNovoMenu(menus[1]);
		addNovoMenu(menus[2]);
		addSeparadorMenu();
		addNovoMenu(menus[3]);
	}
	
	private void criarNovoRotulo()
	{
		CaixaDialogo caixa = new CaixaDialogo(getAreaApplet());
		caixa.showCaixaDialogo(vertice.getRotulo());
	}
	
	private void definirNovaAltura()
	{
		CaixaDialogo caixa = new CaixaDialogo(getAreaApplet(), "Digite a nova altura do v�rtice", 150, 15);
		caixa.showCaixaDialogo(vertice, 3);
	}
	
	private void definirNovaLargura()
	{
		CaixaDialogo caixa = new CaixaDialogo(getAreaApplet(), "Digite a nova largura do v�rtice", 150, 15);
		caixa.showCaixaDialogo(vertice, 2);
	}
	
	private void removeVertice()
	{
		getAreaApplet().getGrafo().removerVertice(vertice);
	}
}