
package SPL_1;

import java.awt.Color;

public class Tile {
    int value;
    
    public Tile(){
        
    }
    
    public Tile (int number){
        value=number;
    }
    
    public boolean isEmpty(){
        return value==0;
    }
    
    public Color tileBackground(){
        
        switch(value){
            
            case 0: 
                return new Color(0xcdc1b4);
            case 2:
                return new Color(0xF7DC6F);
            case 4:
                return new Color(0xF5b7b5); 
            case 8:
                return new Color (0xA2D9CE);
            case 16:
                return new Color (0xF6DDCC );
            case 32:
                return new Color (0xc566f5);
            case 64:
                return new Color(0xf65e3b);
            case 128:
                return new Color(0xb3b4e4);
            case 256:
                return new Color(0xedcc61);
            case 512:
                return new Color(0xedc850);
            case 1024:
                return new Color(0x7D3C98);
            case 2048:
                return new Color(0xedc22e);
            case 4096:
                return new Color(0xd6eaf8);
            case 8192:
                return new Color(0xff6666);
            case 16384:
                return new Color(0xff3333);
            case 32768:
                return new Color(0x66b2ff);
          
        }
        return new Color(0xB7950B);
        
    }
    
}
