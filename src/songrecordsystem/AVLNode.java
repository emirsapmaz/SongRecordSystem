/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package songrecordsystem;

/**
 *
 * @author Emir Sapmaz
 */
public class AVLNode<Item> implements Comparable<String>{
    public Item key; 
    public int data;
    public AVLNode left;
    public AVLNode right;
    public int height; 
    
    public AVLNode(int data, Item key){ //key's are information of the songs, data's are index of the array
        this.data= data;
        this.key = key;
        left = null;
        right = null;
        height = 1;
    }

    @Override
    public int compareTo(String t) {
        int result=0;
        String tk=(String)key;
        result=tk.compareToIgnoreCase(t);
        return result;
    }

    
}


