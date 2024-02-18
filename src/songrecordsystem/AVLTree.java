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
public class AVLTree<Item> {
    AVLNode<Item> root;

    public AVLTree() {
        root = null;
    }

    public int height(AVLNode<Item> d) {
        if (d == null) {
            return 0;
        } else {
            return d.height;
        }
    }

    //rotate focus. to right. replace it with left child
    public AVLNode<Item> rotateMyLeft(AVLNode<Item> focus) {
        AVLNode<Item> k = focus.left;
        focus.left = k.right;
        k.right = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }

    //rotate focus left, replace it with right child
    public AVLNode<Item> rotateMyRight(AVLNode<Item> focus) {
        AVLNode<Item> k = focus.right;
        focus.right = k.left;
        k.left = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }

    public AVLNode<Item> doubleRotateLeftSide(AVLNode focus) {
        focus.left = rotateMyRight(focus.left);
        return rotateMyLeft(focus);
    }

    public AVLNode<Item> doubleRotateRightSide(AVLNode focus) {
        focus.right = rotateMyLeft(focus.right);
        return rotateMyRight(focus);
    }

    // Get Balance factor of node focus
    int getBalance(AVLNode<Item> focus) {
        if (focus == null) {
            return 0;
        }
        return height(focus.left) - height(focus.right);
    }

    public AVLNode<Item> insertInt(AVLNode focus, int data, int key) {
        if (focus == null) {
            focus = new AVLNode(data, key);
        } else if (key < (int)focus.key) {
            focus.left = insertInt(focus.left, data, key);
            if (Math.abs(getBalance(focus))==2 ) {
                if (key < (int)focus.left.key) {
                    focus = rotateMyLeft(focus);
                } else {
                    focus = doubleRotateLeftSide(focus);
                }
            }
        } else if (key > (int)focus.key) {
            focus.right = insertInt(focus.right, data, key);
            if (Math.abs(getBalance(focus))==2) {
                if (key > (int)focus.right.key) {
                    focus = rotateMyRight(focus);
                } else {
                    focus = doubleRotateRightSide(focus);
                }
            }
        } else {
            // key is equal to focus.key, update data
            focus.data = data;
        }

        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        return focus;
    }
    public AVLNode<Item> insertString(AVLNode focus, int data, String key) {
        if (focus == null) {
            focus = new AVLNode(data, key);
        } else if (focus.compareTo(key)>0) {
            focus.left = insertString(focus.left, data, key);
            if (getBalance(focus) == 2) {
                if (focus.left.compareTo(key)>0) {
                    focus = rotateMyLeft(focus);
                } else {
                    focus = doubleRotateLeftSide(focus);
                }
            }
        } else if (focus.compareTo(key)<0) {
            focus.right = insertString(focus.right, data, key);
            if (getBalance(focus) == 2) {
                if (focus.right.compareTo(key)<0) {
                    focus = rotateMyRight(focus);
                } else {
                    focus = doubleRotateRightSide(focus);
                }
            }
        } else {
            // key is equal to focus.key, update data
            focus.data = data;
        }

        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        return focus;
    }

    public void insertI(int data, int key) {
        root = insertInt(root, data, key);
    }
    public void insertS(int data,String key){
        root=insertString(root,data,key);
    }

    public void traverseLevelOrder(AVLNode focus) {
        if (focus == null) {
            focus = root; // if null is passed. print whole tree
        }
        java.util.LinkedList<AVLNode> que = new java.util.LinkedList<AVLNode>();
        que.add(focus);
        while (!que.isEmpty()) {
            AVLNode d = que.removeFirst();
            if (d.left != null) {
                que.addLast(d.left);
            }
            if (d.right != null) {
                que.addLast(d.right);
            }
            System.out.print(d.key+", ");
        }
    }

    public AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    public AVLNode deleteNodeInt(AVLNode focus, int key) {
        // STEP 1: PERFORM STANDARD BST DELETE 
        if (focus == null) {
            return focus;
        }

        // If the key to be deleted is smaller than 
        // the root's key, then it lies in left subtree 
        if (key < (Integer)focus.key) {
            focus.left = deleteNodeInt(focus.left, key);
        } // If the key to be deleted is greater than the 
        // root's key, then it lies in right subtree 
         else if (key > (Integer)focus.key) {
            focus.right = deleteNodeInt(focus.right, key);
        } // if key is same as root's key, then this is the node 
        // to be deleted 
        else {

            // node with only one child or no child 
            if ((focus.left == null) || (focus.right == null)) {
                AVLNode temp = null;
                if (focus.left==null) {
                    temp = focus.right;
                } else { // null == focus.right
                    temp = focus.left;
                }
                // No child case 
                if (temp == null) {
                    temp = focus;
                    focus = null;
                } else // One child case 
                {
                    focus = temp; // Copy the contents of 
                }                                // the non-empty child 
            } else {

                // node with two children: Get the inorder 
                // successor (smallest in the right subtree) 
                AVLNode temp = minValueNode(focus.right);

                // Copy the inorder successor's data to this node 
                focus.key = temp.key;

                // Delete the inorder successor 
                focus.right = deleteNodeInt(focus.right, (Integer)temp.key);
            }
        }

        // If the tree had only one node then return 
        if (focus == null) {
            return focus;
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE 
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether 
        // this node became unbalanced) 
        int balance = getBalance(focus);

        // If this node becomes unbalanced, then there are 4 cases 
        // Left Left Case 
        if (balance > 1 && getBalance(focus.left) >= 0) {
            return rotateMyLeft(focus);
        }

        // Left Right Case 
        if (balance > 1 && getBalance(focus.left) < 0) {
            focus.left = rotateMyRight(focus.left);
            return rotateMyLeft(focus);
        }

        // Right Right Case 
        if (balance < -1 && getBalance(focus.right) <= 0) {
            return rotateMyRight(focus);
        }

        // Right Left Case 
        if (balance < -1 && getBalance(focus.right) > 0) {
            focus.right = rotateMyLeft(focus.right);
            return rotateMyRight(focus);
        }

        return focus;
    }//when using this method, implement as : root=deletenodeint ... since we have deletI method you dont need to do that.
    
    public void deleteI(int key){//call this
        root=deleteNodeInt(root, key);
    }
    public void deleteS(String key){
        root=deleteNodeString(root, key);
    }
    
    public AVLNode deleteNodeString(AVLNode focus, String key) {
        // STEP 1: PERFORM STANDARD BST DELETE 
        if (focus == null) {
            return focus;
        }

        // If the key to be deleted is smaller than 
        // the root's key, then it lies in left subtree 
        if (focus.compareTo(key)>0) {
            focus.left = deleteNodeString(focus.left, key);
        } // If the key to be deleted is greater than the 
        // root's key, then it lies in right subtree 
        else if (focus.compareTo(key)<0) {
            focus.right = deleteNodeString(focus.right, key);
        } // if key is same as root's key, then this is the node 
        // to be deleted 
        else {

            // node with only one child or no child 
            if ((focus.left == null) || (focus.right == null)) {
                AVLNode temp = null;
                if (null == focus.left) {
                    temp = focus.right;
                } else { // null == focus.right
                    temp = focus.left;
                }
                // No child case 
                if (temp == null) {
                    temp = focus;
                    focus = null;
                } else // One child case 
                {
                    focus = temp; // Copy the contents of 
                }                                // the non-empty child 
            } else {

                // node with two children: Get the inorder 
                // successor (smallest in the right subtree) 
                AVLNode temp = minValueNode(focus.right);

                // Copy the inorder successor's data to this node 
                focus.key = temp.key;

                // Delete the inorder successor 
                focus.right = deleteNodeString(focus.right, (String)temp.key);
            }
        }

        // If the tree had only one node then return 
        if (focus == null) {
            return focus;
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE 
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether 
        // this node became unbalanced) 
        int balance = getBalance(focus);

        // If this node becomes unbalanced, then there are 4 cases 
        // Left Left Case 
        if (balance > 1 && getBalance(focus.left) >= 0) {
            return rotateMyLeft(focus);
        }

        // Left Right Case 
        if (balance > 1 && getBalance(focus.left) < 0) {
            focus.left = rotateMyRight(focus.left);
            return rotateMyLeft(focus);
        }

        // Right Right Case 
        if (balance < -1 && getBalance(focus.right) <= 0) {
            return rotateMyRight(focus);
        }

        // Right Left Case 
        if (balance < -1 && getBalance(focus.right) > 0) {
            focus.right = rotateMyLeft(focus.right);
            return rotateMyRight(focus);
        }

        return focus;
    }
    
    public AVLNode searchInt(AVLNode focus,int key){
        if(focus==null)
            return null;
        if((int)focus.key==key)
            return focus;
        else if(key<(int)focus.key)
            return searchInt(focus.left, key);
        else
            return searchInt(focus.right, key);
    }
    
    public AVLNode searchString(AVLNode focus,String key){
        if(focus==null)
            return null;
        if(focus.compareTo(key)==0)
            return focus;
        else if(focus.compareTo(key)>0)
            return searchString(focus.left, key);
        else
            return searchString(focus.right, key);
    }
    
    public void preOrder(AVLNode focus){
        if (focus!=null){ 
            System.out.print(focus.key + ", "); 
            preOrder(focus.left); 
            preOrder(focus.right); 
        } 
    } 
    
    public void traverseInOrder(AVLNode focus){
        if(focus.left!=null)
            traverseInOrder(focus.left);
        System.out.print(focus.key+", ");
        if(focus.right!=null)
            traverseInOrder(focus.right);
    }
    
    public java.util.Stack<Integer> searchWithBoundaries(int lower,int upper){
        java.util.Stack<Integer> a = new java.util.Stack<Integer>();
        while(upper!=lower-1){
            if(searchInt(root, upper)!=null)
                a.push(searchInt(root, upper).data);
            upper--;
        }
        return a;
        
    }
    
}
