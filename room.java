package program2;

import java.util.ArrayList;
import java.util.List;

public class room {

	
	private List<Integer> students = new ArrayList<Integer>();
	private int roomNumber;
	private double RMSScore;

	room(List<Integer> students, int roomNumber){
		this.students = students;
		this.roomNumber = roomNumber;
	}
	
	public double getRMSScore() {
		return RMSScore;
	}
	
	public List<Integer> getStudent(){
		return this.students;
	}
	
	public int getRoomNum() {
		return roomNumber;
	}
	
	public void setRMSScore(double score) {
		this.RMSScore = score;
	}
	

	
	
}
