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
 * sempre que o usuário final clicar com o botão direito do mouse sobre um vértice 
 * do editor.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas opções de vértice que afetam seu contexto. Um exemplo de reutilização da 
 * classe MenuMouseVertice é mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o método setMenuMouseVertice
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
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o código para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das opções do MenuMouseArea<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//código para exibir o novo menu<br>
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
	 * Cria um novo menu popup utilizado quando o usuário clicar em um vértice
	 * contido dentro do editor.
	 *
	 * @param origem a AreaApplet onde será exibido o menu popup
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
	 * Método invocado pela classe AreaApplet sempre que o usuário final clicar
	 * com o botão direito do mouse sobre um vértice contido no editor.
	 *
	 * As informações passadas pelos parâmetros são o objeto rótulo que obteve o 
	 * clique do usuário final e as respectivas coordenadas no momento do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o botão 
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
	 * Método invocado pela própria classe para tratar os eventos de clique 
	 * gerado pelo usuário final na escolha das opções do menu popup.
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
	 * Método invocado sempre que houver um arraste do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see MenuMouseArea#mouseDragged
	 * @see MenuMouseAresta#mouseDragged
	 * @see MenuMouseRotulo#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um movimento do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see MenuMouseArea#mouseMoved
	 * @see MenuMouseAresta#mouseMoved
	 * @see MenuMouseRotulo#mouseMoved
	 */
	public void mouseMoved(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver um clique do mouse sobre a área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum clique com botão do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see MenuMouseArea#mouseClicked
	 * @see MenuMouseAresta#mouseClicked
	 * @see MenuMouseRotulo#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma entrada do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see MenuMouseArea#mouseEntered
	 * @see MenuMouseAresta#mouseEntered
	 * @see MenuMouseRotulo#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * Método invocado sempre que houver uma saída do mouse na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando algum movimento de saída do mouse no editor.
	 *
	 * @param e o evento de saída do mouse
	 * @see MenuMouseArea#mouseExited
	 * @see MenuMouseAresta#mouseExited
	 * @see MenuMouseRotulo#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for pressionado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando um pressionamento de algum botão do mouse.
	 *
	 * @param e o evento pressionamento do botão do mouse
	 * @see MenuMouseArea#mousePressed
	 * @see MenuMouseAresta#mousePressed
	 * @see MenuMouseRotulo#mousePressed
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * Método invocado sempre que algum botão do mouse for liberado na área de 
	 * apresentação do editor. Mesmo que não haja um clique com o botão direito 
	 * do mouse exibindo o menu popup, este método será chamado.
	 *
	 * Nas subclasses de MenuMouseVertice esse método deve ser sobrescrito para
	 * tratar as novas opções que exigem uma interação do usuário final com o 
	 * editor realizando uma liberação de algum botão do mouse.
	 *
	 * @param e o evento liberação do botão do mouse
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
		titulosMenus[0] = new String("Cria novo rótulo");
		titulosMenus[1] = new String("Definir nova altura");
		titulosMenus[2] = new String("Definir nova largura");
		titulosMenus[3] = new String("Remover vértice");
		
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
		CaixaDialogo caixa = new CaixaDialogo(getAreaApplet(), "Digite a nova altura do vértice", 150, 15);
		caixa.showCaixaDialogo(vertice, 3);
	}
	
	private void definirNovaLargura()
	{
		CaixaDialogo caixa = new CaixaDialogo(getAreaApplet(), "Digite a nova largura do vértice", 150, 15);
		caixa.showCaixaDialogo(vertice, 2);
	}
	
	private void removeVertice()
	{
		getAreaApplet().getGrafo().removerVertice(vertice);
	}
}