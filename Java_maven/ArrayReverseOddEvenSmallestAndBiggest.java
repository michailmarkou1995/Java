
package javaapplication12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class test{static int x=5;}
public class JavaApplication12 {
    public static void main(String[] args) {

               ArrayList<Integer> AskisiReverse4=new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
               Integer []Askisi4=new Integer[20];
               int [] askisi1_Odd=new int[10];
               int []askisi1_Even=new int[10];
               int max=0,min=0,min1=0,summary_not=0;
               int counters=0,counters1=0;
               Object[] arr = AskisiReverse4.toArray(); 
               for (int i =1; i<=AskisiReverse4.size(); i++)
               {
                   for (int j=AskisiReverse4.size()-i; j<AskisiReverse4.size();)
                   {
                       Askisi4[j]=(int)arr[i-1];
                       if (j==0)
                       System.out.println("array REVERSE "+Arrays.toString(Askisi4));
                        j=arr.length+1;
                   }
               }
              ListIterator<Integer> 
                iterator = AskisiReverse4.listIterator(); 
              
              // ASKISI 1
for (var i : AskisiReverse4) {
          if (i%2==0){
              askisi1_Even[counters++]=i;
          }  
          else if (i%2==1){
              askisi1_Odd[counters1++]=i;
          }        
          if(counters ==AskisiReverse4.size()/2 && counters1==(AskisiReverse4.size()/2)){
          System.out.println("EVEN "+ Arrays.toString(askisi1_Even)+"\n ODD " 
                  +Arrays.toString(askisi1_Odd));
         
          }
        }
//ASKISI 2+3
counters=0;
min=min1=Integer.MAX_VALUE;
            while(iterator.hasNext())
            {
                int current=iterator.next();
                if((int)arr[current-1]<min)
                {
                    min1=min;
                    min=(int)arr[current-1];
                }
                else if ((int)arr[current-1] < min1 && (int)arr[current-1] != min) 
                min1 = (int)arr[current-1]; 
                if ((int)arr[current-1] >= max){
                    max=(int)arr[current-1];
                }
                 if((int)arr[current-1] <= min)
                    min=(int)arr[current-1];
                 summary_not+=(int)arr[current-1];
            }
            summary_not-=min+max;
            if (min1 == Integer.MAX_VALUE) 
            System.out.println("There is no second" + 
                               "smallest element"); 
            else
            System.out.println("The smallest element is " + 
                               min + " and second Smallest" + 
                               " element is " + min1); 
            System.out.println("Max is "+max +" Summary not first and last " +summary_not);
    }
}
