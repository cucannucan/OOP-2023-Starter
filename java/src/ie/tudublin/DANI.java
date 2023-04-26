package ie.tudublin;

import java.util.ArrayList;
import java.util.ArrayList;


import processing.core.PApplet;

public class DANI extends PApplet {

	private ArrayList<Word> model;

  public DANI() {
    model = new ArrayList<Word>();
  }

  public void loadFile(String filename) {
    String[] lines = loadStrings(filename);
    for (String line : lines) {
      String[] words = split(line, ' ');
      for (int i = 0; i < words.length - 1; i++) {
        String w = words[i].replaceAll("[^\\w\\s]","");
        String f = words[i+1].replaceAll("[^\\w\\s]","");
        w = w.toLowerCase();
        f = f.toLowerCase();
        Word word = findWord(w);
        if (word == null) {
          word = new Word(w);
          model.add(word);
        }
        word.addFollow(f);
      }
    }
  }

  public Word findWord(String word) {
    for (Word w : model) {
      if (w.getWord().equals(word)) {
        return w;
      }
    }
    return null;
  }

  public void printModel() {
    for (Word w : model) {
      println(w.toString());
    }
  }

  public String writeSonnet() {
    String sonnet = "";
    for (int i = 0; i < 14; i++) {
      String line = "";
      Word w = model.get((int) random(model.size()));
      line += w.getWord();
      for (int j = 0; j < 7; j++) {
        ArrayList<Follow> follows = w.getFollows();
        if (follows.isEmpty()) {
          break;
        }
        Follow f = follows.get((int) random(follows.size()));
        line += " " + f.getWord();
        w = findWord(f.getWord());
      }
      sonnet += line + "\n";
    }
    return sonnet;
  }
	

	public void settings() {
		size(1000, 1000);
		//fullScreen(SPAN);
	}

    String[] sonnet;

   
	public void setup() {
		colorMode(HSB);

       
	}

	public void keyPressed() {

	}

	float off = 0;

	public void draw() 
    {
		background(0);
		fill(255);
		noStroke();
		textSize(20);
        textAlign(CENTER, CENTER);
        
	}

	public class Follow {
		private String word;
		private int count;
	  
		public Follow(String word) {
		  this.word = word;
		  count = 1;
		}
	  
		public String getWord() {
		  return word;
		}
	  
		public int getCount() {
		  return count;
		}
	  
		public void incrementCount() {
		  count++;
		}
	  
		public String toString() {
		  return word + "(" + count + ")";
		}
	  }

	  

public class Word {
  private String word;
  private ArrayList<Follow> follows;

  public Word(String word) {
    this.word = word;
    follows = new ArrayList<Follow>();
  }

  public String getWord() {
    return word;
  }

  public ArrayList<Follow> getFollows() {
    return follows;
  }

  public void addFollow(String follow) {
    Follow f = findFollow(follow);
    if (f == null) {
      f = new Follow(follow);
      follows.add(f);
    } else {
      f.incrementCount();
    }
  }

  public Follow findFollow(String follow) {
    for (Follow f : follows) {
      if (f.getWord().equals(follow)) {
        return f;
      }
    }
    return null;
  }

  public String toString() {
    String result = word + ": ";
    for (Follow f : follows) {
      result += f.toString() + " ";
    }
    return result;
  }
}




	  
}
