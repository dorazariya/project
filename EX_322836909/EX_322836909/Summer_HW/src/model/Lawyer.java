package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import enums.Gender;
import enums.Specialization;
import enums.Status;

public class Lawyer extends Person implements Serializable {
	// fields
	protected ArrayList<Case> casesHandled = new ArrayList<>();
	protected Specialization specialization;
	protected int licenseNumber;
	protected double salary;
	protected Department department;
	
	//full
	public Lawyer(int id, String firstName, String lastName, Date birthdate, String address, String email, String phone,
			Gender gender, ArrayList<Case> casesHandled, Specialization specialization, int licenseNumber,
			double salary, Department department) {
		super(id, firstName, lastName, birthdate, address, email, phone, gender);
		this.casesHandled = casesHandled;
		this.specialization = specialization;
		this.licenseNumber = licenseNumber;
		this.salary = salary;
		this.department = department;
	}
	
	// without department && arraylist
	public Lawyer(int id, String firstName, String lastName, Date birthdate,
	              String address, String phone, String email, Gender gender,
	              Specialization specialization, int licenseNumber, double salary) {
	    super(id, firstName, lastName, birthdate, address, phone, email, gender);
	    this.casesHandled = new ArrayList<>();   
	    this.specialization = specialization;
	    this.licenseNumber = licenseNumber;
	    this.salary = salary;
	    this.department = null;        
	}


	// Getters
	public ArrayList<Case> getCasesHandled() {
		return casesHandled;
	}

	public Specialization getSpecialization() {
		return specialization;
	}

	public int getLicenseNumber() {
		return licenseNumber;
	}

	public double getSalary() {
		return salary;
	}

	public Department getDepartment() {
		return department;
	}

	// Setters
	public void setCasesHandled(ArrayList<Case> casesHandled) {
		this.casesHandled = casesHandled;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	public void setLicenseNumber(int licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
	    return "Lawyer [id =" + id + ", FirstName=" + firstName + ", LastName=" + lastName +
	           ", BirthDate=" + birthdate + ", Address=" + address + 
	           ", PhoneNumber=" + phone + ", Email=" + email + ", Gender=" + gender +
	           ", Specialization=" + specialization + ", licenseNumber= " + licenseNumber + ", salary=" + salary + "]";
	}

	public boolean addCase(Case Case) {
        if (Case != null && !casesHandled.contains(Case)) {
            casesHandled.add(Case);
            return true;
        }
        return false; 
    }
	
    public boolean removeCase(Case Case) {
        if (Case == null) return false;
        return casesHandled.remove(Case);
    }
    
    public boolean closeCase(Case Case) {
        if (Case != null && casesHandled.contains(Case)) {
        	Case.setCaseStatus(Status.finished);
            return true;
        }
        return false; 
    }
    
    public Case getRequiredCase() {
		Case res = null;
    	for(Case c:casesHandled) {
    		if(c.getLawyer() != null && c.getLawyer().getSpecialization()==this.specialization) {
    			if(res==null || c.getAccused().getCases().size() > res.getAccused().getCases().size())
    				res = c;
    		}
    	}
    	return res;
    }
}
