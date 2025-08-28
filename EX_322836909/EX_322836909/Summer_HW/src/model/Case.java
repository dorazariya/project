package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import enums.Specialization;
import enums.Status;

public class Case implements Serializable{
	// fields
    private static int nextCode = 0;
	private String code;
	private Accused accused;
	private Date openDate;
	private Status caseStatus;
	private Specialization caseType;
	private Lawyer lawyer;
	private Verdict verdict;
	
	public Case(Accused accused, Date openDate, Status caseStatus, Specialization caseType, Lawyer lawyer,
			Verdict verdict) {
		super();
		this.code = String.valueOf(nextCode++);
		this.accused = accused;
		this.openDate = openDate;
		this.caseStatus = caseStatus;
		this.caseType = caseType;
		this.lawyer = lawyer;
		this.verdict = verdict;
	}
	public String getCode() {
		return code;
	}
	public Accused getAccused() {
		return accused;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public Status getCaseStatus() {
		return caseStatus;
	}
	public Specialization getCaseType() {
		return caseType;
	}
	public Lawyer getLawyer() {
		return lawyer;
	}
	public Verdict getVerdict() {
		return verdict;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setAccused(Accused accused) {
		this.accused = accused;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public void setCaseStatus(Status caseStatus) {
		this.caseStatus = caseStatus;
	}
	public void setCaseType(Specialization caseType) {
		this.caseType = caseType;
	}
	public void setLawyer(Lawyer lawyer) {
		this.lawyer = lawyer;
	}
	public void setVerdict(Verdict verdict) {
		this.verdict = verdict;
	}
	
	@Override
	public String toString() {
	    String base = "code=" + code + ", accused=";
	    if (accused != null) base += accused.toString(); else base += "null";
	    base += ", openedDate=" + openDate;
	    base += ", caseStatus=" + caseStatus;
	    base += ", caseType=" + caseType;
	    base += ", lawyer=";
	    if (lawyer != null) base += lawyer.firstName; else base += "null";
	    if (verdict != null && caseStatus == Status.finished) {
	        base += ", verdict=" + verdict.toString();
	    }
	    return "Case [" + base + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Case other = (Case) obj;
		return code == other.code;
	}
	
	/*
	public boolean addLawyer(Lawyer lawyer) {
        if (Case != null && !casesHandled.contains(Case)) {
            casesHandled.add(Case);
            return true;
        }
        return false; 
    }
	
    public boolean removeLawyer(Lawyer lawyer) {
        if (Case == null) return false;
        return casesHandled.remove(Case);
    }
    */
	
	
}
