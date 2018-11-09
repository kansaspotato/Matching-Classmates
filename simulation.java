package program2;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Arrays;
import java.util.Scanner;



public class simulation {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		List<room> rooms = new ArrayList<room>();
		List<Double> RMSList = new ArrayList<Double>();
		
		
		// creating student's compatible score in array (200x200)
		 Scanner sc = new Scanner(new BufferedReader(new FileReader("/Users/jinhyuk/Desktop/자바/program2/src/program2/score")));
	      int rows = 200;
	      int columns = 200;
	      int [][] myArray = new int[rows][columns];
	      while(sc.hasNextLine()) {
	         for (int i=0; i<myArray.length; i++) {
	            String[] line = sc.nextLine().trim().split(" ");
	            for (int j=0; j<line.length; j++) {
	               myArray[i][j] = Integer.parseInt(line[j]);}}}
		
	    
	
		Random r = new Random();
	
		// creating 50 rooms
		int studentIndex = 1;
		int student = 0;
	
		for (int i = 0; i < 50; i++) {
			
			List<Integer> students = new ArrayList<Integer>();
			
			for(int j = studentIndex; j < studentIndex+4; j++) {
				students.add(j);
			}
			room temp = new room(students, i);
			student++;
			rooms.add(temp);
			studentIndex+=4;
		}
		
		// calculate the initial RMS score for 50 rooms. 
		for(int i = 0; i < 50; i ++) {
		rooms.get(i).setRMSScore(calculateRMS(rooms.get(i).getStudent(), myArray));
		
		}
		
		// start simulated annealing 
		double temperature = 800;
		int iteration = 1;
		int attempts = 0;
		int swaps = 0;
		
		while(attempts != 20000 || swaps != 0) {
			swaps = 0;
			attempts = 0;
			
			while(swaps != 2000 && attempts != 20000) {
		int room1 = r.nextInt(50);
		int room2 = r.nextInt(50);
		while(room1 == room2) {
			room2 = r.nextInt(50);
		}
		int room1Index = r.nextInt(4);
		int room2Index = r.nextInt(4);	
		int room1Num = rooms.get(room1).getStudent().get(room1Index);
		int room2Num = rooms.get(room2).getStudent().get(room2Index);

		
			boolean swap = compareScore(rooms, myArray, room1, room2, room1Index, room2Index, temperature);
	
			if(swap == true) {
			swaps++;
			attempts++;
			rooms.get(room1).getStudent().remove(room1Index);
			rooms.get(room1).getStudent().add(room2Num);
			rooms.get(room2).getStudent().remove(room2Index);
			rooms.get(room2).getStudent().add(room1Num);
		
				
				
			}
			else {
			attempts++;
			}
		
		
		
		}
			double total = 0;
			for(int i = 0; i < 50; i++) {
				RMSList.add(rooms.get(i).getRMSScore());
				total += rooms.get(i).getRMSScore();
			}
			double RMSAvg = total/50;
			Collections.sort(RMSList);
			
			
			System.out.println("Iteration: " + iteration + " " + "Swaps: " + swaps + " " + "Attempts: " + attempts + " " + "Min= " + RMSList.get(0)  + " " +
			"Max= " + RMSList.get(49)  + " " + "Avg= " + RMSAvg  + " " + "Tempterature:" + temperature);
			RMSList.clear();
			iteration++;
			temperature *= 0.95;
		}
	
	
		
	}

	public static boolean compareScore(List<room> rooms, int[][] myArray, int room1, int room2, int room1Index, int room2Index, double temperature) {
		List<Integer> roomOne = new ArrayList<Integer>();
		List<Integer> roomTwo = new ArrayList<Integer>();
		roomOne.addAll(rooms.get(room1).getStudent());
		roomTwo.addAll(rooms.get(room2).getStudent());
		
		
		double oldScore1 = rooms.get(room1).getRMSScore();
		double oldScore2 = rooms.get(room2).getRMSScore();
		double oldScoreAvg = (oldScore1 + oldScore2) / 2;
		

		
		int roomOneStudent = roomOne.get(room1Index);
		int roomTwoStudent = roomTwo.get(room2Index);
		roomOne.remove(room1Index);
		roomOne.add(roomTwoStudent);
		roomTwo.remove(room2Index);
		roomTwo.add(roomOneStudent);
	
		
		double room1RMS = calculateRMS(roomOne, myArray);
		
		double room2RMS = calculateRMS(roomTwo, myArray);
	
		double newScoreAvg = (room1RMS + room2RMS) / 2;
		double difference = newScoreAvg - oldScoreAvg;
		
		if(newScoreAvg > oldScoreAvg) {
			rooms.get(room1).setRMSScore(room1RMS);
			rooms.get(room2).setRMSScore(room2RMS);
			roomOne.clear();
			roomTwo.clear();
			return true;
		}
		else {
			
			double p1 = (Math.exp(difference/temperature));
			double p2 = Math.random();
			
			if (p1 > p2) {
				rooms.get(room1).setRMSScore(room1RMS);
				rooms.get(room2).setRMSScore(room2RMS);
				roomOne.clear();
				roomTwo.clear();
				return true;
			}
		}
		roomOne.clear();
		roomTwo.clear();
	
		return false;
		
	}
	
	public static double calculateRMS(List<Integer> room,int[][] myArray) {
		double total = Math.pow(myArray[room.get(0) - 1][room.get(1) -1], 2.0) + Math.pow(myArray[room.get(0) - 1][room.get(2) -1], 2.0) + 
				Math.pow(myArray[room.get(0) - 1][room.get(3) -1] ,2.0)+ Math.pow(myArray[room.get(1) - 1][room.get(2) - 1], 2.0) +
				Math.pow(myArray[room.get(1) - 1][room.get(3) - 1], 2.0) + Math.pow(myArray[room.get(2) - 1][room.get(3) - 1], 2.0);
		double average = total/6;
		double RMS = Math.sqrt(average);
		return RMS;
		
	}
	

}
