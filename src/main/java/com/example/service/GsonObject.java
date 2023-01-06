package com.example.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.payload.ExpectedJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GsonObject {
	static ExpectedJson jsonObj[] = null;
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		//ExpectedJson jsonObj[] = null;
		try {
			jsonObj = mapper.readValue(new FileInputStream("D:\\home\\jsonfile\\expected.json"), ExpectedJson[].class);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}

		ArrayList<ExpectedJson> al = new ArrayList<>();
		System.out.println("Q1.Read the attached json file and display its content.");
		for (ExpectedJson ejsonOject : jsonObj) {
			System.out.println("Id is: " + ejsonOject.getId());
			System.out.println("InterfaceName is: " + ejsonOject.getInterface().getInterfaceName());
			System.out.println("InterfaceDescription is: " + ejsonOject.getInterface().getInterfaceDescription());
			System.out.println("Priority is: " + ejsonOject.getPriority());
			System.out.println("isActive is: " + ejsonOject.isActive());
			System.out.println("From is: " + ejsonOject.getFrom());
			System.out.println("To is: " + ejsonOject.getTo());
			System.out.println("Expected is: " + ejsonOject.getExpected());
			System.out.println("Relation is: " + ejsonOject.getRelation());
			System.out.println("----------------");

			al.add(ejsonOject);

		}	
		System.out.println("===========================================================");

		System.out.println("Q2. List all the interfaces’ names sorted by their priority.");
		final Function<ExpectedJson, Integer> byPriority = person -> person.getPriority();   
		final Function<ExpectedJson, String> byTheirName = person -> person.getInterface().getInterfaceName();
		List<ExpectedJson> sortedlist =   al.stream()
				.sorted(Comparator.comparing(byPriority).thenComparing(byTheirName))
				.collect(Collectors.toList());
		for(ExpectedJson sl : sortedlist) {
			System.out.println(sl.getInterface().getInterfaceName());
		}
		System.out.println("===========================================================");

		System.out.println("Q3. List all the active interfaces’ names for which the interval includes the current timestamp.");        
		for(int i=0;i<al.size();i++) {
			if(al.get(i).isActive()) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
				String st = al.get(i).getFrom();
				String et = al.get(i).getTo();
				LocalTime startTime = LocalTime.parse(st, format);
				LocalTime endTime = LocalTime.parse(et, format);
				LocalTime targetTime = LocalTime.now();
				if(targetTime.isBefore(endTime) && targetTime.isAfter(startTime)) {
					System.out.println(al.get(i).getInterface().getInterfaceName());
				}
			}
		}
		System.out.println("===========================================================");

		System.out.println("Q4. Build the following method which takes the number of transferred files and the time interval in which they were transferred and list all the active interfaces along with their status (success or error) if the relation is met or not");                
		/*String st= null;
		String et= null ; 
		int noOfExceptedFiles = 0;
		System.out.println("Please enter the number of transfered files: ");
		Scanner sc = new Scanner(System.in);
		Integer noOfFiles = sc.nextInt();
		for(int i=0;i<al.size();i++) {
			if(al.get(i).isActive()) {
				 st = al.get(i).getFrom();
				 et = al.get(i).getTo();
				 noOfExceptedFiles =al.get(i).getExpected();
				 checkTransfer(noOfExceptedFiles,st,et);
			}
		}

	}
	static void checkTransfer(int expectedFiles,String from, String to) {


	}
		 */
		checkTransfer(4, "13:00", "15:00");
	}
	static void checkTransfer(int noOfFiles, String from, String to) {
		for (ExpectedJson ejsonObject : jsonObj) {
			if ( ejsonObject.isActive()){
				if ((ejsonObject.getRelation().equals("==")&& ejsonObject.getExpected() == noOfFiles) || 
						(ejsonObject.getRelation().equals(">=")&& ejsonObject.getExpected() <= noOfFiles) || 
						( ejsonObject.getRelation().equals("<=")&& ejsonObject.getExpected() >= noOfFiles) ){
					System.out.println(ejsonObject.getInterface().getInterfaceName().concat("-success"));   	
				}
				else{
					System.out.println(ejsonObject.getInterface().getInterfaceName().concat("-error"));
				}
			}
		}
	}
}
