package chess;

public class AdjacentSquares {

	private static final int[][] adjacencyLookup = {
			
			// {North,NE,East,SE,South,SW,West,NW}
			
			{8, 9, 1, -1, -1, -1, -1, -1}, //[Square 0]
			{9, 10, 2, -1, -1, -1, 0, 8},
			{10, 11, 3, -1, -1, -1, 1, 9},
			{11, 12, 4, -1, -1, -1, 2, 10},
			{12, 13, 5, -1, -1, -1, 3, 11},
			{13, 14, 6, -1, -1, -1, 4, 12},
			{14, 15, 7, -1, -1, -1, 5, 13},
			{15, -1, -1, -1, -1, -1, 6, 14},
			
			{16, 17, 9, 1, 0, -1, -1, -1},
			{17, 18, 10, 2, 1, 0, 8, 16},
			{18, 19, 11, 3, 2, 1, 9, 17},
			{19, 20, 12, 4, 3, 2, 10, 18},
			{20, 21, 13, 5, 4, 3, 11, 19},
			{21, 22, 14, 6, 5, 4, 12, 20},
			{22, 23, 15, 7, 6, 5, 13, 21},
			{23, -1, -1, -1, 7, 6, 14, 22},
			
			{24, 25, 17, 9, 8, -1, -1, -1},
			{25, 26, 18, 10, 9, 8, 16, 24},
			{26, 27, 19, 11, 10, 9, 17, 25},
			{27, 28, 20, 12, 11, 10, 18, 26},
			{28, 29, 21, 13, 12, 11, 19, 27},
			{29, 30, 22, 14, 13, 12, 20, 28},
			{30, 31, 23, 15, 14, 13, 21, 29},
			{31, -1, -1, -1, 15, 14, 22, 30},
			
			{32, 33, 25, 17, 16, -1, -1, -1},
			{33, 34, 26, 18, 17, 16, 24, 32},
			{34, 35, 27, 19, 18, 17, 25, 33},
			{35, 36, 28, 20, 19, 18, 26, 34},
			{36, 37, 29, 21, 20, 19, 27, 35},
			{37, 38, 30, 22, 21, 20, 28, 36},
			{38, 39, 31, 23, 22, 21, 29, 37},
			{39, -1, -1, -1, 23, 22, 30, 38},
			
			{40, 41, 33, 25, 24, -1, -1, -1},
			{41, 42, 34, 26, 25, 24, 32, 40},
			{42, 43, 35, 27, 26, 25, 33, 41},
			{43, 44, 36, 28, 27, 26, 34, 42},
			{44, 45, 37, 29, 28, 27, 35, 43},
			{45, 46, 38, 30, 29, 28, 36, 44},
			{46, 47, 39, 31, 32, 33, 37, 45},
			{47, -1, -1, -1, 31, 30, 38, 46},
			
			{48, 49, 41, 33, 32, -1, -1, -1},
			{49, 50, 42, 34, 33, 32, 40, 48},
			{50, 51, 43, 35, 34, 33, 41, 49},
			{51, 52, 44, 36, 35, 34, 42, 50},
			{52, 53, 45, 37, 36, 35, 43, 51},
			{53, 54, 46, 38, 37, 36, 44, 52},
			{54, 55, 47, 39, 38, 37, 45, 53},
			{55, -1, -1, -1, 39, 38, 46, 54},
			
			{56, 57, 49, 41, 40, -1, -1, -1},
			{57, 58, 50, 42, 41, 40, 48, 56},
			{58, 59, 51, 43, 42, 41, 49, 57},
			{59, 60, 52, 44, 43, 42, 50, 58},
			{60, 61, 53, 45, 44, 43, 51, 59},
			{61, 62, 54, 46, 45, 44, 52, 60},
			{62, 63, 55, 47, 46, 45, 53, 61},
			{63, -1, -1, -1, 47, 46, 54, 62},
			
			{-1, -1, 57, 49, 48, -1, -1, -1},
			{-1, -1, 58, 50, 49, 48, 56, -1},
			{-1, -1, 59, 51, 50, 49, 57, -1},
			{-1, -1, 60, 52, 51, 50, 58, -1},
			{-1, -1, 61, 53, 52, 51, 59, -1},
			{-1, -1, 62, 54, 53, 52, 60, -1},
			{-1, -1, 63, 55, 54, 53, 61, -1},
			{-1, -1, -1, -1, 55, 54, 62, -1}
			
	};
	
	public static int get(int squareNumber, int direction) {
		if (squareNumber < 0 || squareNumber > 63 || direction < 0 || direction > 7) {
			return -1;
		}
		return adjacencyLookup[squareNumber][direction];
		
	}
	
}
