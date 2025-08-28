package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

import enums.Specialization;
import enums.Status;
import model.Accused;
import model.Case;
import model.Department;
import model.Judge;
import model.Lawyer;
import model.Verdict;
import utils.MyFileLogWriter;

public class Court implements Serializable {
	// fields
    private static final Court INSTANCE = new Court();
	private final HashMap< Integer, Lawyer> allLawyers;
	private final HashMap < Integer, Accused> allAccused;
	private final HashMap < Integer, Department > allDepartments;
	private final HashMap <String , Case> allCases;
	private final HashMap <Integer , Verdict > allVerdicts;
	
    public Court() {
    	allLawyers = new HashMap<>();
    	allAccused = new HashMap<>();
    	allDepartments = new HashMap<>();
    	allCases = new HashMap<>();
    	allVerdicts = new HashMap<>();
    }
    
    public static Court getInstance() { return INSTANCE; }

	@Override
	public String toString() {
		return "Court [allLawyers=" + allLawyers + ", allAccused=" + allAccused + ", allDepartments="
				+ allDepartments + ", allCases=" + allCases + ", allVerdicts=" + allVerdicts + "]";
	}

	////
	// Functions for adding elements from collections
	////
	public boolean addLawyer(Lawyer l) {
	    if (l == null || allLawyers.containsKey(l.getId())) return false;
	    allLawyers.put(l.getId(), l);
	    return true;
	}
	
	public boolean addJudge(Judge j) {
	    if (j == null || allLawyers.containsKey(j.getId())) return false;
	    allLawyers.put(j.getId(), j);
	    return true;
	}

	public boolean addAccused(Accused a) {
	    if (a == null || allAccused.containsKey(a.getId())) return false;
	    allAccused.put(a.getId(), a);
	    return true;
	}

	public boolean addDepartment(Department d) {
	    if (d == null || allDepartments.containsKey(d.getNumber())) return false;
	    allDepartments.put(d.getNumber(), d);
	    return true;
	}
	
	public boolean addVerdict(Verdict v) {
	    if (v == null || allVerdicts.containsKey(v.getVerdictID())) return false;
	    allVerdicts.put(v.getVerdictID(), v);

	    Case c = v.getCase();
	    if (c != null) {
	        c.setVerdict(v);
	        c.setCaseStatus(Status.finished);
	    }
	    return true;
	}

	public boolean addCase(Case Case) {
	    if (Case == null || allCases.containsKey(Case.getCode())) return false;
	    allCases.put(Case.getCode(), Case);

	    if (Case.getLawyer() != null) Case.getLawyer().addCase(Case);
	    if (Case.getAccused() != null) Case.getAccused().addCase(Case);
	    if (Case.getVerdict() != null) addVerdict(Case.getVerdict());

	    return true;
	}
	
	public boolean addLawyerToDepartment(Department department, Lawyer lawyer) {
	    if (department == null || lawyer == null) return false;
	    lawyer.setDepartment(department);
	    return department.getLawyersList().add(lawyer);
	}

	public boolean addJudgeToDepartment(Department department, Judge judge) {
	    if (department == null || judge == null) return false;
	    allDepartments.putIfAbsent(department.getNumber(), department);

	    department.getLawyersList().add(judge);
	    if (department.getManager() != judge) {
	        department.setManager(judge);
	        judge.setDepartment(department);
	    }
	    return true;
	}

	////
	// Functions for removing elements from collections
	////
	public boolean removeLawyer(Lawyer l) {
	    if (l == null) return false;
	    Lawyer removed = allLawyers.remove(l.getId());
	    if (removed == null) return false;

	    Department dep = removed.getDepartment();
	    if (dep != null) {
	        dep.getLawyersList().remove(removed);
	        if (dep.getManager() == removed) dep.setManager(null);
	    }
	    for (Case c : allCases.values()) {
	        if (c.getLawyer() == removed) c.setLawyer(null);
	    }
	    return true;
	}
	
	public boolean removeJudge(Judge j) {
	    if (j == null) return false;

	    Lawyer asLawyer = allLawyers.get(j.getId());
	    if (!(asLawyer instanceof Judge)) {
	        return false; 
	    }
	    Judge removed = (Judge) asLawyer;
	    allLawyers.remove(removed.getId());

	    Department d = removed.getDepartment(); 
	    if (d != null && d.getManager() == removed) {
	        d.setManager(null);
	    }
	    for (Department dep : allDepartments.values()) {
	        if (dep.getManager() == removed) {
	            dep.setManager(null);
	        }
	    }

	    for (Verdict v : allVerdicts.values()) {
	        if (v != null && v.getJudge() == removed) {
	            v.setJudge(null);
	        }
	    }
	    return true;
	}

	public boolean removeAccused(Accused a) {
	    if (a == null) return false;
	    Accused removed = allAccused.remove(a.getId());
	    if (removed == null) return false;

	    for (Case c : new ArrayList<>(removed.getCases())) {
	        c.setAccused(null);
	        removed.getCases().remove(c);
	    }
	    return true;
	}

	public boolean removeDepartment(Department d) {
	    if (d == null) return false;
	    Department removed = allDepartments.remove(d.getNumber());
	    if (removed == null) return false;

	    for (Lawyer l : new ArrayList<>(removed.getLawyersList())) {
	        l.setDepartment(null);
	    }
	    removed.setManager(null);
	    removed.getLawyersList().clear();
	    return true;
	}

	public boolean removeCase(Case c) {
	    if (c == null) return false;
	    Case removed = allCases.remove(c.getCode());
	    if (removed == null) return false;

	    Lawyer lw = removed.getLawyer();
	    if (lw != null && lw.getCasesHandled() != null) lw.getCasesHandled().remove(removed);

	    Accused ac = removed.getAccused();
	    if (ac != null && ac.getCases() != null) ac.getCases().remove(removed);

	    if (removed.getVerdict() != null) {
	        Verdict v = removed.getVerdict();
	        allVerdicts.remove(v.getVerdictID());
	        removed.setVerdict(null);
	        removed.setCaseStatus(Status.inProcess);
	    }
	    return true;
	}

	public boolean removeVerdict(Verdict v) {
	    if (v == null) return false;
	    Verdict removed = allVerdicts.remove(v.getVerdictID());
	    if (removed == null) return false;

	    Case caseRef = removed.getCase();
	    if (caseRef != null) {
	        caseRef.setVerdict(null);
	        caseRef.setCaseStatus(Status.inProcess);
	    }
	    return true;
	}

    /**
     * Retrieves a Object by their unique code.
     *
     * @param code
     * @return the object, or null if not found
     */
	public Lawyer getRealLawyer(int id) {
	    return allLawyers.get(id);
	}
	public Judge getRealJudge(int id) {
	    Lawyer l = allLawyers.get(id);
	    if (l instanceof Judge) {
	        return (Judge) l;
	    }
	    return null;
	}
	public Accused getRealAccused(int id) {
	    return allAccused.get(id);
	}
	public Department getRealDepartment(int number) {
	    return allDepartments.get(number);
	}
	public Case getRealCase(String code) {
	    return allCases.get(code);
	}
	public Verdict getRealVerdict(int id) {
	    return allVerdicts.get(id);
	}
	
	////
	// Query Methods
	////

	//1
	public int howManyCasesBefore(Date date) {
	    if (date == null) return 0;
	    int count = 0;
	    for (Case c : allCases.values()) {
	        Verdict v = c.getVerdict();
	        if (v != null && c.getCaseStatus() == Status.finished && v.getIssusedDate() != null && v.getIssusedDate().before(date)) {
	            count++;
	        }
	    }
	    return count;
	}

	//2
	
	   /**
     * Calculates the difference in days between earliest and latest case dates for a lawyer.
     */
    public int differenceBetweenTheLatestAndEarliestCase(Lawyer lawyer) {
        if (lawyer == null) return 0;
        List<Date> dates = lawyer.getCasesHandled().stream()
                .map(Case::getOpenDate)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
        if (dates.size() < 2) return 0;
        long diffMillis = dates.get(dates.size()-1).getTime() - dates.get(0).getTime();
        return (int)(diffMillis / (1000L*60*60*24)); 
    }


	// 3
	public void printMostExperiencedJudge() {
	    Judge mostExperienced = null;
	    for (Lawyer l : allLawyers.values()) {
	        if (l instanceof Judge) {
	            Judge j = (Judge) l;
	            if (mostExperienced == null || j.getExperienceYear() > mostExperienced.getExperienceYear()) {
	                mostExperienced = j;
	            }
	        }
	    }
	    if (mostExperienced != null) {
	        MyFileLogWriter.println("The Most Experienced Judge is : " + mostExperienced);
	    }
	}

	// 4
	public Accused getAccusedWithMaxCasesByLawyers(Specialization caseType) {
	    Accused maxAccused = null;
	    int maxCases = -1;

	    for (Accused accused : allAccused.values()) {
	        int count = 0;
	        for (Case c : accused.getCases()) {
	            Lawyer lawyer = c.getLawyer();
	            if (lawyer != null && lawyer.getSpecialization() == caseType) {
	                count++;
	            }
	        }
	        if (count > maxCases) {
	            maxCases = count;
	            maxAccused = accused;
	        }
	    }

	    if (maxCases == 0) {
	        for (Accused accused : allAccused.values()) {
	            if (maxAccused == null || accused.getCases().size() > maxAccused.getCases().size()) {
	                maxAccused = accused;
	            }
	        }
	    }

	    return maxAccused;
	}

	//5
	public HashMap<Lawyer, Stack<Department>> lawyersThatWorkWithMoreThanOneCase() {
	    HashMap<Lawyer, Stack<Department>> res = new HashMap<>();

	    for (Lawyer l : allLawyers.values()) {
	        if (l == null) continue;
	        
	        Stack<Department> deps = new Stack<>();

	        for (Department d : allDepartments.values()) {
	            if (d.getLawyersList().contains(l)) {
	                deps.push(d);
	            }
	        }

	        if (deps.size() > 1) {
	            res.put(l, deps);
	        }
	    }

	    return res;
	}


	// 6
	public HashMap<Department, Integer> findInActiveCasesCountByDepartment() {
	    HashMap<Department, Integer> map = new HashMap<>();

	    for (Department d : allDepartments.values()) {
	        int count = 0;
	        for (Case c : allCases.values()) {
	            Lawyer lawyer = c.getLawyer();
	            if (lawyer != null &&
	                c.getCaseStatus() == Status.finished &&
	                d.getLawyersList().contains(lawyer)) {
	                count++;
	            }
	        }
	        map.put(d, count);
	    }
	    return map;
	}
	
	// 7
	public Judge appointANewManager(Department department) {
	    if (department == null) return null;

	    Judge most = null;
	    for (Lawyer l : allLawyers.values()) {
	        if (l instanceof Judge j) {
	            if (most == null || j.getExperienceYear() > most.getExperienceYear()) {
	                most = j;
	            }
	        }
	    }

	    if (most == null) {
	        Lawyer bestL = null;
	        for (Lawyer l : allLawyers.values()) {
	            if (!(l instanceof Judge)) {
	                if (bestL == null || l.getId() < bestL.getId()) bestL = l;
	            }
	        }
	        if (bestL != null) {
	            most = new Judge(
	                bestL.getId(), bestL.getFirstName(), bestL.getLastName(),
	                bestL.getBirthdate(), bestL.getAddress(), bestL.getPhone(), bestL.getEmail(),
	                bestL.getGender(), bestL.getSpecialization(),
	                bestL.getLicenseNumber(), bestL.getSalary(), 0
	            );
	            allLawyers.put(most.getId(), most); 
	        }
	    }

	    if (most == null && department.getManager() != null) {
	        most = department.getManager();
	    }

	    if (most != null) {
	        department.setManager(most);
	        most.setSalary(most.getSalary() + 5000);
	        if (most.getDepartment() == null) most.setDepartment(department);
	    }
	    return most;
	}
}
