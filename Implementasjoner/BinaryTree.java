/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearch;

/**
 *
 * @author eivind
 */
public class BinaryTree {

    Node root;

    public void addNode(int key, String name) {

        Node newNode = new Node(key, name);

        if (root == null) {

            root = newNode;

        } else {

            Node focusNode = root;

            Node parent;

            while (true) {

                parent = focusNode;

                if (key < focusNode.key) {

                    focusNode = focusNode.leftChild;
                    
                    if(focusNode == null) {
                    
                        parent.leftChild = newNode; 
                        return;
                    }
                } else {
                
                   focusNode = focusNode.rightChild; 
                   if(focusNode == null) {
                    
                        parent.rightChild = newNode; 
                        return;
                    }
                }
            }
        }

    } 
    
    public void inOrderTraverseTree(Node focusNode) {
    
        if(focusNode != null){
        
            inOrderTraverseTree(focusNode.leftChild);
            
            System.out.println(focusNode); 
            
            inOrderTraverseTree(focusNode.rightChild);
        }
        
    } 
    
    public void preOrderTraverseTree(Node focusNode){
    
        if(focusNode != null){
            
            System.out.println(focusNode); 
            
            inOrderTraverseTree(focusNode.leftChild); 
            
            inOrderTraverseTree(focusNode.rightChild);
    }
    
    }
    

    public static void main(String[] args) {
        
        BinaryTree theTree = new BinaryTree();  
        
        System.out.println("Sortert preorder: ");
        
        theTree.preOrderTraverseTree(theTree.root);
        
        theTree.addNode(100, "Boss"); 
        theTree.addNode(85, "Vice Press"); 
        theTree.addNode(60, "Office Manager"); 
        theTree.addNode(40, "Secretary"); 
        theTree.addNode(30, "Sales Manager"); 
        theTree.addNode(20, "Salesman");
        
        System.out.println("Sortert inorder: ");
        
        theTree.addNode(100, "Boss"); 
        theTree.addNode(85, "Vice Press"); 
        theTree.addNode(60, "Office Manager"); 
        theTree.addNode(40, "Secretary"); 
        theTree.addNode(30, "Sales Manager"); 
        theTree.addNode(20, "Salesman");
        
        theTree.inOrderTraverseTree(theTree.root);  
        
        
        
        
    }

}

class Node {

    int key;
    String name;

    Node leftChild;
    Node rightChild;

    public Node(int key, String name) {

        this.key = key;
        this.name = name;

    }

    public String toString() {

        return name + "has a key: " + key;

    }

}
