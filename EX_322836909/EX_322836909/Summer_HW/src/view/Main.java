package view;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.CSVExporter;
import utils.MyFileLogWriter;
import utils.UtilsMethods;
import autopilot.OutputDocument;
import autopilot.Section;
import control.Court;
import enums.Gender;
import enums.Specialization;
import enums.Status;
import model.Accused;
import model.Case;
import model.Department;
import model.Judge;
import model.Lawyer;
import model.Verdict;

public class Main {


	private static Court court = Court.getInstance();
	private static OutputDocument document = new OutputDocument();
	private static Map<String,Command> commands = new HashMap<>();
	private static Map<String,Section> sections = new HashMap<>();
	private static final String OUTPUT_FILE = "output.txt";
	

	
	private interface Command {  
		void execute(Section section,String... args);
	} 
	static {
		Section space = document.nextSection();
		Section finish = document.nextSection();
		
		//ADD 
		Section addDepartment = document.nextSection();
		Section addLawyer = document.nextSection();
		Section addJudge = document.nextSection();
		Section addVerdict = document.nextSection();
		Section addCase = document.nextSection();
		Section addAccused = document.nextSection();
		Section addLawyerDepartment = document.nextSection();
		Section addJudgeDepartment = document.nextSection();



		//Remove
		Section removeDepartment = document.nextSection();
		Section removeLawyer = document.nextSection();
		Section removeJudge = document.nextSection();
		Section removeVerdict = document.nextSection();
		Section removeCase = document.nextSection();
		Section removeAccused = document.nextSection();


		
		//Query
		Section howManyCasesBefore=document.nextSection();
		Section differenceBetweenTheLatestAndEarliestCase=document.nextSection();
		Section printMostExperiencedJudge=document.nextSection();
		Section getAccusedWithMaxCasesByLawyers=document.nextSection();

		Section lawyersThatWorkWithMoreThanOneCase =document.nextSection();
		Section findInactiveCasesCountByDepartment  =document.nextSection();
		Section appointANewManager =document.nextSection();

		Section getRequiredCase =document.nextSection();

		
		
		
		
		//Sections's HashMap PUT
		
		sections.put("addLawyerDepartment",addLawyerDepartment);//
		sections.put("addJudgeDepartment",addJudgeDepartment);//
		sections.put("addDepartment",addDepartment);
		sections.put("addLawyer",addLawyer);
		sections.put("addJudge",addJudge);
		sections.put("addVerdict",addVerdict);
		sections.put("addCase",addCase);
		sections.put("addAccused",addAccused);

		sections.put("removeDepartment",removeDepartment);
		sections.put("removeLawyer",removeLawyer);
		sections.put("removeJudge",removeJudge);
		sections.put("removeVerdict",removeVerdict);
		sections.put("removeCase",removeCase);
		sections.put("removeAccused",removeAccused);

		sections.put("howManyCasesBefore",howManyCasesBefore);
		sections.put("differenceBetweenTheLatestAndEarliestCase",differenceBetweenTheLatestAndEarliestCase);
		sections.put("printMostExperiencedJudge",printMostExperiencedJudge);
		sections.put("getAccusedWithMaxCasesByLawyers",getAccusedWithMaxCasesByLawyers);
		sections.put("findWitnessWithMostRepetitiveWordsInTestimony",getAccusedWithMaxCasesByLawyers);

		sections.put("lawyersThatWorkWithMoreThanOneCase",lawyersThatWorkWithMoreThanOneCase);
		sections.put("findInactiveCasesCountByDepartment",findInactiveCasesCountByDepartment);
		sections.put("appointANewManager",appointANewManager);

		sections.put("getRequiredCase",getRequiredCase);

		//PUT Query
		
		
		sections.put("space",space);
		sections.put("finish",finish);

		commands.put("space", (section, args) -> {
			MyFileLogWriter.println(""); 
		});

		commands.put("finish", (section, args) -> {
			MyFileLogWriter.saveLogFile();
		});
		
		
		commands.put("addDepartment", (section, args) -> {
			Department department = new Department(Integer.parseInt(args[0]),args[1],court.getRealJudge(Integer.parseInt(args[2])),args[3],Specialization.valueOf(args[4]));
			if(court.addDepartment(department)) 
				MyFileLogWriter.println("successfully added department "+department.getNumber());
			else
				MyFileLogWriter.println("failed to add department "+department.getNumber());
			if(department.getNumber()==1) {
				MyFileLogWriter.println("Department details: "+department.toString());				
			}
		});
			
		
		commands.put("addLawyer", (sections,args) -> {
			
			Lawyer lawyer = new Lawyer(Integer.parseInt(args[0]),args[1],args[2],UtilsMethods.parseDate(args[3])
					,args[4],args[5],args[6],Gender.valueOf(args[7]),Specialization.valueOf(args[8]),
							Integer.parseInt(args[9]),Double.parseDouble(args[10]));
				if(court.addLawyer(lawyer))
				MyFileLogWriter.println("successfully added lawyer "+lawyer.getId());
			else
				MyFileLogWriter.println("failed to add lawyer "+lawyer.getId());
			//f7
			if(lawyer.getId()==81) {
				MyFileLogWriter.println("lawyer details: "+lawyer.toString());
			}
			
		});
		/*
		 * public Accused(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, Gender gender, String job) {
		 */
		commands.put("addAccused", (sections,args) -> {
			
			Accused accused = new Accused(Integer.parseInt(args[0]),args[1],args[2],UtilsMethods.parseDate(args[3])
					,args[4],args[5],args[6],Gender.valueOf(args[7]),args[8]);
		if(court.addAccused(accused))
				MyFileLogWriter.println("successfully added Accused "+accused.getId());
			else
				MyFileLogWriter.println("failed to add Accused "+accused.getId());
			//f7
			if(accused.getId()==7) {
				MyFileLogWriter.println("Accused details: "+accused.toString());
			}
			
		});
		
		commands.put("addJudge", (sections,args) -> {
			Judge judge = new Judge(Integer.parseInt(args[0]),args[1],args[2],UtilsMethods.parseDate(args[3])
					,args[4],args[5],args[6],Gender.valueOf(args[7]),Specialization.valueOf(args[8]),
							Integer.parseInt(args[9]),Double.parseDouble(args[10]),Integer.parseInt(args[11]));
				if(court.addJudge(judge))
				MyFileLogWriter.println("successfully added judge "+judge.getId());
			else
				MyFileLogWriter.println("failed to add judge "+judge.getId());
			//f7
			if(judge.getId()==8) {
				MyFileLogWriter.println("Judge details: "+judge.toString());
			}
		});
		
		commands.put("addCase", (sections,args) -> {
			Case casee = new Case(court.getRealAccused(Integer.parseInt(args[0])),UtilsMethods.parseDate(args[1])
					,Status.valueOf(args[2]),Specialization.valueOf(args[3]),
					court.getRealLawyer(Integer.parseInt(args[4])),null);
			if(court.addCase(casee))
				MyFileLogWriter.println("successfully added Case "+casee.getCode());
			else
				MyFileLogWriter.println("failed to add  Case "+casee.getCode());
			if(casee.getCode().equals("3")) {
				MyFileLogWriter.println("Case details: "+casee.toString());				
			}
		});

		//	public Verdict(int verdictID, String verdictSummary, Date issusedDate, Judge judge, Case casee, Appeal appeal) {

		commands.put("addVerdict", (sections,args) -> {
			Verdict verdict = new Verdict(args[0],UtilsMethods.parseDate(args[1])
					,court.getRealJudge(Integer.parseInt(args[2])),
					court.getRealCase(args[3]));
			if(court.addVerdict(verdict))
				MyFileLogWriter.println("successfully added verdict "+verdict.getVerdictID());
			else
				MyFileLogWriter.println("failed to add verdict "+verdict.getVerdictID());
			if(verdict.getVerdictID()==24) {
				MyFileLogWriter.println("verdict details: "+verdict.toString());				
			}
		});


		commands.put("addLawyerToDepartment", (sections,args) -> {
			if(court.addLawyerToDepartment(court.getRealDepartment(Integer.parseInt(args[1])),court.getRealLawyer(Integer.parseInt(args[0]))))
				MyFileLogWriter.println("successfully added Lawyer "+args[0]+" to Department "+args[1]);
			else
				MyFileLogWriter.println("failed to add Lawyer "+args[0]+" to Department "+args[1]);
		});

		commands.put("addJudgeToDepartment", (sections,args) -> {
			if(court.addJudgeToDepartment(court.getRealDepartment(Integer.parseInt(args[1])),court.getRealJudge(Integer.parseInt(args[0]))))
				MyFileLogWriter.println("successfully added Judge "+args[0]+" to Department "+args[1]);
			else
				MyFileLogWriter.println("failed to add Judge "+args[0]+" to Department "+args[1]);
		});

		commands.put("removeDepartment", (sections,args) -> {
			if(court.removeDepartment(court.getRealDepartment(Integer.parseInt(args[0]))))
				MyFileLogWriter.println("successfully removed Department "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Department "+args[0]);
		});
		
		commands.put("removeLawyer", (sections,args) -> {
			
			if(court.removeLawyer(court.getRealLawyer(Integer.parseInt(args[0]))))
				MyFileLogWriter.println("successfully removed Lawyer "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Lawyer "+args[0]);
		});
		commands.put("removeJudge", (sections,args) -> {
			
			if(court.removeJudge(court.getRealJudge(Integer.parseInt(args[0]))))
				MyFileLogWriter.println("successfully removed Judge "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Judge "+args[0]);
		});
		
		
		commands.put("removeVerdict", (sections,args) -> {
			
			if(court.removeVerdict(court.getRealVerdict(Integer.parseInt(args[0]))))
				MyFileLogWriter.println("successfully removed Verdict "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Verdict "+args[0]);
		});
		
		commands.put("removeAccused", (sections,args) -> {	
			
			if(court.removeAccused(court.getRealAccused(Integer.parseInt(args[0]))))
				MyFileLogWriter.println("successfully removed Accused "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Accused "+args[0]);
		});
		
		commands.put("removeCase", (sections,args) -> {
			
			if(court.removeCase(court.getRealCase(args[0])))
				MyFileLogWriter.println("successfully removed Case "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Case "+args[0]);
		});
		
		
		commands.put("howManyCasesBefore", (sections,args) -> {
				MyFileLogWriter.println("*** The amount of cases before Date is: " + 
			(court.howManyCasesBefore(UtilsMethods.parseDate(args[0]))));
			
		});
		
		commands.put("differenceBetweenTheLatestAndEarliestCase", (sections,args) -> {
			MyFileLogWriter.println("*** The difference Between The Longest Case And The Shortest one is " + 
		(court.differenceBetweenTheLatestAndEarliestCase(court.getRealLawyer(Integer.parseInt(args[0]))))+" days.");
		});
		
		commands.put("printMostExperiencedJudge", (sections,args) -> {
			court.printMostExperiencedJudge();
		});
		
		commands.put("getAccusedWithMaxCasesByLawyers", (sections,args) -> {
			MyFileLogWriter.println("*** The Accused With Max Cases By Lawyers :  \n" + 
		(court.getAccusedWithMaxCasesByLawyers(Specialization.valueOf(args[0])).toString()));
		});
		
		commands.put("lawyersThatWorkWithMoreThanOneCase", (sections, args) -> {
	    	 MyFileLogWriter.println("*** Lawyers That Work In More Than One Department *** ");

		    court.lawyersThatWorkWithMoreThanOneCase().forEach((lawyer, cases) -> {
		        MyFileLogWriter.println("Lawyer: " + lawyer.toString());
		        MyFileLogWriter.println("Departments: " + cases.toString());
		    });
		});

		commands.put("findInactiveCasesCountByDepartment", (sections, args) -> {
	    	MyFileLogWriter.println("*** Inactive Cases Count By Department ***");

		    court.findInActiveCasesCountByDepartment().forEach((department, count) -> {
		        MyFileLogWriter.println("Department number: " + department.getNumber()+ " - "+department.getName() + ", Inactive Cases: " + count);
		    });
		});


		commands.put("appointANewManager", (sections, args) -> {
		    Department department = court.getRealDepartment(Integer.parseInt(args[0])); // Assuming department ID is provided
		    Judge newManager = court.appointANewManager(department);
		    MyFileLogWriter.println("*** The new manager of the department is: " + newManager.toString());
		});


		commands.put("getRequiredCase", (sections, args) -> {
			
			Lawyer l = court.getRealLawyer(77);
			Case reCase1=l.getRequiredCase();
		    MyFileLogWriter.println("*** Required Case for Lawyer 77 is: " + reCase1.toString());


			Accused a = court.getRealAccused(8);
			Case reCase2=a.getRequiredCase();
			
		    MyFileLogWriter.println("*** Required Case for Accused 8 is: " + reCase2.toString());
		});
		
	
	}
		
		
		
	
	
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stu
		MyFileLogWriter.initializeMyFileWriter();

		try{
			List<String[]> input = CSVExporter.importCSV("INPUT.csv");

			for (int i = 1; i < input.size(); i++) {

				//get row
				String[] values = input.get(i);

				if(values.length == 0)
					continue;
				//get command
				String command = values[0];

				//get params
				String[] params = Arrays.copyOfRange(values,1,values.length);

				//send to func 
				try {
					func(command, params);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			System.out.println("All commands executed. Please check \"" + OUTPUT_FILE + "\".");
		}catch (Exception e) {
			System.err.println("Failed to executed all commands.");
			e.printStackTrace();
		}


	}

	private static void func(String command,String[] args){
		//extract command
		Command c = commands.get(command);

		//check that command exists
		if (c != null){
			//get relevant section
			Section section = sections.get(command);

			//execute
			c.execute(section,args);
		}
	}

}
