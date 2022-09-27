package SPL_1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Game extends JPanel {
    private static final Color backColor=new Color(0x7F8C8D);
    private static final String fontName="Arial";
    private static final int tileSize=115;
    private static final int tileBorder=16;
    
    private Tile[] tiles;
    int score=0;
    
    
    boolean gameOver=false;
    JButton button;
    JLabel l;
    
    Game() {
        this.setSize(900,700);
        this.setLocation(0, 0);
        this.setFocusable(true);
        
        l=new JLabel ("2048");
        l.setBounds(370,30,150,70);
        l.setFont(new Font ("Bernard MT Condensed", Font.BOLD,70));
        this.add(l);
        
        button=new JButton("Know the Rules");
        button.setSize(120, 55);
        button.setLocation(170,39);
        this.add(button);
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorder(new LineBorder(Color.RED));
        button.setFocusPainted(true);
        button.setFont(new Font ("Arial", Font.BOLD,14));
        button.setCursor(new Cursor (Cursor.HAND_CURSOR));
        
        button.addActionListener(new ActionListener(){
        
            public void actionPerformed(ActionEvent e)
            {
                ImageIcon image2 =new ImageIcon("rule2.png");
                JOptionPane.showMessageDialog(null,"* Use your arrow keys to move the tiles.\n* Tiles with the same number merge into one when they touch.\n* For every turn a random tile will appear in any random place.\n* To restart the game plz press ENTER or ESC key.\n\n\n  Play agian and again to reach your goal score.","Rules are following",JOptionPane.PLAIN_MESSAGE, image2);    
            }
        });
        
        this.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode()==KeyEvent.VK_ESCAPE ||  e.getKeyCode()==KeyEvent.VK_ENTER){
                        reset();
                    }
                if (!movePossible()){
                    gameOver=true;
                }
                if (e.getKeyCode()==KeyEvent.VK_R){
                    ImageIcon image2 =new ImageIcon("rule2.png");
                    JOptionPane.showMessageDialog(null,"* Use your arrow keys to move the tiles.\n* Tiles with the same number merge into one when they touch.\n* For every turn a random tile will appear in any random place.\n* To restart the game plz press ENTER or ESC key.\n\n\n  Play agian and again to reach your goal score.","Rules are following",JOptionPane.PLAIN_MESSAGE, image2);    
            }
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        right();
                        break;
                    case KeyEvent.VK_UP:
                        up();
                        break;       
                    case KeyEvent.VK_DOWN:
                        down();
                        break;
                    }
                repaint();
       
            }     
        });
        reset();
        this.setLayout(null);       
    }
    
    public void reset()
    {
        score =0;
        gameOver=false;
        tiles=new Tile[4*4];
        for (int i=0; i<tiles.length; i++){
            tiles[i]=new Tile();
        }
        addTile();
        addTile();
    }
    
    public void left()
    {
      boolean needTile=false;
      for(int i=0; i<4; i++){
          Tile[] line1=lineNumber(i);   
          Tile[] line2=moveLine(line1);
          Tile[] line3=mergeLine(line2);
          
          setLine(i, line3);
          
          if (!needTile && !compare(line1, line3)){
              needTile=true;
          }
      }
      if (needTile){
         addTile();
      }
    }
   
    public void right()
    {
        tiles=rotateTile(2);
        left();
        tiles=rotateTile(2);    
    }
    
    public void up()
    {
        tiles=rotateTile(3);
        left();
        tiles=rotateTile(1);
    }
    
    public void down()
    {
       tiles=rotateTile(1);
       left();
       tiles=rotateTile(3 );
    }
    
    public void addTile() {
        
         List<Tile> list =emptySpace();
        int target=0;
        if (!emptySpace().isEmpty()){
            int index=(int) (Math.random() * list.size()) % list.size();
            Tile emptyTile=list.get(index);
           
            
            for (int i=0; i<tiles.length; i++){
                int value=tiles[i].value;
                if (value>=target){
                    target=value;
                }
            }
            
            if (target>=32 && target<128){
                emptyTile.value = Math.random() < 0.2 ? 2 : Math.random()<0.6? 4: 16;
            }
            else if (target>=128 && target <1024)
                {
                     emptyTile.value = Math.random() < 0.2 ? 2 : Math.random()<0.4? 4:  Math.random()<0.7? 32:64;
                }
            else if (target>=1024  && target<=2048)
                {
                     emptyTile.value = Math.random() < 0.2 ? 2 : Math.random()<0.3? 4:  Math.random()<0.7? 128:256;
                }
             else if (target>2048)
                {
                     emptyTile.value = Math.random() < 0.2 ? 2 : Math.random()<0.3? 4:  Math.random()<0.7? 512:256;
                }
            
            else{
                 emptyTile.value = Math.random() < 0.6 ? 2 : 4;
            }
        }

    }
    
    public List<Tile> emptySpace(){
        List<Tile> list =new ArrayList<Tile>(16);
        for(Tile t: tiles)
            if (t.isEmpty()){
                list.add(t);
            }
        return list;     
    }
    
    public Tile[] lineNumber(int index){
        Tile[] result=new Tile[4];
        for (int i=0; i<4; i++){
            result[i]=tileAt(i, index);
        }
        return result;
    }
    
    public Tile tileAt(int x, int y){
        return tiles[x + y*4];
    }
    
    public Tile[] moveLine(Tile[] oldLine){
        LinkedList <Tile> list=new LinkedList<Tile>();
        for (int i=0; i<4; i++){
            if(!oldLine[i].isEmpty()){
                list.addLast(oldLine[i]);
            }
        }
        if (list.size()==0){
            return oldLine;
        }
        else{
            Tile[] newLine=new Tile[4];
            completeSize(list,4);
            for (int i=0; i<4 ; i++){
                newLine[i]=list.removeFirst();
            }
            return newLine;      
    }
        
    }
    
    public void completeSize(List<Tile> l, int size){
        while(l.size()!=size){
            l.add(new Tile());
        }
    }
    
    public Tile[] mergeLine(Tile[] oldLine){
        LinkedList<Tile> list= new LinkedList<Tile> ();
        
        for (int i=0; i<4 && !oldLine[i].isEmpty() ; i++){
            int num=oldLine[i].value;
            
            if(i<3 && oldLine[i].value==oldLine[i+1].value){
                num=num+num;
                score=score+num;
                i++;
               
            }
            
            list.add(new Tile(num));
            
            
        }
       
                
        if (list.size()==0){
        return oldLine;
    }
        else {
                completeSize(list, 4);
                return list.toArray(new Tile[4]);
                }
    
        
    }
    
    public void setLine(int index, Tile[] list){
        System.arraycopy(list, 0, tiles, index*4, 4);
    }
    
    public boolean compare (Tile[] line1, Tile[] line2){
        if (line1==line2){
            return true;
        }
        else if (line1.length !=line2.length){
            return false;
        }
        for (int i=0; i<line1.length; i++){
            if (line1[i].value !=line2[i].value){
                return false;
            }
        }
        return false;
    }
    
     private Tile[] rotateTile (int k)
        {
            int a,b,p,q;
            Tile[] newTiles=new Tile[4*4];
            a=3;
            b=3;
            p=-1;
            q=0;
            if (k==1){
                b=0;
                p=0;
                q=1;
            }
            else if (k==3){
                a=0;
                p=0;
                q=-1;
            }
            for (int x=0; x<4; x++)
            {
                for (int y=0; y<4; y++)
                {
                    int newX = (x * p) - (y * q) + a;
	            int newY = (x * q) + (y * p) + b;
		  newTiles[(newX) + (newY) * 4] = tileAt(x, y);
                    
                }
            }
            return newTiles;
         
        }
     
     private boolean isFull() {
	 return emptySpace().size() == 0;
	}
     
     public boolean movePossible(){
         if(!isFull()){
             return true;
         }
         for (int x=0; x<4; x++){
             for (int y=0; y<4; y++){
                 Tile t=tileAt(x,y);
                 if ((x<3 && t.value==tileAt(x+1, y).value) || (y<3 && t.value==tileAt(x,y+1).value))
                 {
                     return true;
                 }
             }
         }
         return false;
     }
     
   

    public void paint(Graphics g){
        super.paint(g);
		
        g.setColor(backColor);
        g.fillRoundRect(167, 117, 537, 537, 10, 10);
      
        g.setColor(backColor);
        g.fillRoundRect(578, 35, 122, 60, 10, 10);
        
        g.setColor(new Color(12, 8, 1));
        g.setFont(new Font(fontName, Font.BOLD, 16));	
        g.drawString("SCORE", 608, 57);
         
        for (int y=0; y<4; y++){
            for (int x=0; x<4; x++) {
                drawTile(g, tiles[x+ y*4], x, y);
            }
        }       
    }
    
    private void drawTile(Graphics g, Tile tile, int x, int y){
        Graphics2D g2d=((Graphics2D) g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        
        int value = tile.value;
        int xPoint = point(x)+165; 
        int yPoint = point(y)+115;
        
        g.setColor(tile.tileBackground());
        g.fillRoundRect(xPoint, yPoint, tileSize, tileSize, 15, 15);
        
        int size = value < 100 ? 40 : value < 1024 ? 32 : value < 2048? 26: 22;
	Font font = new Font(fontName, Font.BOLD, size);
	g.setFont(font);
        
        String s = String.valueOf(value);
	final FontMetrics fm = getFontMetrics(font);
        
        final int width = fm.stringWidth(s);
        final int height = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
        
        if (value != 0){
            
            g.setColor(new Color(39, 55, 70));
	    g.drawString(s, xPoint + (tileSize - width) / 2, yPoint + tileSize - (tileSize - height) / 2 - 2);
        }
        
        if ( gameOver) { 
	    g.setColor(new Color(255, 255, 255, 30));
	    g.fillRect(167, 117,537, 537);
                        
	    g.setColor(new Color(249, 35, 10));
	    g.setFont(new Font(fontName, Font.BOLD, 48));
	    g.drawString("Game over!", 310, 250);
		
            g.setColor(new Color(23,32,42));
            g.setFont(new Font(fontName, Font.BOLD, 26));
            g.drawString("Press ENTER or ESC to play again", 220, 350);
        }
        
         g.setColor(new Color(253, 254, 254));
	 g.setFont(new Font(fontName, Font.BOLD, 22));
	 g.drawString("" + score, 610, 82);
       
    }
    private int point(int x) {
         return x * (tileBorder + tileSize) + tileBorder;
	}

}

