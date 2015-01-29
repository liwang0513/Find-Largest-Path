package main;

import org.json.simple.JSONArray;

public class BoardTest {
	
	public static void main(String[] args) {
		Parser parser = new Parser();
		JSONArray theArray = parser.parseJSONFile("apply-20-challenge.json");
		
//		int[][] array = {{0,5,0,8,1,8},
//                {3,6,1,3,6,3},
//                {9,5,7,9,1,1},
//                {8,7,9,4,8,3},
//                {7,8,7,6,2,5},
//                {3,4,0,5,0,4}};
		
		Board board = new Board(theArray);
		
		System.out.println(board.getLargestPath());

	}
}
