package com.georgeb.testgitcommands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.georgeb.rcpapp.parts.GitCommands;
import org.eclipse.jgit.api.errors.GitAPIException;

public class Testing {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String[] lista = { "GeorgeBanica1990", "repositoryserios", "octocat", "Spoon-Knife" };
		// GitCommands.fork(lista);
		String owner = "octocat";
		String name = "Spoon-Knife";
//		try {
//			GitCommands.testClone("https://github.com/GeorgeBanica1990/rcp-app-testfiles.git", "F:\\Eclipse\\detest");
//			System.out.print("Success!!");
//		} catch (GitAPIException e) {
//			// TODO Auto-generated catch block
//			System.out.println("eroare");
//			e.printStackTrace();
//		}
		try {
			ArrayList<String> commits = GitCommands.getCommits("F:\\Eclipse\\detest");
			for(String commit: commits){
				System.out.println(commit);
			}
			
			System.out.print("YUPII");
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			System.out.println("eroare");
			e.printStackTrace();
	}
	}
}
