package com.georgeb.rcpapp.parts;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class GetGitAddress {

	private Text txtInput;
	private TableViewer tableViewer;
	private Table table;
	public static String repoInfo = "No repo yet";
	private Button b1;
	private String gitRepo;
	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		txtInput = new Text(parent, SWT.BORDER);
		txtInput.setMessage("user:password:owner:reponame");
		txtInput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				gitRepo = txtInput.getText();
			}

		});
		
		b1 = new Button(parent, SWT.PUSH);
		b1.setText("set repo");
		b1.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						GitCommands.fork(getGitArgs(txtInput.getText()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//ListOfCommits.setData(cloneAndLog(txtInput.getText()));
					txtInput.setText("user:password:owner:reponame");
					
					break;
				}
			}

		});
		txtInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		table = new Table(parent, SWT.BORDER_SOLID | SWT.BORDER_SOLID);
		table.setHeaderVisible(true);
		TableColumn column1 = new TableColumn(table, SWT.NONE);
		column1.setText("Currently working on repository:");
		TableItem item1 = new TableItem(table, SWT.NULL);
		TableItem item2 = new TableItem(table, SWT.NULL);
		TableItem item3 = new TableItem(table, SWT.NULL);
		item1.setText("No repo");
		item2.setText("Currently working on commit:");
		item3.setText("No commit");
		table.getColumn(0).pack();
		table.setBounds(25, 25, 40, 40);
		tableViewer = new TableViewer(parent);
		tableViewer.add(table);
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		

	}

	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	public static String getInfo() {
		return repoInfo;
	}
	
	private String[] getGitArgs(String input){
		if (input.split(":").length == 4)
		return input.split(":"); 
		else{
			JOptionPane.showMessageDialog(null, "Wrong values. input should be like-> user:password:owner:name:organization", "InfoBox: " + "Input value wrong", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		
	}

	private ArrayList<String> cloneAndLog(String info){
		String[] infos = getGitArgs(info);
		ArrayList<String> commits = new ArrayList<String>();
		try {
//			GitCommands.testClone(uri, localRep);
			GitCommands.testClone("https://github.com/GeorgeBanica1990/rcp-app-testfiles.git", "F:\\Eclipse\\detest");
			commits = GitCommands.getCommits("F:\\Eclipse\\detest");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commits;
	}
}
