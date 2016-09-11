package com.georgeb.rcpapp.parts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
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

/**
 * Class containing: - text input part containing user:password:owner:reponame -
 * table containing repository name and working commit.
 * 
 * @author GeorgeB
 *
 */
public class GetGitAddress {

	private Text txtInput;
	private TableViewer tableViewer;
	private Table table;
	public static String repoInfo = "No repository forked yet!";
	private Button button;
	private static String username;
	private static String password;
	private static String owner;
	private static String reponame;
	public static TableItem item3;

	/**
	 * Creation of Part Composite
	 * 
	 * @param parent
	 */
	@PostConstruct
	public void createComposite(Composite parent) {

		parent.setLayout(new GridLayout(1, false));
		// tableViewer = new TableViewer(parent);

		txtInput = new Text(parent, SWT.BORDER);
		txtInput.setMessage("user:password:owner:reponame");

		/**
		 * add text listener to get user input. User Input should
		 * contain:user:password:owner:reponame
		 */
		txtInput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				System.out.println("Modifying text");
			}

		});
		table = new Table(parent, SWT.BORDER_SOLID | SWT.BORDER_SOLID);
		table.setHeaderVisible(true);
		TableColumn column1 = new TableColumn(table, SWT.NONE);
		column1.setText("Currently working on repository:");
		TableItem item1 = new TableItem(table, SWT.NULL);
		TableItem item2 = new TableItem(table, SWT.NULL);
		item3 = new TableItem(table, SWT.NULL);
		item1.setText("No repository forked");
		item2.setText("Currently working on commit:");
		item3.setText("No commit. Fork a repository first!");
		table.getColumn(0).pack();
		table.setBounds(25, 25, 40, 40);
		button = new Button(parent, SWT.PUSH);
		button.setText("Fork Repository!");
		/**
		 * add button listener to fork repository and add commits when button is
		 * pushed
		 */
		button.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						setGitArgs(txtInput.getText());
						if (!((username == null) || (password == null) || (owner == null) || (reponame == null))) {
							fork(txtInput.getText());
							// sleep a few seconds for the fork process to
							// finish
							TimeUnit.SECONDS.sleep(3);
							item1.setText(reponame);
							ArrayList<String> data = cloneAndLog(txtInput.getText());
							ListOfCommits.setData(data);

							if (!data.equals(null)) {
								item3.setText(data.get(0).toString());
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					txtInput.setText("");
					txtInput.setMessage("user:password:owner:reponame");
					break;
				}
			}
		});

		// tableViewer.add(table);

		tableViewer = new TableViewer(table);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		txtInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	/**
	 * Set static variables for user, password, owner, reponame used in git
	 * commands.
	 * 
	 * @param input
	 *            - will be split in user, password, owner, reponame. If input
	 *            does not match a message dialog will be displayed
	 */
	private void setGitArgs(String input) {
		if (input.split(":").length == 4) {
			String[] data = input.split(":");
			username = data[0];
			password = data[1];
			owner = data[2];
			reponame = data[3];
		} else {
			JOptionPane.showMessageDialog(null, "Wrong values. input should be like-> user:password:owner:reponame",
					"InfoBox: " + "Input value wrong", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Clones repository and fills information about commits on the repo.
	 * 
	 * @param info
	 * @return ArrayList<String> containing commits
	 */
	private ArrayList<String> cloneAndLog(String info) {
		setGitArgs(info);
		String tempLocationName = System.getProperty("java.io.tmpdir") + "\\eclipse-rcp";
		File tempLocation = new File(tempLocationName);
		String address;
		deleteDirectory(tempLocation);
		ArrayList<String> commits = new ArrayList<String>();
		try {
			boolean success = tempLocation.mkdirs();
			if (success) {
				address = "https://github.com/" + username.toString() + "/" + reponame.toString() + ".git";
				GitCommands.gitCloneRepository(address, tempLocationName);
				commits = GitCommands.getCommits(tempLocationName);
			} else {
				JOptionPane.showMessageDialog(null,
						"Temporary folder for clone of repository could not be created! Restart application and try again.",
						"Error while cloning!", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commits;
	}

	/**
	 * Deletes directory passed.
	 * 
	 * @param directory
	 * @return boolean if deletion was successful or not
	 */
	public static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	/**
	 * fork repository using static variables for user, password, owner,
	 * reponame
	 * 
	 * @param data
	 * @throws IOException
	 */
	public void fork(String data) throws IOException {
		setGitArgs(data);

		if (data.split(":").length == 4) {
			GitHubClient client = new GitHubClient();
			client.setCredentials(username, password);
			RepositoryService service = new RepositoryService(client);
			RepositoryId toBeForked = new RepositoryId(owner, reponame);
			service.forkRepository(toBeForked);
		} else {
			JOptionPane.showMessageDialog(null, "Missing data from the input", "Cannot Fork!",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
