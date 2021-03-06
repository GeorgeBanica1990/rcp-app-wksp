package com.georgeb.rcpapp.parts;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class CurrentCommitInfo {

	private TableViewer tableViewer;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.MULTI);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());;
		tableViewer.setInput(createInitialDataModel());
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
	}
	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}
	private List<String> createInitialDataModel() {
		return Arrays.asList("commitinfo 1", "commitinfo 2", "commitinfo 3", "commitinfo 4", "commitinfo 1");
	}
}
