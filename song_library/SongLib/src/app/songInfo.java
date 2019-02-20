/*Claudia Pan - cp728,
 *Yelin Shin - ys521*/
package app;

public class songInfo implements Comparable <songInfo>{
	private String song_name;
	private String song_artist;
	private String song_album;
	private String song_year; 
	
	public songInfo (String song_name, String song_artist, String song_album, String song_year) {
		this.song_name = song_name;
		this.song_artist = song_artist;
		this.song_album = song_album;
		this.song_year = song_year;
	}
	public String getSong_Name () {
		return song_name; 
	}
	public void setSong_Name (String song_name) {
		this.song_name = song_name; 
	}
	
	public String getSong_Artist () {
		return song_artist;
	}
	public void setSong_Artist (String song_artist) {
		this.song_artist= song_artist;
	}
	
	public String getSong_Album() {
		return song_album;
	}
	public void setSong_Album(String song_album) {
		this.song_album = song_album;
	}
	
	public String getSong_Year() {
		return song_year;
	}
	public void setSong_Year(String song_year) {
		this.song_year = song_year; 
	}
	
	public int comapareTo (songInfo x) {
		return this.song_name.toLowerCase().compareTo(x.song_name.toLowerCase());
	}
	@Override
	public int compareTo(songInfo o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
