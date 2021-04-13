//Using Classes,Constructor gia to pws tha simperiferthi to programma se kathe
//Game Loop
//To program exi spasti se mia class me functions oi opoies kanoun "sxedon"->
//-> mia doulia i kathe mia
/* ****** SAMPLE OUTPUT OF PROGRAM *********

Zari [1] 
Thes allo zari?
yes

Zari [2] 
Thes allo zari?
y

Zari [3] 
Thes allo zari?
nο

Zari 2-24 bale: 
19

Number of Tries AI: 20
Average=4.9
CONTRATZ
*/


package xzaria;

import java.util.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//Class Creation
 class FGame 
{
     static private int random,zari,max,min;     // see constructor for initialisation
      
     public FGame(){reset();}                    //Constructor Default Call
     void reset(){zari=0;random=0;max=6;min=1;}  //Declaration of method
     void increment(){max*=2;min++;}
     //User Input gia to Zari
     static public int GetZari()
     {
         Scanner input = new Scanner(System.in);
         System.out.println("\nZari "+min+"-"+max+" bale: ");
         
         zari=input.nextInt();
         int f= valid(zari);
         return f;
     };
     public static int Rand()
     {
         int range = max - min +1;
         random=(int)(Math.random()* range) + min; 
                 return random;
     };
     
     //Routina gia Validation tou number me anaklisis sto GetZari
     static public int valid(int y)
     {
         while(y <min || y > max)   //oi proipothesis tou pexti os pros INPUT
            y=GetZari();
         return y;
     };
}

// ****** MAIN CLASS ******
public class XZaria {
    
    public static void main(String[] args) {
       boolean bPlayAgain=false; 
       do//Main Game Loop
       {
          PlayIntro();
          bPlayAgain=PlayGame();
          //bPlayAgain = AskToPlayAgainOrAddZari();
       }while(bPlayAgain); 
    }
    
    //functions attached stin Main oxi part tou CLASS
 static public void PlayGameZari()
 {
     FGame game = new FGame(); // You need to create instance of same class and then call non-static method
     game.reset();
     //int x = game.GetZari();
     int x;
     boolean k = false;
     int random = game.Rand();
     int counterZaria=1;
     
     do //dinoume edw flexibility sto USER gia N arithmo Zarion
     {
         System.out.println("\nZari ["+counterZaria+"] ");
         game.increment();
         counterZaria++;
         System.out.println("Thes allo zari?");
     }while((AskToPlayAgainOrAddZari()));
     x = game.GetZari();
     int summy =0;
     double avg=0.0;
     int i;
     for(i =1; !k ;i++)
     {
         random = game.Rand();
         summy+=(int)random;
         if(x==random)
         {
             k=true;
             System.out.println("\nNumber of Tries AI: " +i);
             break;
         }
         
     }
     avg=summy/10.0;
     System.out.println("\nAVERAGE: "+avg);
 }
 
 //function attached stin MAIN 
 public static boolean AskToPlayAgainOrAddZari()
 {
     String text="";
     Scanner input = new Scanner(System.in);
     text=input.nextLine();
     return (text.charAt(0) == 'y' || text.charAt(0)=='Y');//epistrefi True me kapio String
     //eksetazontas mono to Prwto Char Letter tou PINAKA String !!!! px NO [N]o || [n]o == FALSE
 }
public static void PlayIntro()
{
          int width = 100;
        int height = 30;

        //BufferedImage image = ImageIO.read(new File("/Users/mkyong/Desktop/logo.jpg"));
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 24));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("2Games", 10, 20);

        //save this image
        //ImageIO.write(image, "png", new File("/users/mkyong/ascii-art.png"));

        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {

                sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");

            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(sb);
}
    
}
public static boolean PlayGame()
{
       boolean again=true;
       do
       {
       System.out.println("\n2 Games Zaria kai Conversions\n");
       System.out.println("\nGAME LOBBY\n"); 
       System.out.println("0 - Exit the program.\n"); 
       System.out.println("1 - PlayZaria.\n");
       System.out.println("2 - Decimal-Binary.\n");
       System.out.println("3 - Binary-Decimal.\n"); 
       System.out.println("Enter choice: "); 
       Scanner input = new Scanner(System.in);
       int choice = input.nextInt();   
        switch (choice)
        {
            
            case 0: System.out.println("GoodBye !");again=false;break;
	    case 1:PlayGameZari(); break;  
            case 2:BinDec(); break;
            case 3:again=DecBin(); break;
            default: {
                System.out.println("\nWanna Chooce something else? ");
            }
        }
       }while(again);
            return again;
}
public static boolean BinDec()
{
    Scanner input = new Scanner(System.in); 
    int decimal = 0; 
    System.out.println("Enter Binary ");
    int binary=input.nextInt();
    int bin=binary;
    int n = 0;  
    while(true){  
      if(binary == 0){  
        break;  
      } else {  
          int temp = binary%10;  
          decimal += temp*Math.pow(2, n);  
          binary = binary/10;  
          n++;  
       } 
    }
    System.out.println("Binary "+bin+" is to Decimal: "+decimal);
    System.out.println("\nPlay Again ");
    return AskToPlayAgainOrAddZari();
}

public static boolean DecBin()
{
    Vector v = new Vector();
    Scanner input = new Scanner(System.in);
    System.out.println("\nENTER DECIMAL to Binary: ");
    int x = input.nextInt();
    int rev=0;
    while(x>0)
    {
        rev=x%2;
        v.add(rev);
        x/=2;
    }
    Collections.reverse(v);
    System.out.println(v);
    System.out.println("");
    System.out.println("\nPlay Again ");
    return AskToPlayAgainOrAddZari();
}

}


