package com.georgeb.rcpapp.parts;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ListOfCommits {
	//private Text txtInput;
			private static TableViewer tableViewer;

			@PostConstruct
			public void createComposite(Composite parent) {
				parent.setLayout(new GridLayout(1, false));

				tableViewer = new TableViewer(parent, SWT.MULTI);

				tableViewer.setContentProvider(ArrayContentProvider.getInstance());;
				//tableViewer.setInput(createInitialDataModel());
				tableViewer.add(GetGitAddress.repoInfo);
				tableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			}
			public static void setData(ArrayList<String> data){
				tableViewer.clear(0);
				for(String commit : data){
					tableViewer.add(commit);
					
				}
				
				
				
			}
			@Focus
			public void setFocus() {
				tableViewer.getTable().setFocus();
			}
			

	}