package model;

import java.io.Serializable;
import java.util.HashSet;

import enums.Specialization;

public class Department implements Serializable{
	// fields
	private int number;
	private String name;
	private Judge manager;
	private String building;
	private Specialization specialization;
	private HashSet <Lawyer>lawyersList = new HashSet<>();
	
	public Department(int numberPk, String name, Judge manager, String building, Specialization specialization,
			HashSet<Lawyer> lawyersList) {
		super();
		this.number = numberPk;
		this.name = name;
		this.manager = manager;
		this.building = building;
		this.specialization = specialization;
		this.lawyersList = lawyersList;
	}
	
	// without arraylist
	public Department(int number, String name, Judge manager, String building, Specialization specialization) {
		super();
		this.number = number;
		this.name = name;
		this.manager = manager;
		this.building = building;
		this.specialization = specialization;
	}


	// Getters
	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public Judge getManager() {
		return manager;
	}

	public String getBuilding() {
		return building;
	}

	public Specialization getSpecialization() {
		return specialization;
	}

	public HashSet<Lawyer> getLawyersList() {
		return lawyersList;
	}
	
	// Setters
	public void setNumber(int number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setManager(Judge manager) {
		this.manager = manager;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	public void setLawyersList(HashSet<Lawyer> lawyersList) {
		this.lawyersList = lawyersList;
	}
	
	// Add\Remove
	public boolean addLawyer(Lawyer lawyer) {
	if (lawyer != null && !lawyersList.contains(lawyer)) {
		lawyersList.add(lawyer);
            return true;
        }
        return false; 
    }
	
	public boolean removeLawyer(Lawyer lawyer) {
        if (lawyer == null) return false;
        return lawyersList.remove(lawyer);
    }
	
	public boolean addJudge(Judge judge) {
	if (judge != null && !lawyersList.contains(judge)) {
		lawyersList.add(judge);
            return true;
        }
        return false; 
    }
	
	public boolean removeJudge(Judge judge) {
        if (judge == null) return false;
        return lawyersList.remove(judge);
    }
	
	@Override
	public String toString() {
	    return "number=" + number + ", name=" + name + ", manager=" + manager +
	           ", building=" + building + ", specialization=" + specialization;
	}
}
