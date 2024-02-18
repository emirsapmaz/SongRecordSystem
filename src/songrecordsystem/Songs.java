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
public class Songs {
    
    String songName;
    String artist;
    int id;
    String genre;
    int year;

    public Songs(String songName, String artist, int id, String genre, int year) {
        this.songName = songName;
        this.artist = artist;
        this.id = id;
        this.genre = genre;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Song name: |"+songName+"| Artist: |"+artist+"| Id: |"+id+"| Genre: |"+genre+"| Year: |"+year+"|";
    }
    
    
    
    
}
