package diagram.menu;

import Teste.GUI;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import diagram.componente.Vertice;
import diagram.componente.Rotulo;

import diagram.editor.AreaAplicativo;

/**
 * A classe JMenuMouseVertice utiliza as classes do pacote swing para exibir um menu
 * sempre que o usu�rio final clicar com o bot�o direito do mouse sobre um v�rtice 
 * do editor.
 * 
 * Novos menus podem ser inseridos ao final desta classe caso deseja-se implementar
 * novas op��es de v�rtice que afetam seu contexto. Um exemplo de reutiliza��o da 
 * classe JMenuMouseVertice � mostrado abaixo.
 *
 * Para reutilizar a nova classe criada, deve-se chamar o m�todo setMenuMouseVertice
 * da classe EditorAplicativoGrafo ou EditorAppletGrafo para que seja atualizado 
 * o novo menu popup.<br>
 *<br>
 *&nbsp; import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class MenuNovo extends JMenuMouseVertice<br>
 * {<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public MenuMovo()<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(); // Chama o construtor para instanciar os menus da classe JMenuMouseArea<br>
 *<br> 			
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//inserir o c�digo para criar os novos menus<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this(); //chama o menu da superclasse para tratar das op��es do JMenuMouseArea<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//c�digo para exibir o novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see JMenuMouse
 * @see JMenuMouseArea
 * @see JMenuMouseAresta
 * @see JMenuMouseRotulo
 */
public class JMenuMouseVertice extends JMenuMouse
{
	private Vertice		vertice;
	
	/**
	 * Cria um novo menu popup utilizado quando o usu�rio clicar em um v�rtice
	 * contido dentro do editor.
	 *
	 * @param origem a AreaAplicativo onde ser� exibido o menu popup
	 */
	public JMenuMouseVertice(AreaAplicativo origem, GUI frame) 
	{
		super(origem, frame);
		
		int 		i;
		JMenuItem	menuTemp;
		
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
	 * M�todo invocado pela classe AreaAplicativo sempre que o usu�rio final clicar
	 * com o bot�o direito do mouse sobre um v�rtice contido no editor.
	 *
	 * As informa��es passadas pelos par�metros s�o o objeto r�tulo que obteve o 
	 * clique do usu�rio final e as respectivas coordenadas no momento do clique.
	 *
	 * @param objeto o objeto que teve o evento gerado pelo clique sobre o bot�o 
	 * direito do mouse
	 * @param x a coordenada do mouse no eixo x
	 * @param y a coordenada do mouse no eixo y
	 * @see JMenuMouseArea#menu
	 * @see JMenuMouseAresta#menu
 	 * @see JMenuMouseRotulo#menu
	 */
	public void menu(Object objeto, int x, int y)
	{	
		vertice = (Vertice) objeto;
		show(getAreaAplicativo(), x, y);
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
		JMenuItem	menuTemp[];
		
		menuTemp = new JMenuItem[getNumeroMenus()];
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
		
		getAreaAplicativo().repaint();
	}
	
	//Metodos herados das interfaces de evento do mouse
	
	/**
	 * M�todo invocado sempre que houver um arraste do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum tipo de arraste com o mouse. 
	 *
	 * @param e o evento de arraste do mouse
	 * @see JMenuMouseArea#mouseDragged
	 * @see JMenuMouseAresta#mouseDragged
	 * @see JMenuMouseRotulo#mouseDragged
	 */
	public void mouseDragged(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver um movimento do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum tipo de movimento com o mouse.
	 *
	 * @param e o evento de movimento do mouse
	 * @see JMenuMouseArea#mouseMoved
	 * @see JMenuMouseAresta#mouseMoved
	 * @see JMenuMouseRotulo#mouseMoved
	 */
	public void mouseMoved(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver um clique do mouse sobre a �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum clique com bot�o do mouse.
	 *
	 * @param e o evento de clique do mouse
	 * @see JMenuMouseArea#mouseClicked
	 * @see JMenuMouseAresta#mouseClicked
	 * @see JMenuMouseRotulo#mouseClicked
	 */
	public void mouseClicked(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver uma entrada do mouse na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum movimento de entrada do mouse no editor.
	 *
	 * @param e o evento de entrada do mouse
	 * @see JMenuMouseArea#mouseEntered
	 * @see JMenuMouseAresta#mouseEntered
	 * @see JMenuMouseRotulo#mouseEntered
	 */
	public void mouseEntered(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que houver uma sa�da do mouse na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando algum movimento de sa�da do mouse no editor.
	 *
	 * @param e o evento de sa�da do mouse
	 * @see JMenuMouseArea#mouseExited
	 * @see JMenuMouseAresta#mouseExited
	 * @see JMenuMouseRotulo#mouseExited
	 */
	public void mouseExited(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for pressionado na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando um pressionamento de algum bot�o do mouse.
	 *
	 * @param e o evento pressionamento do bot�o do mouse
	 * @see JMenuMouseArea#mousePressed
	 * @see JMenuMouseAresta#mousePressed
	 * @see JMenuMouseRotulo#mousePressed
	 */
	public void mousePressed(MouseEvent e){}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for liberado na �rea de 
	 * apresenta��o do editor. Mesmo que n�o haja um clique com o bot�o direito 
	 * do mouse exibindo o menu popup, este m�todo ser� chamado.
	 *
	 * Nas subclasses de JMenuMouseVertice esse m�todo deve ser sobrescrito para
	 * tratar as novas op��es que exigem uma intera��o do usu�rio final com o 
	 * editor realizando uma libera��o de algum bot�o do mouse.
	 *
	 * @param e o evento libera��o do bot�o do mouse
	 * @see JMenuMouseArea#mouseReleased
	 * @see JMenuMouseAresta#mouseReleased
	 * @see JMenuMouseRotulo#mouseReleased
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
		JMenuItem menus[] = new JMenuItem[4];
		menus[0] = new JMenuItem(titulosMenus[0]);
		menus[1] = new JMenuItem(titulosMenus[1]);
		menus[2] = new JMenuItem(titulosMenus[2]);
		menus[3] = new JMenuItem(titulosMenus[3]);
		
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
		String texto;
		
		Rotulo rotulo = new Rotulo("Vazio");
		
		texto = JOptionPane.showInputDialog(getAreaAplicativo(), "Digite o conteúdo do rótulo");
		if (texto != null)
			rotulo.setTexto(texto);
		
		vertice.setRotulo(rotulo);
	}
	
	private void definirNovaAltura()
	{
		String 	texto;
		int		altura;
		
		texto = JOptionPane.showInputDialog(getAreaAplicativo(), "Digite a nova altura do vértice");
		if (texto != null)
		{
			try{
				altura = Integer.parseInt(texto);
				vertice.setAltura(altura);
			}catch(NumberFormatException e){
			}
		}
	}
	
	private void definirNovaLargura()
	{
		String 	texto;
		int		largura;
		
		texto = JOptionPane.showInputDialog(getAreaAplicativo(), "Digite a nova altura do vértice");
		if (texto != null)
		{
			try{
				largura = Integer.parseInt(texto);
				vertice.setLargura(largura);
			}catch(NumberFormatException e){
			}
		}
	}
	
	private void removeVertice()
	{
		getAreaAplicativo().getGrafo().removerVertice(vertice);
	}
}