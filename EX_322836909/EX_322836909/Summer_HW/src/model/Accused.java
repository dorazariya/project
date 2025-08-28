package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

import enums.Gender;
import enums.Status;

public class Accused extends Person implements Serializable {
	//fields
	private String job;
	private HashSet <Case> cases = new HashSet<>();
	
	public Accused(int id, String firstName, String lastName, Date birthdate, String address, String email,
			String phone, Gender gender, String job, HashSet<Case> cases) {
		super(id, firstName, lastName, birthdate, address, email, phone, gender);
		setJob(job);
		this.cases = cases;
	}
	
	// without hashset
	public Accused(int id, String firstName, String lastName, Date birthdate, String address, String email,
			String phone, Gender gender, String job) {
		super(id, firstName, lastName, birthdate, address, email, phone, gender);
		setJob(job);
	}

	// Getters
	public String getJob() {
		return job;
	}

	public HashSet<Case> getCases() {
		return cases;
	}
	// Setters
	public void setJob(String job) {
	    if (job == null) {
	        this.job = null;
	        return;
	    }
	    job = job.replace("\"", "");
	    job = job.trim();
	    this.job = job;
	}

	@Override
	public String toString() {
	    return "Accused [id =" + id + ", FirstName=" + firstName + ", LastName=" + lastName +
	           ", BirthDate=" + birthdate + ", Address=" + address +
	           ", PhoneNumber=" + phone + ", Email=" + email + ", Gender=" + gender +
	           " ,job=" + job + "]";
	}

	public void setCases(HashSet<Case> cases) {
		this.cases = cases;
	}
	
	public boolean addCase(Case Case) {
        if (Case != null && !cases.contains(Case)) {
        	cases.add(Case);
            return true;
        }
        return false; 
    }
	
    public boolean removeCase(Case Case) {
        if (Case == null) return false;
        return cases.remove(Case);
    }
    
    public boolean closeCase(Case Case) {
        if (Case != null && cases.contains(Case)) {
        	Case.setCaseStatus(Status.finished);
            return true;
        }
        return false; 
    }
    
    public Case getRequiredCase() {
        Case res = null;
        for (Case c : cases) {
            if (res == null || c.getOpenDate().before(res.getOpenDate())) res = c;
        }
        return res; 
    }
}
