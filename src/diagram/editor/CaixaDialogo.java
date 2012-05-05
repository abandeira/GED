package diagram.editor;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;

import diagram.componente.Aresta;
import diagram.componente.Rotulo;
import diagram.componente.Vertice;

/**
 * Cria uma caixa de di�logo customizada que executa determinadas fun��es quando o
 * usu�rio final interage com o editor de diagramas. Est� classe � automaticamente
 * inst�nciada pelas API do editor sempre que for utilizada a applet do pacote awt.
 * Portanto est� classe n�o precisa ser inst�nciada quando houver a necessidade de 
 * utilizar o editor de diagramas. 
 *
 * A caixa de di�logo ser� exibida no centro do editor de diagramas.
 *
 * @author Luis Henrique Castilho da Silva
 */
public class CaixaDialogo extends Dialog implements WindowListener, ActionListener, KeyListener
{
	private final int	LARGURA_CAIXA = 300,
						ALTURA_CAIXA = 150;
	
	private Label		lblRotulo;
	
	private TextField	caixaInformacao;
	
	private	Button		botaoOk, botaoCancel;
	
	private Rotulo		rotulo;
	
	private Vertice		vertice;
	
	private AreaApplet	areaGrafo;
	
	private int			opcao;
	
	/**
	 * Cria uma caixa de di�logo compat�vel com o pacote awt perguntando ao usu�rio 
	 * se ele deseja inserir informa��es de texto dentro da caixa de texto. Os bot�es
	 * criados neste construtor s�o o bot�o <B>Ok</B> e <B>Cancelar</B>.
	 *
	 * @param areaGrafo a �rea na qual a caixa de dialogo ser� exibida
	 */
	public CaixaDialogo(AreaApplet areaGrafo)
	{
		super(new Frame(), true);
		
		int x, y;
		
		opcao = 0;
		this.areaGrafo = areaGrafo;
		x = (areaGrafo.getX() + areaGrafo.getWidth()/2) - LARGURA_CAIXA/2;
		y = (areaGrafo.getY() + areaGrafo.getHeight()/2) - ALTURA_CAIXA/2;
		
		setLayout(null);
		setTitle("Caixa de Dialogo");
		setResizable(false);
		setSize(LARGURA_CAIXA, ALTURA_CAIXA);
		setLocation(x, y);
		
		lblRotulo = new Label("Digite o conte�do do r�tulo.");
		lblRotulo.setBounds(20, 40, 160, 15);
		add(lblRotulo);
		
		caixaInformacao = new TextField(50);
		caixaInformacao.setBounds(45, 70, 200, 20);
		add(caixaInformacao);
		
		botaoOk = new Button("Ok");
		botaoOk.setBounds(60, 105, 80, 20);
		botaoOk.addActionListener(this);
		botaoOk.addKeyListener(this);
		add(botaoOk);
		
		botaoCancel = new Button("Cancelar");
		botaoCancel.setBounds(160, 105, 80, 20);
		botaoCancel.addActionListener(this);
		botaoCancel.addKeyListener(this);
		add(botaoCancel);
		
		addWindowListener(this);
	}
	
	/**
	 * Cria uma caixa de di�logo compat�vel com o pacote awt perguntando ao usu�rio 
	 * se ele deseja remover os componentes selecionados. Os bot�es
	 * criados neste construtor s�o o bot�o <B>Sim</B> e <B>N�o</B>.
	 *
	 * Caso o par�metro quest�o for verdadeiro, um bot�o do tipo n�o ser� criado,
	 * caso contr�rio um bot�o Cancelar ser� criado.
	 *
	 * @param areaGrafo a �rea na qual a caixa de dialogo ser� exibida
	 * @param questao se o nome bot�o dever� ser modificado para n�o
	 */
	public CaixaDialogo(AreaApplet areaGrafo, boolean questao)
	{
		super(new Frame(), true);
		
		int x, y;
		
		opcao = 0;
		this.areaGrafo = areaGrafo;
		x = (areaGrafo.getX() + areaGrafo.getWidth()/2) - LARGURA_CAIXA/2;
		y = (areaGrafo.getY() + areaGrafo.getHeight()/2) - ALTURA_CAIXA/2;
		
		setLayout(null);
		setTitle("Caixa de Dialogo");
		setResizable(false);
		setSize(LARGURA_CAIXA, ALTURA_CAIXA);
		setLocation(x, y);
		
		lblRotulo = new Label("Deseja realmente remover os componentes?");
		lblRotulo.setBounds(20, 40, 250, 15);
		add(lblRotulo);
		
		if (questao)
		{
			botaoOk = new Button("Sim");
			botaoCancel = new Button("N�o");
		}
		else
		{
			botaoOk = new Button("Ok");
			botaoCancel = new Button("Cancelar");	
		}	
		
		botaoOk.setBounds(60, 90, 80, 20);
		botaoOk.addActionListener(this);
		botaoOk.addKeyListener(this);
		add(botaoOk);
		
		botaoCancel.setBounds(160, 90, 80, 20);
		botaoCancel.addActionListener(this);
		botaoCancel.addKeyListener(this);
		add(botaoCancel);
		
		addWindowListener(this);
	}
	
	/**
	 * Cria uma caixa de di�logo compat�vel com o pacote awt, por�m o texto da pergunta 
	 * dever� � passado pelo par�metro mensagem. Est� caixa de dialogo cont�m uma caixa 
	 * de texto na qual os usu�rios podem inserir as informa��es necess�rias para responder
	 * a pergunta.Os bot�es criados neste construtor s�o o bot�o <B>Ok</B> e <B>Cancelar</B>.
	 *
	 * @param areaGrafo a �rea na qual a caixa de dialogo ser� exibida
	 * @param mensagem  a pergunta a ser exibida na caixa de di�logo
	 * @param larguraMensagem o comprimento total da largura ocupado pela pergunta
	 * @param alturaMensagem a comprimento total da altura ocupada pela pergunta
	 */
	public CaixaDialogo(AreaApplet areaGrafo, String mensagem, int larguraMensagem, int alturaMensagem)
	{
		this(areaGrafo);
		lblRotulo.setText(mensagem);
		lblRotulo.setBounds(20, 40, larguraMensagem, alturaMensagem);
	}
	
	/**
	 * Exibe a caixa de di�logo e aguarda um evento do mouse para executar determinada
	 * tarefa implementada nos m�todos de eventos das interfaces ActionListener, 
	 * KeyListener e WindowListener.
	 * 
	 * Este m�todo � automaticamente chamado pela classe AreaGrafo quando um evento
	 * ocorre sobre algum componente.
	 *
	 * @param rotulo o rot�lo que teve um evento gerado sobre ele
	 */
	public void showCaixaDialogo(Rotulo rotulo)
	{
		opcao = 1;
		this.rotulo = rotulo;
		show();
	}
	
	/**
	 * Exibe a caixa de di�logo e aguarda um evento do mouse para executar determinada
	 * tarefa implementada nos m�todos de eventos das interfaces ActionListener, 
	 * KeyListener e WindowListener.
	 * 
	 * Este m�todo � automaticamente chamado pela classe AreaGrafo quando um evento
	 * ocorre sobre algum componente.
	 *
	 * Para o par�metro op��o existem 4 valores implementados que variam de 1 a 4.
	 * No valor 1, o m�todo muda o texto do rotulo apresentado. No valor 2, o m�todo 
	 * modifica o valor da largura do v�rtice. No valor 2, o m�todo modifica o 
	 * valor da altura e por fim no valor 4, o m�todo deleta os componentes selecionados.
	 * Outros valores n�o realizam nenhuma tarefa.
	 *
	 * @param vertice o v�rtice que teve um evento gerado sobre ele
	 * @param opcao a op��o que define o tipo de tarefa a ser executada
	 */
	public void showCaixaDialogo(Vertice  vertice, int opcao)
	{
		if ((opcao == 2) || (opcao == 3))
			this.opcao = opcao;
		else
			this.opcao = 0;

		this.vertice = vertice;
		show();
	}
	
	/**
	 * Exibe a caixa de di�logo e aguarda um evento do mouse para executar determinada
	 * tarefa implementada nos m�todos de eventos das interfaces ActionListener, 
	 * KeyListener e WindowListener.
	 * 
	 * Este m�todo � automaticamente chamado pela classe AreaGrafo quando um evento
	 * ocorre sobre algum componente. Por padr�o esse m�todo automaticamente executa
	 * a tarefa de deletar todos os componentes atualmente selecionados no editor
	 * de diagramas.
	 */
	public void showCaixaDialogo()
	{
		opcao = 4;
		show();
	}
	
	//Metodos herdados da interface WindowListener
	
	/**
	 * M�todo chamado sempre que uma janela n�o aguarda um janela ativada.
	 *
	 * @param e o evento de janela (Window)
	 */
	public void windowActivated(WindowEvent e){}

	/**
	 * M�todo chamado sempre que uma janela for fechada.
	 *
	 * @param e o evento de janela (Window)
	 */
	public void windowClosed(WindowEvent e){}
	
	/**
	 * M�todo chamado sempre que uma janela estiver sendo fechada.
	 *
	 * @param e o evento de janela (Window)
	 */
	public void windowClosing(WindowEvent e)
	{
		dispose();
	}
	
	/**
	 * M�todo chamado sempre que uma janela for ativada.
	 *
	 * @param e o evento de janela (Window)
	 */
	public void windowDeactivated(WindowEvent e){}
	
	/**
	 * M�todo chamado sempre que uma janela 
	 *
	 * @param e o evento de janela (Window)
	 */
	public void windowDeiconified(WindowEvent e){} 
	
	/**
	 * M�todo chamado sempre que uma janela mudar do estado normal para minimizado.
	 *
	 * @param e o evento de janela (Window)
	 */
	public void windowIconified(WindowEvent e){}
	
	/**
	 * M�todo chamado sempre que uma janela for aberta.
	 *
	 * @param e o evento de janela (Window)
	 */
	public void windowOpened(WindowEvent e){}
	
	//Metodos herdados da interface ActionListener
	
	/**
	 * M�todo invocado sempre que ocorrer um clique do mouse sobre algum bot�o
	 * contido dentro da caixa de di�logo.
	 *
	 * @param e o evento de clique do mouse sobre algum bot�o
	 */
	public void actionPerformed(ActionEvent e)
	{
		int 	valorInteiro, i;
		Aresta	ares[];
		Vertice	ver;
			
		if (e.getSource() == botaoOk)
		{
			if (opcao == 1)
			{
				//Mudar o texto do rotulo
				rotulo.setTexto(caixaInformacao.getText());
				areaGrafo.repaint();
				dispose();
			}
			else if (opcao == 2)
			{
				//Mudar o valor da largura do vertice
				try{
					valorInteiro = Integer.parseInt(caixaInformacao.getText());
					vertice.setLargura(valorInteiro);
					areaGrafo.repaint();
				}catch(NumberFormatException erro){}
				dispose();
			}
			else if (opcao == 3)
			{
				//Mudar o valor da altura do vertice
				try{
					valorInteiro = Integer.parseInt(caixaInformacao.getText());
					vertice.setAltura(valorInteiro);
					areaGrafo.repaint();
				}catch(NumberFormatException erro){}
				dispose();
			}
			else if (opcao == 4)
			{
				//Bot�o delete pressionado
				for (i = areaGrafo.getGrafo().getNumeroTotalVertices() - 1; i >= 0; i--)
				{
					vertice = areaGrafo.getGrafo().getVertice(i);
					if (vertice.getSelecionado())
					{
						//Deletar componente
						areaGrafo.getGrafo().removerVertice(vertice);
					}
					else
					{
						//Manter componente
						if (vertice.getRotulo().getRotuloSelecionado())	
							vertice.getRotulo().setTexto("");
					}
				}

				ares = areaGrafo.getGrafo().getTodasArestas();
				for (i = areaGrafo.getGrafo().getNumeroTotalArestas() - 1; i >= 0; i--)
				{
					if (ares[i].getSelecionado())
					{
						//Deletar aresta
						areaGrafo.getGrafo().removerAresta(ares[i]);
					}
					else
					{
						//Manter aresta
						if (ares[i].getRotulo().getRotuloSelecionado())
							ares[i].getRotulo().setTexto("");
					}
				}
				
				areaGrafo.repaint();
				dispose();
			}
		}
		else if (e.getSource() == botaoCancel)
		{
			dispose();
		}
	}
	
	//Metodos herdados da interface KeyListener
	
	/**
	 * M�todo invocado sempre que uma tecla for pressionada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyPressed(KeyEvent e)
	{
		int 	valorInteiro, i;
		Vertice	ver;
		Aresta	ares[];
		
		if (e.getSource() == botaoOk)
		{
			//Tecla sobre o bot�o Ok
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				//Tecla enter
				if (opcao == 1)
				{
					//Mudar o texto do rotulo
					rotulo.setTexto(caixaInformacao.getText());
					areaGrafo.repaint();
					dispose();
				}
				else if (opcao == 2)
				{
					try{
						valorInteiro = Integer.parseInt(caixaInformacao.getText());
						vertice.setLargura(valorInteiro);
						areaGrafo.repaint();
					}catch(NumberFormatException erro){}
					dispose();
				}
				else if (opcao == 3)
				{
					try{
						valorInteiro = Integer.parseInt(caixaInformacao.getText());
						vertice.setAltura(valorInteiro);
						areaGrafo.repaint();
					}catch(NumberFormatException erro){}
					dispose();
				}
				else if (opcao == 4)
				{
					//Bot�o delete pressionado
					for (i = 0; i < areaGrafo.getGrafo().getNumeroTotalVertices(); i++)
					{
						vertice = areaGrafo.getGrafo().getVertice(i);
						if (vertice.getSelecionado())
						{
							//Deletar componente
							areaGrafo.getGrafo().removerVertice(vertice);
						}
						else
						{
							//Manter componente
							if (vertice.getRotulo().getRotuloSelecionado())	
								vertice.getRotulo().setTexto("");
						}
					}
	
					ares = areaGrafo.getGrafo().getTodasArestas();
					for (i = 0; i < areaGrafo.getGrafo().getNumeroTotalArestas(); i++)
					{
						if (ares[i].getSelecionado())
						{
							//Deletar aresta
							areaGrafo.getGrafo().removerAresta(ares[i]);
						}
						else
						{
							//Manter aresta
							if (ares[i].getRotulo().getRotuloSelecionado())
								ares[i].getRotulo().setTexto("");
						}
					}
					
					areaGrafo.repaint();
					dispose();
				}
			}
		}
		else if (e.getSource() == botaoCancel)
		{
			//Tecla sobre o bot�o Cancel
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				dispose();
			}
		}
	}
	
	/**
	 * M�todo invocado sempre que uma tecla for liberada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyReleased(KeyEvent e){}
	
	/**
	 * M�todo invocado sempre que uma tecla for digitada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyTyped(KeyEvent e){}
}