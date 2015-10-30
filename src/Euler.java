import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Euler {

	public static void main(String[] args) throws FileNotFoundException {
		String[] inputs = { "input1.txt", "input2.txt", "input3.txt", "input4.txt"};
		
		for(String s : inputs)
			System.out.println(s + ": " + eulerCharacteristic(s));
	}
	
	public static int eulerCharacteristic(String file) throws FileNotFoundException {
		//read in
		Scanner scan = new Scanner(new File(file));
		int rows = scan.nextInt();
		int columns = scan.nextInt();
		scan.nextLine();
		char[][] input = new char[rows+2][columns+2];
		readIn(input, scan);
		scan.close();
		//print(input);
		
		//fill all none hole O's
		fillO(input, 0, 0, '-');
		//print(input);
		
		//find holes
		int holeCount = 0;
		for (int i = 0; i < input.length; i++)
			for (int j = 0; j < input[0].length; j++)
				if(input[i][j] == 'O') {
					fillO(input, i, j, Integer.toString((holeCount%10)).charAt(0));
					holeCount++;
				}
		//print(input);
		
		//find regions
		int regionCount = 0;
		for (int i = 0; i < input.length; i++)
			for (int j = 0; j < input[0].length; j++)
				if(input[i][j] == 'X') {
					fillX(input, i, j, (char)(regionCount + 'a'));
					regionCount++;
				}
		//print(input);
		return regionCount - holeCount;
	}
	
	public static void fillX(char[][] input, int x, int y, char replacement) {
		input[x][y] = replacement;
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if(x+i > 0 && x+i < input.length - 1 && y+j > 0 && y+j < input[0].length - 1 && input[x+i][y+j] == 'X')
					fillX(input, x+i, y+j, replacement);
	}
	
	public static void fillO(char[][] input, int x, int y, char replacement) {
		input[x][y] = replacement;
		if(x > 0 && input[x-1][y] == 'O')
			fillO(input, x-1, y, replacement);
		if(x < input.length - 1 && input[x+1][y] == 'O')
			fillO(input, x+1, y, replacement);
		if(y > 0 && input[x][y-1] == 'O')
			fillO(input, x, y-1, replacement);
		if(y < input[0].length - 1 && input[x][y+1] == 'O')
			fillO(input, x, y+1, replacement);
	}
	
	public static void print(char[][] input) {
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++)
				System.out.print(input[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	
	public static void readIn(char[][] input, Scanner scan) {
		for (int i = 0; i < input[0].length; i++)
			input[0][i] = 'O';
		for (int i = 1; i < input.length - 1; i++) {
			input[i][0] = 'O';
			String line = scan.nextLine();
			for (int j = 1; j < input[0].length - 1; j++)
				input[i][j] = line.charAt(j - 1);
			input[i][input[0].length - 1] = 'O';
		}
		for (int i = 0; i < input[0].length; i++)
			input[input.length - 1][i] = 'O';
	}

}
