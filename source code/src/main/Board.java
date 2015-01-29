package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Board {
	
	private int rowNum;
	private int colNum;
	
	private Entry[][] board;
	
	public Board(JSONArray jsonArray) {
		
		if (jsonArray != null) {
			this.rowNum = jsonArray.size();
			this.colNum = ((JSONArray)jsonArray.get(0)).size();
			board = new Entry[rowNum][colNum];
		}
		
		for (int i=0; i<rowNum; i++) {
			for (int j=0; j<colNum; j++) {
				int value = Integer.parseInt(((JSONArray)jsonArray.get(i)).get(j).toString());
				
				board[i][j] = new Entry(value, "");
			}
		}
	}
	
	public Board(int[][] array) {
		
		if (array != null) {
			this.rowNum = array.length;
			this.colNum = array[0].length;
			board = new Entry[rowNum][colNum];
		}
		
		for (int i=0; i<rowNum; i++) {
			for (int j=0; j<colNum; j++) {
				int value = array[i][j];
				
				board[i][j] = new Entry(value, "");
			}
		}
	}
	
	public int getRowNum() {
		return rowNum;
	}
	
	public int getColNum() {
		return colNum;
	}
	
	public Entry[][] getBoard() {
		return board;
	}
	
	
	// use dynamic programming to solve this problem
	// time complexity is O(N), space complexity is O(logN)
	public String getLargestPath() {
		
		Entry[] line = new Entry[colNum];
		
		// construct the first line
		for (int i=0; i<colNum; i++) {
			line[i] = new Entry(board[0][i].getValue(), board[0][i].getPath());
			
			if (i > 0) {
				line[i].setValue(line[i].getValue()+line[i-1].getValue());
				line[i].setPath(line[i-1].getPath()+"R");
			}
		}
		
		// use dynamic programming to iterate the whole board
		for (int rowIndex=1; rowIndex<rowNum; rowIndex++) {
			for (int i=0; i<colNum; i++) {
				// special case for the first element
				if (i==0) {
					line[i].setValue(board[rowIndex][i].getValue()+line[0].getValue());
					line[i].setPath(line[0].getPath()+"D");
					continue;
				}
				
				// compare the left and top entry to choose the right way
				if (line[i-1].getValue() >= line[i].getValue()) {
					// choose the left entry
					line[i].setValue(board[rowIndex][i].getValue()+line[i-1].getValue());
					line[i].setPath(line[i-1].getPath()+"R");
				} else {
					// choose the top entry
					line[i].setValue(board[rowIndex][i].getValue()+line[i].getValue());
					line[i].setPath(line[i].getPath()+"D");
				}
			}
			
		}
		
		return line[line.length-1].getPath();
	}
	
}

class Entry {
	
	private int value;
	private String path;
	
	public Entry(int value, String path) {
		this.value = value;
		this.path = path;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getPath() {
		return path;
	}
}

class Parser {
	
	public JSONArray parseJSONFile(String url) {
		
		JSONParser parser = new JSONParser();
		JSONArray theArray = null;

		try {
			theArray = (JSONArray) parser.parse(new FileReader(url));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        return theArray;
	}
}
