/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package songrecordsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Emir Sapmaz
 */
public class SongRecordSystem {
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner inputsong = new Scanner(new File("songs.txt"));
        Scanner sngcount = new Scanner(new File("songs.txt"));
        AVLTree<String> songNameTree = new AVLTree<>();
        AVLTree<String> ArtistTree = new AVLTree<>();
        AVLTree<Integer> IdTree = new AVLTree<>();
        
        Songs[] songs;
        int songCount=0;
        while(sngcount.hasNextLine()){//finding the number of lines in the text file to get a size for the array
            sngcount.nextLine();
            songCount++;
        }
        songs = new Songs[songCount];
        for (int i = 0; i < songCount; i++) {//reading the text file
            inputsong.useDelimiter(";");
            String songName=inputsong.next();
            String artist=inputsong.next();
            int id=inputsong.nextInt();
            String genre=inputsong.next();
            int year=Integer.parseInt((inputsong.nextLine()).substring(1));//because of the line is ending and there is no ; at the end so delimeter don't work and program gives ";year", by using sub string we are getting only "year"
            songs[i]=new Songs(songName, artist, id, genre, year);
            songNameTree.insertS(i, songName);
            ArtistTree.insertS(i, artist);
            IdTree.insertI(i, id);
            System.out.println(songs[i].toString());
        }
        
        Scanner input = new Scanner(System.in);
        int menu;
        int index=-1;
        do{
            System.out.println("#################################################################");
            System.out.println("Which task you want to do?\n" +
                            "1- Search 1(search songs using the name, artist, or ID fields(finding the index))\n" +
                            "2- Search 3(search ranges)\n" +
                            "3- Printing desired field of the tree(You need the find an index first!)\n" +
                            "4- Print all of the trees\n" +
                            "5- Delete from all trees\n" +
                            "0- Exit");
            System.out.println("#################################################################"); 
            menu=input.nextInt();
            switch(menu){
                case 1: 
                    int menu3;
                    do{
                        System.out.println("*****************************************************************");
                        System.out.println("Enter the tree number to do your search.\n"+
                                        "1- Song name tree\n"+
                                        "2- Artist name tree\n"+
                                        "3- Id tree\n"+
                                        "0- Exit");
                        System.out.println("*****************************************************************");
                       menu3=input.nextInt();
                       switch(menu3){
                            case 1:
                                System.out.println("Enter the name of the song.");
                                input.nextLine();
                                String user1 = input.nextLine();
                                if(songNameTree.searchString(songNameTree.root,user1 )!=null){
                                    index=songNameTree.searchString(songNameTree.root,user1 ).data;
                                    System.out.println("Index has been found in the tree please go back to menu. \n"+songs[index].toString());
                                }else
                                    System.out.println("Index has NOT been found in the tree.");
                                break;
                            case 2:
                                System.out.println("Enter the name of the artist.");
                                input.nextLine();
                                String user2 = input.nextLine();
                                if(ArtistTree.searchString(ArtistTree.root,user2 )!=null){
                                    index=ArtistTree.searchString(ArtistTree.root,user2 ).data;
                                    System.out.println("Index has been found in the tree please go back to menu. \n"+songs[index].toString());
                                }else
                                    System.out.println("Index has NOT been found in the tree.");
                                break;
                            case 3:
                                System.out.println("Enter the id of the song.");
                                int user3 = input.nextInt();
                                if(IdTree.searchInt(IdTree.root,user3 )!=null){
                                    index=IdTree.searchInt(IdTree.root,user3 ).data;
                                    System.out.println("Index has been found in the tree please go back to menu. \n"+songs[index].toString());
                                }else
                                    System.out.println("Index has NOT been found in the tree.");
                                break;
                            case 0:
                                System.out.println("Continuing with the main menu !");
                                break;
                            default:
                                System.out.println("Please enter an appropriate number to continue!"); 
                       }
                    }while(menu3!=0);

                    break;
                case 2:
                    System.out.println("Enter an LOWER bound for the search.");
                    int lowBound=input.nextInt();
                    System.out.println("Enter an UPPER bound for the search.");
                    int upBound=input.nextInt();
                    if(lowBound>upBound){
                        System.out.println("Low bound should be lesser than upper bound. Please enter appropriate numbers to continue!");
                        break;
                    }
                    java.util.Stack<Integer> a = new java.util.Stack<Integer>();
                    a=IdTree.searchWithBoundaries(lowBound,upBound);
                    if(a.empty())
                        System.out.println("There is no matching id from the trees.");
                    else{
                        System.out.println("Songs between ID ("+lowBound+" to "+upBound+") is:");
                            while(!a.isEmpty()){
                                System.out.println(songs[a.pop()].toString());
                            }
                    }
                    break;

                case 3:
                    if(index!=-1){
                        int menu2;
                        do{
                           System.out.println("*****************************************************************");
                            System.out.println("Which task you want to do?\n" +
                            "1- Print song name\n"+
                            "2- Print artist\n" +
                            "3- Print id\n" +
                            "4- Print genre\n" +
                            "5- Print year\n" +
                            "0- Exit");
                            System.out.println("*****************************************************************"); 
                           menu2=input.nextInt();
                           switch(menu2){
                                case 1:
                                    System.out.println(songs[index].songName);
                                    break;
                                case 2:
                                    System.out.println(songs[index].artist);
                                    break;
                                case 3:
                                    System.out.println(songs[index].id);
                                    break;
                                case 4:
                                    System.out.println(songs[index].genre);
                                    break;
                                case 5:
                                    System.out.println(songs[index].year);
                                    break;
                                case 0:
                                    System.out.println("Continuing with the main menu !");
                                    break;
                                default:
                                    System.out.println("Please enter an appropriate number to continue!");    
                           }
                        }while(menu2!=0);
                    }else
                        System.out.println("No assigned index yet, use search 1 to find an index");
                    break;
                    
                case 4: 
                    if(IdTree.root==null)
                        System.out.println("The tree is empty, you can't do any traversals!");
                    else{
                        int menu1;
                        do{
                            System.out.println("*****************************************************************");
                            System.out.println("Which traversal type you want to do?\n" +
                            "1- Preorder traversal\n"+
                            "2- Inorder Traversal\n" +
                            "3- Level Order Traversal\n" +
                            "0- Exit");
                            System.out.println("*****************************************************************");
                            menu1=input.nextInt();
                            switch(menu1){
                                case 1:
                                    System.out.println("Preorder Traversal of the trees are: \n");
                                    songNameTree.preOrder(songNameTree.root);
                                    System.out.println("\n-----------------------------------------------------------------");
                                    ArtistTree.preOrder(ArtistTree.root);
                                    System.out.println("\n-----------------------------------------------------------------");
                                    IdTree.preOrder(IdTree.root);
                                    System.out.println("");
                                    break;
                                case 2:
                                    System.out.println("Inorder Traversal of the trees are: \n");
                                    songNameTree.traverseInOrder(songNameTree.root);
                                    System.out.println("\n-----------------------------------------------------------------");
                                    ArtistTree.traverseInOrder(ArtistTree.root);
                                    System.out.println("\n-----------------------------------------------------------------");
                                    IdTree.traverseInOrder(IdTree.root);
                                    System.out.println("");
                                    break;
                                case 3:
                                    System.out.println("Level Order Traversal of the trees are: \n");
                                    songNameTree.traverseLevelOrder(songNameTree.root);
                                    System.out.println("\n-----------------------------------------------------------------");
                                    ArtistTree.traverseLevelOrder(ArtistTree.root);
                                    System.out.println("\n-----------------------------------------------------------------");
                                    IdTree.traverseLevelOrder(IdTree.root);
                                    System.out.println("");
                                    break;
                                case 0:
                                    System.out.println("Continuing with the main menu !");
                                    break;
                                default:
                                    System.out.println("Please enter an appropriate number to continue!");
                           }
                        }while(menu1!=0);
                    }
                    break;
                    
                case 5:
                    System.out.println("Enter an id to delete from all of the trees:");
                    int userid=input.nextInt();
                    if(IdTree.searchInt(IdTree.root, userid)==null)
                        System.out.println("ID is not found in the trees, please enter an valid id which is from the tree.");
                    else{
                     String usera=songs[IdTree.searchInt(IdTree.root, userid).data].artist;
                     String users=songs[IdTree.searchInt(IdTree.root, userid).data].songName;
                     ArtistTree.deleteS(usera);
                     songNameTree.deleteS(users);
                     IdTree.deleteI(userid);
                     System.out.println("Entered id "+userid+" has been deleted from all of the trees.");
                     }
                    break;
                case 0:
                    System.out.println("See you !");
                    break;
                default:
                    System.out.println("Please enter an appropriate number to continue!");
                   
            }
            
        }while(menu!=0);
        
        
        
        
    
        
    }

    
    
}
