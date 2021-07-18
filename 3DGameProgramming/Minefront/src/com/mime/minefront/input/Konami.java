package com.mime.minefront.input;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.TreeMap;

public class Konami {

	static public int[] code = 
        {KeyEvent.VK_UP, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_B};
    static public Map<Integer, Integer>[] graph;
    static private int currentNode = 0;
    
//    public static void main(String args[]) {
//        //Create graph
//        graph = generateSequenceMap(code);
//
//        //Call checkKonami(key) whenever a key is pressed
//    }

    static public boolean checkKonami(int keyPressed) {
        Integer nextNode = graph[currentNode].get(keyPressed);

        //Set currentNode to nextNode or to 0 if no matching sub-sequence exists
        currentNode = (nextNode==null ? 0 : nextNode);

        return currentNode == code.length-1;
    }


    static public Map<Integer, Integer>[] generateSequenceMap(int[] sequence) {

        //Create map
        Map<Integer, Integer>[] graph = new Map[sequence.length];
        for(int i=0 ; i<sequence.length ; i++) {
            graph[i] = new TreeMap<Integer,Integer>();
        }

        //i is delta
        for(int i=0 ; i<sequence.length ; i++) {
            loop: for(int j=i ; j<sequence.length-1 ; j++) {
            if(sequence[j-i] == sequence[j]) {
                System.out.println("If at Node "+j+" you give me seq["+(j-i+1) 
                        + "] OR " + (sequence[j-i+1]) + " , goto Node " + (j-i+1));

                //Ensure that the longest possible sub-sequence is recognized
                Integer value = graph[j].get(sequence[j-i+1]);
                if(value == null || value < j-i+1)
                    graph[j].put(sequence[j-i+1], j-i+1);
            }
            else
                break loop;
            }
        }
        return graph;
    }
    
}
