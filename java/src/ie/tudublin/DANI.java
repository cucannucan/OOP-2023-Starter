//Ceren Ucan D21124013
//This lab test is about creating a sonnet by using the words from the shakespere.txt file.
package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;

public class DANI extends PApplet {
	ArrayList<Word> model = new ArrayList<Word>();
	String[] sonnet;

	public void settings() {
		size(1024, 1000);
	}

	public void setup() {
		colorMode(HSB);
		loadFile("java/data/shakespere.txt");
		printModel();
	}

	public void keyPressed() {
		if (key == ' ') {
			sonnet = writeSonnet();
			for (int i = 0; i < sonnet.length; i++) {
				System.out.println(sonnet[i]);
			}
			redraw();
		}
	}

	public void draw() {
		background(0);
		fill(255);
		noStroke();
		textSize(20);
		textAlign(CENTER, CENTER);
		if (sonnet != null) {
			float x = width / 2;
			float y = height / 2;
			float lineHeight = textAscent() + textDescent();
			for (int i = 0; i < sonnet.length; i++) {
				text(sonnet[i], x, y);
				y += lineHeight;
			}
		}
	}

	// This method takes a file name as input and loads the text from that file into memory. 
	public void loadFile(String filename) {
		String[] lines = loadStrings(filename);
		for (String line : lines) {
			String[] words = split(line, ' ');
			for (int i = 0; i < words.length; i++) {
				String w = words[i].replaceAll("[^\\w\\s]", "").toLowerCase();
				if (w.length() == 0) {
					continue;
				}
				Word word = findWord(w);
				if (word == null) {
					word = new Word(w);
					model.add(word);
				}
				if (i < words.length - 1) {
					String nextW = words[i + 1].replaceAll("[^\\w\\s]", "").toLowerCase();
					if (nextW.length() == 0) {
						continue;
					}
					word.addFollow(nextW);
				}
			}
		}
	}

	//This method searches through the model object for a Word object with the given string value. If it finds a matching Word object, it returns that object; otherwise, it returns null.
	public Word findWord(String str) {

		for (Word word : model) {
			if (word.getWord().equals(str)) {
				return word;
			}
		}
		return null;
	}

	//This method prints out the model object by iterating over each Word object in the list and printing its word value and list of followers.
	public void printModel() {
		for (Word word : model) {
			System.out.print(word.getWord() + ": ");
			for (Follow follow : word.getFollows()) {
				System.out.print(follow.getWord() + "(" + follow.getCount() + ") ");
			}
			System.out.println();
		}
	}

	//his method generates a sonnet using the model object by randomly selecting a starting Word object from the list and then iterating through its list of followers to choose the next word in each line of the sonnet. It repeats this process for 14 lines to generate a full sonnet, which it returns as an array of strings.
	public String[] writeSonnet() {
		String[] sonnet = new String[14];
		for (int i = 0; i < sonnet.length; i++) {
			String line = "";
			Word currentWord = model.get((int) random(model.size()));
			line += currentWord.getWord();
			for (int j = 0; j < 7; j++) {
				ArrayList<Follow> follows = currentWord.getFollows();
				if (follows.size() == 0) {
					break;
				}
				Follow follow = follows.get((int) random(follows.size()));
				line += " " + follow.getWord();
				currentWord = findWord(follow.getWord());
			}
			sonnet[i] = line;
		}
		return sonnet;
	}
}

//This is a helper class that represents a single follower word and its count in the Word object's list of followers.
class Follow {
	private String word;
	private int count;

	public Follow(String word) {
		this.word = word;
		this.count = 1;
	}

	public String getWord() {
		return this.word;
	}

	public int getCount() {
		return this.count;
	}

	public void incrementCount() {
		this.count++;
	}

	public String toString() {
		return word + "(" + count + ")";
	}
}

//This is a class that represents a single word and its list of followers in the model object. It has methods to add a follower to its list, retrieve its list of followers, and convert the Word object to a string for printing.
class Word {
	private String word;
	private ArrayList<Follow> follows;

	public Word(String word) {
		this.word = word;
		this.follows = new ArrayList<Follow>();
	}

	public String getWord() {
		return this.word;
	}

	public ArrayList<Follow> getFollows() {
		return this.follows;
	}

	public void addFollow(String word) {
		for (Follow follow : follows) {
			if (follow.getWord().equals(word)) {
				follow.incrementCount();
				return;
			}
		}
		follows.add(new Follow(word));
	}

	public String toString() {
		String str = word + ": ";
		for (Follow follow : follows) {
			str += follow.toString() + " ";
		}
		return str;
	}
}