/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram.componente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Andre
 */
public class VerticeAtor extends Vertice
{
    
    public VerticeAtor(int codigo, int x, int y, int largura, int altura)
    {
        super(codigo, x, y, largura, altura);
    }

    public VerticeAtor(int codigo, int x, int y, int largura, int altura, String nome) {
        super(codigo, x, y, largura, altura);
        Rotulo rotulo = new Rotulo();
        rotulo.setTexto(nome);
        this.setRotulo(rotulo);
        this.setDescricao("Ator - "+nome);
        
    }
        
    @Override
    public void desenharVertice(Graphics desenho, Component componente) {
        //Desenha o fundo do v�rtice...
	desenho.setColor(Color.white);
	desenho.fillRect(getX(), getY(), getLargura() , getAltura());
        
        
        desenho.setColor(Color.black);
        
        //CABEÇA
        desenho.drawOval(getX()+(getLargura()/4), getY(), getLargura()/2 , getAltura()/4);
        
        //BRAÇOS
        desenho.drawLine(getX(), getY()+getAltura()/3, getX()+getLargura() , getY()+getAltura()/3);
        
        //CORPO
        desenho.drawLine(getX()+(getLargura()/4)+(getLargura()/4), getY()+(getAltura()/4), getX()+(getLargura()/4)+(getLargura()/4), getY()+(getAltura()*2/3));
        
        //PERNA DIREITA
        desenho.drawLine(getX()+(getLargura()/4)+(getLargura()/4), getY()+(getAltura()*2/3), getX() , getY()+getAltura());
        
        //PERNA ESQUERDA
        desenho.drawLine(getX()+(getLargura()/4)+(getLargura()/4), getY()+(getAltura()*2/3), getX()+getLargura(), getY()+getAltura());

	

	
    }

    @Override
    public boolean coordenadaPertenceVertice(int x, int y) {
	Ellipse2D.Float area = new Ellipse2D.Float (getX(), getY(), getLargura(), getAltura());	
	return area.contains(x, y);
    }
}