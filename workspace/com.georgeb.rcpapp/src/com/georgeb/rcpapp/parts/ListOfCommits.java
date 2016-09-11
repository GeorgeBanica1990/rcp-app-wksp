package com.georgeb.rcpapp.parts;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableItem;

/**
 * Class containing commits information
 * @author GeorgeB
 *
 */
public class ListOfCommits {
	private static TableViewer tableViewer;
	private static String commitToBeCheckedOut;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.MULTI);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.add(GetGitAddress.repoInfo);
		tableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Menu contextMenu = new Menu(tableViewer.getTable());
		tableViewer.getTable().setMenu(contextMenu);
		MenuItem mItem1 = new MenuItem(contextMenu, SWT.None);
		mItem1.setText("Revert to commit");

		/**
		 * add mouse listener to react when right click is pressed.
		 */
		tableViewer.getTable().addListener(SWT.MouseDown, new Listener() {

			public void handleEvent(Event event) {
				TableItem[] selection = tableViewer.getTable().getSelection();
				if (selection.length != 0 && (event.button == 3)) {
					commitToBeCheckedOut = selection[0].getText().split("\\W+")[0];
					mItem1.setText("Revert to commit " + commitToBeCheckedOut);
					contextMenu.setVisible(true);

				}

			}

		});
		
		/**
		 * add listener to table cell to retrieve commit that will be checked out.
		 */
		mItem1.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

				JOptionPane.showMessageDialog(null, "Commit will be reverted", "Revert Commit",
						JOptionPane.INFORMATION_MESSAGE);
				GitCommands.gitCheckoutCommit(commitToBeCheckedOut);
				GetGitAddress.item3.setText(commitToBeCheckedOut);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Fill table with commit information
	 * @param data
	 */
	public static void setData(ArrayList<String> data) {
		tableViewer.clear(0);
		for (String commit : data) {
			tableViewer.add(commit);
			System.out.println(commit);

		}

	}

	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

}