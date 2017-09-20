import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class Frontend 
{	
	private JFrame firstframe, professorframe, ploginframe;
	private JRadioButton LoginasProf, LoginasStud;
	private JPanel professorpanel, ploginpanel;
	private JButton ViewCourses, AddCourse;
	private JTextArea tp_id;
	private JTextArea tp_pass;
	private JButton ploginb;
	private JFrame sloginframe;
	private JPanel sloginpanel;
	private JTextArea ts_id;
	private JTextArea ts_pass;
	private JButton sloginb;
	private JFrame studentframe;
	private JPanel studentpanel;
	private JButton OngoingCourses;
	private JButton EnrollForCourse;
	private JButton CompletedCourses;
	private JButton plogout;
	private JButton slogout;
	private JButton initial_back;
	private JButton pchangep, schangep;
	private JFrame addcourseframe;
	private JPanel addcoursepanel;
	private JTextArea tc_id, tc_name, tdomain;
	private JButton addcourseb, cancel_1;
	private JPanel pcppanel, scppanel, pviewpanel, ongoingpanel, completedpanel, sdeletepanel, gradepanel, pdeletepanel;
	private JFrame pcpframe, scpframe, pviewframe, ongoingframe, completedframe, sdeleteframe, gradeframe, pdeleteframe;
	private JButton pchangepb, cancel_2, schangepb, cancel_3, unenrollb, cancel_4, cancel_5, cancel_6, gradeb, cancel_7, proceedgradeb, deletecb, pdeleteb, sdeleteb, cancel_8, cancel_9;
	private JTextArea tppass_new, tppass_new_re,sppass_new, sppass_new_re  ;
	private JComboBox domains, grades, courses, students;
	private JButton viewstudents;
	String PID, SID, cdomain;
	
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/Student_Course_Management";
	static final String USER = "root";
	static final String PASS = "Praniti123!";
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to the database ... ");
			
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			Frontend window = new Frontend();
			window.firstframe.setVisible(true);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public Frontend() {
		initial();
	}
	private void initial() {
	//initial frame the one which decided student or professor
		firstframe = new JFrame();
		firstframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		firstframe.setTitle("LOG-IN PAGE");
		firstframe.setForeground(UIManager.getColor("Button.darkShadow"));
		firstframe.setSize(new Dimension(400,100));
		firstframe.setResizable(false);
		firstframe.setLocationRelativeTo(null);
		firstframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel firstpanel = new JPanel();
		LoginasProf = new JRadioButton("Log-in as Professor",false);
		LoginasStud = new JRadioButton("Log-in as Student",false);

		LoginasProf.addActionListener(secondwindow);
		LoginasStud.addActionListener(secondwindow);
		
		ButtonGroup group = new ButtonGroup();
		group.add(LoginasProf);
		group.add(LoginasStud);
		
		firstpanel.setLayout(new BoxLayout(firstpanel,BoxLayout.PAGE_AXIS));
		firstpanel.add(Box.createRigidArea(new Dimension(0,10)));
		firstpanel.add(Box.createVerticalGlue());
		firstpanel.add(LoginasProf);
		firstpanel.add(Box.createVerticalGlue());
		firstpanel.add(LoginasStud);
		
		firstframe.add(firstpanel);
		firstframe.setVisible(true);
	}
	//action-listener for button , takes to respective login page
	ActionListener secondwindow = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(LoginasProf)){Prof_login();}
			else if (e.getSource().equals(LoginasStud)){Stud_login();}
		}		
	};
	public void Prof_frame() {
		ploginframe.setVisible(false);
		professorframe = new JFrame();
		professorframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		professorframe.setTitle("PROFESSOR'S PAGE");
		professorframe.setForeground(UIManager.getColor("Button.darkShadow"));
		professorframe.setSize(new Dimension(400,300));
		professorframe.setResizable(false);
		professorframe.setLocationRelativeTo(null);
		professorframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ViewCourses = new JButton ("View Courses under you");
		AddCourse = new JButton ("Add new Course under you");
		plogout = new JButton ("Log out");
		pchangep = new JButton ("Change Password");
		
		ViewCourses.setAlignmentX(Component.CENTER_ALIGNMENT);
		ViewCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {p_view_course();}});           
		AddCourse.setAlignmentX(Component.CENTER_ALIGNMENT);
		AddCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {add_course();}});          
		plogout.setAlignmentX(Component.CENTER_ALIGNMENT);
		plogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				professorframe.setVisible(false);
				initial();
			}});
		pchangep.setAlignmentX(Component.CENTER_ALIGNMENT);
		pchangep.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) { p_change_password();}});
		
		professorpanel = new JPanel();
		professorpanel.setLayout(new BoxLayout(professorpanel,BoxLayout.PAGE_AXIS));
		professorpanel.setAlignmentX(Component.TOP_ALIGNMENT);
		professorpanel.add(Box.createRigidArea(new Dimension(0,20)));
		professorpanel.add(ViewCourses);
		professorpanel.add(Box.createRigidArea(new Dimension(0,20)));
		professorpanel.add(AddCourse);
		professorpanel.add(Box.createRigidArea(new Dimension(0,20)));
		professorpanel.add(pchangep);
		professorpanel.add(Box.createRigidArea(new Dimension(0,20)));
		professorpanel.add(plogout);
		professorframe.add(professorpanel);
		professorframe.setVisible(true);
		
	}
	
	public void Stud_frame() {
		sloginframe.setVisible(false);
		studentframe = new JFrame();
		studentframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		studentframe.setTitle("STUDENT'S PAGE");
		studentframe.setForeground(UIManager.getColor("Button.darkShadow"));
		studentframe.setSize(new Dimension(400,300));
		studentframe.setResizable(false);
		studentframe.setLocationRelativeTo(null);
		studentframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		OngoingCourses = new JButton ("Ongoing Courses");
		CompletedCourses = new JButton ("Completed Courses");
		EnrollForCourse = new JButton ("Enroll for New Course");
		slogout = new JButton ("Log Out");
		schangep = new JButton ("Change Password");
		
		OngoingCourses.setAlignmentX(Component.CENTER_ALIGNMENT);
		OngoingCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {ongoing_courses();}});
		CompletedCourses.setAlignmentX(Component.CENTER_ALIGNMENT);
		CompletedCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {completed_courses();}});
		EnrollForCourse.setAlignmentX(Component.CENTER_ALIGNMENT);
		EnrollForCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {choose_domain();}});
		slogout.setAlignmentX(Component.CENTER_ALIGNMENT);
		slogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentframe.setVisible(false);
				initial();
			}});
		schangep.setAlignmentX(Component.CENTER_ALIGNMENT);
		schangep.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) { s_change_password();}});
		
		studentpanel = new JPanel();
		studentpanel.setLayout(new BoxLayout(studentpanel,BoxLayout.PAGE_AXIS));
		studentpanel.setAlignmentX(Component.TOP_ALIGNMENT);
		studentpanel.add(Box.createRigidArea(new Dimension(0,20)));
		studentpanel.add(OngoingCourses);
		studentpanel.add(Box.createRigidArea(new Dimension(0,20)));
		studentpanel.add(CompletedCourses);
		studentpanel.add(Box.createRigidArea(new Dimension(0,20)));
		studentpanel.add(EnrollForCourse);
		studentpanel.add(Box.createRigidArea(new Dimension(0,20)));
		studentpanel.add(schangep);
		studentpanel.add(Box.createRigidArea(new Dimension(0,20)));
		studentpanel.add(slogout);
		studentframe.add(studentpanel);
		studentframe.setVisible(true);
	}
	
	public void Prof_login() {
		firstframe.setVisible(false);
		ploginframe = new JFrame();
		ploginframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		ploginframe.setTitle("PROFESSOR'S LOG IN PAGE");
		ploginframe.setForeground(UIManager.getColor("Button.darkShadow"));
		ploginframe.setSize(new Dimension(400,350));
		ploginframe.setResizable(false);
		ploginframe.setLocationRelativeTo(null);
		ploginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ploginpanel = new JPanel();
		ploginpanel.setLayout(new BoxLayout(ploginpanel,BoxLayout.PAGE_AXIS));
		ploginpanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		JLabel p_id = new JLabel("Professor ID:");
		p_id.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel p_pass = new JLabel("Password:");
		p_pass.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tp_id = new JTextArea();
		tp_id.setFont(new Font("Serif",Font.BOLD,14));
		tp_id.setLineWrap(true);
		tp_id.setWrapStyleWord(true);
		tp_id.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tp_pass = new JTextArea();
		tp_pass.setFont(new Font("Serif",Font.BOLD,14));
		tp_pass.setLineWrap(true);
		tp_pass.setWrapStyleWord(true);
		tp_pass.setAlignmentX(Component.CENTER_ALIGNMENT);
		tp_pass.setSize(400, 50);
		
		ploginb = new JButton("LOGIN");
		ploginb.setAlignmentX(Component.CENTER_ALIGNMENT);
		initial_back = new JButton("BACK");
		initial_back.setAlignmentX(Component.CENTER_ALIGNMENT);
		ploginb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//add authentication query
				PID = tp_id.getText();
				String PPW = tp_pass.getText();
				Connection conn = null;
				Statement stmt = null;
				try {
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					stmt = conn.createStatement();
				String query = "select Password from Professor where P_id=" + PID;
				ResultSet rs = stmt.executeQuery(query);
				rs.next();
				String dbpassword = rs.getString("Password");
				  if(dbpassword.equals(PPW)) {
				JOptionPane.showMessageDialog(null,"Succesful" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
				Prof_frame();
				  }
				  else JOptionPane.showMessageDialog(null,"Incorrect Password" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e1)
				{
					System.out.println(e1);
				}
			}});  
		initial_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ploginframe.setVisible(false);
				initial();}}); 
		ploginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		ploginpanel.add(p_id);
		ploginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		ploginpanel.add(tp_id);
		ploginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		ploginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		ploginpanel.add(p_pass);
		ploginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		ploginpanel.add(tp_pass);
		ploginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		ploginpanel.add(ploginb);
		ploginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		ploginpanel.add(initial_back);
		
		ploginframe.add(ploginpanel);
		ploginframe.setVisible(true);
	}
	
	public void Stud_login() {
		firstframe.setVisible(false);
		sloginframe = new JFrame();
		sloginframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		sloginframe.setTitle("STUDENT'S LOG IN PAGE");
		sloginframe.setForeground(UIManager.getColor("Button.darkShadow"));
		sloginframe.setSize(new Dimension(400,350));
		sloginframe.setResizable(false);
		sloginframe.setLocationRelativeTo(null);
		sloginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		sloginpanel = new JPanel();
		sloginpanel.setLayout(new BoxLayout(sloginpanel,BoxLayout.PAGE_AXIS));
		sloginpanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		JLabel s_id = new JLabel("Student's ID:");
		s_id.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel s_pass = new JLabel("Password:");
		s_pass.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		ts_id = new JTextArea();
		ts_id.setFont(new Font("Serif",Font.BOLD,14));
		ts_id.setLineWrap(true);
		ts_id.setWrapStyleWord(true);
		ts_id.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		ts_pass = new JTextArea();
		ts_pass.setFont(new Font("Serif",Font.BOLD,14));
		ts_pass.setLineWrap(true);
		ts_pass.setWrapStyleWord(true);
		ts_pass.setAlignmentX(Component.CENTER_ALIGNMENT);
		ts_pass.setSize(400, 50);
		
		sloginb = new JButton("LOGIN");
		sloginb.setAlignmentX(Component.CENTER_ALIGNMENT);
		sloginb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//add authentication query
				SID = ts_id.getText();
				String PPW = ts_pass.getText();
				Connection conn = null;
				Statement stmt = null;
				try {
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					stmt = conn.createStatement();
				String query = "select Password from Student where S_id=" + SID;
				ResultSet rs = stmt.executeQuery(query);
				rs.next();
				String dbpassword = rs.getString("Password");
				  if(dbpassword.equals(PPW)) {
				JOptionPane.showMessageDialog(null,"Succesful" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
				Stud_frame();
				  }
				  else JOptionPane.showMessageDialog(null,"Incorrect Password" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e1)
				{
					System.out.println(e1);
				}				
			}});    
		initial_back = new JButton("BACK");
		initial_back.setAlignmentX(Component.CENTER_ALIGNMENT);
		initial_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sloginframe.setVisible(false);
				initial();}});
		sloginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		sloginpanel.add(s_id);
		sloginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		sloginpanel.add(ts_id);
		sloginpanel.add(Box.createRigidArea(new Dimension(0,20)));
		sloginpanel.add(s_pass);
		sloginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		sloginpanel.add(ts_pass);
		sloginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		sloginpanel.add(sloginb);
		sloginpanel.add(Box.createRigidArea(new Dimension(0,10)));
		sloginpanel.add(initial_back);
		
		sloginframe.add(sloginpanel);
		sloginframe.setVisible(true);
	}
	
	public void add_course() {
		//page to add course details, okay button with message dialog, cancel button to go back
		professorframe.setVisible(false);
		addcourseframe = new JFrame();
		addcourseframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		addcourseframe.setTitle("ADD A NEW COURSE");
		addcourseframe.setForeground(UIManager.getColor("Button.darkShadow"));
		addcourseframe.setSize(new Dimension(400,350));
		addcourseframe.setResizable(false);
		addcourseframe.setLocationRelativeTo(null);
		addcourseframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addcoursepanel = new JPanel();
		addcoursepanel.setLayout(new BoxLayout(addcoursepanel,BoxLayout.PAGE_AXIS));
		addcoursepanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		JLabel c_name = new JLabel("Course's Name:");
		c_name.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel domain = new JLabel("Domain:");
		domain.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tc_name = new JTextArea();
		tc_name.setFont(new Font("Serif",Font.BOLD,14));
		tc_name.setLineWrap(true);
		tc_name.setWrapStyleWord(true);
		tc_name.setAlignmentX(Component.CENTER_ALIGNMENT);
		tc_name.setSize(400, 50);
		
//		tdomain = new JTextArea();                        //make domain scroll if possible
//		tdomain.setFont(new Font("Serif",Font.BOLD,14));
//		tdomain.setLineWrap(true);
//		tdomain.setWrapStyleWord(true);
//		tdomain.setAlignmentX(Component.CENTER_ALIGNMENT);
		//String cdomain;
		String[] domain_types = { "Computer", "Electronics", "Languages", "Humanities"};
		domains = new JComboBox(domain_types);
		domains.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent e)	{
				cdomain = (String)domains.getSelectedItem();
				tdomain.setText(cdomain);
			}
		});
		
		
		addcourseb = new JButton("ADD COURSE");
		addcourseb.setAlignmentX(Component.CENTER_ALIGNMENT);
		addcourseb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//add the course to the database query
				//if case to check if c_id already exists
				String cname = tc_name.getText();
				String cdomain = tdomain.getText();
				Connection conn = null;
				Statement stmt = null;
				try {
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					stmt = conn.createStatement();
					//This does not work.
					String query5 = "insert into Courses(C_Name, P_id, Domain) "+ "values(?,?,?)";
			  		stmt = conn.prepareStatement(query5);
			  		PreparedStatement s1= conn.prepareStatement(query5);
			  		(s1).setString(1,cname);
		  			(s1).setString(2,PID);
		  			(s1).setString(3,cdomain);
					int i = stmt.executeUpdate(query5);
					if(i!=-1)
			  			{
			  			JOptionPane.showMessageDialog(null,"Succesful" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
						addcourseframe.setVisible(false);
						Prof_frame();
			  			}
			  		else JOptionPane.showMessageDialog(null,"Not Succesful" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e1)
				{
					System.out.println(e1);
				}
				JOptionPane.showMessageDialog(null,"Succesful" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
				addcourseframe.setVisible(false);
				Prof_frame();
			}});    
		cancel_1 = new JButton("CANCEL");
		cancel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addcourseframe.setVisible(false);
				Prof_frame();}});
		addcoursepanel.add(Box.createRigidArea(new Dimension(0,20)));
		addcoursepanel.add(c_name);
		addcoursepanel.add(Box.createRigidArea(new Dimension(0,10)));
		addcoursepanel.add(tc_name);
		addcoursepanel.add(Box.createRigidArea(new Dimension(0,20)));
		addcoursepanel.add(domain);
//		addcoursepanel.add(Box.createRigidArea(new Dimension(0,10)));
//		addcoursepanel.add(tdomain);
		addcoursepanel.add(Box.createRigidArea(new Dimension(0,10)));
		addcoursepanel.add(domains);
		addcoursepanel.add(Box.createRigidArea(new Dimension(0,10)));
		addcoursepanel.add(addcourseb);
		addcoursepanel.add(Box.createRigidArea(new Dimension(0,10)));
		addcoursepanel.add(cancel_1);
		
		addcourseframe.add(addcoursepanel);
		addcourseframe.setVisible(true);
	}
	
	public void p_view_course() {
		//view of courses, buttons to grade students in a course and to delete a course, back button
		professorframe.setVisible(false);
		pviewframe = new JFrame();
		pviewframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		pviewframe.setTitle("LOG-IN PAGE");
		pviewframe.setForeground(UIManager.getColor("Button.darkShadow"));
		pviewframe.setSize(new Dimension(400,500));
		pviewframe.setResizable(false);
		pviewframe.setLocationRelativeTo(null);
		pviewframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pviewpanel = new JPanel();
		pviewpanel.setLayout(new BoxLayout(pviewpanel,BoxLayout.PAGE_AXIS));
		pviewpanel.setAlignmentX(Component.TOP_ALIGNMENT);

		JTable p_c_table = new JTable(new DefaultTableModel(new Object[]{"Course ID", "Course Name", "Domain", "Availability"}, 0));
		DefaultTableModel model = (DefaultTableModel) p_c_table.getModel();
		model.addRow(new Object[]{"Course ID", "Course Name", "Domain", "Availability"});
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
		String query2 = "select * from Courses where P_id=" + PID;
		ResultSet rs1 = stmt.executeQuery(query2);
		while(rs1.next())
		  {
			  String CName = rs1.getString("C_Name");
			  String CID = rs1.getString("C_id");
			  String domain = rs1.getString("Domain");
			  String avail = rs1.getString("Availability");
			  model.addRow(new Object[]{CID, CName, domain, avail});
		  }
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}
		
		cancel_6 = new JButton("BACK");
		cancel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pviewframe.setVisible(false);
				Prof_frame();}});
		
		gradeb = new JButton("GRADE A STUDENT");
		gradeb.setAlignmentX(Component.CENTER_ALIGNMENT);
		gradeb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pviewframe.setVisible(false);
				grade_student();}});
		
		deletecb = new JButton("DELETE A COURSE");
		deletecb.setAlignmentX(Component.CENTER_ALIGNMENT);
		deletecb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pviewframe.setVisible(false);
				prof_choose_course_todelete();}});
		
		pviewpanel.add(p_c_table);
		pviewpanel.add(gradeb);
		pviewpanel.add(deletecb);
		pviewpanel.add(cancel_6);
		pviewframe.add(pviewpanel);
		pviewframe.setVisible(true);
	}

	public void grade_student() {
		//three scrolls - one for courses, second for students enrolled in that course, third for grades -A+,A,B+,.., proceed grading and back buttons
		pviewframe.setVisible(false);
		gradeframe = new JFrame();
		gradeframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		gradeframe.setTitle("GRADE A STUDENT");
		gradeframe.setForeground(UIManager.getColor("Button.darkShadow"));
		gradeframe.setSize(new Dimension(400,350));
		gradeframe.setResizable(false);
		gradeframe.setLocationRelativeTo(null);
		gradeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gradepanel = new JPanel();
		gradepanel.setLayout(new BoxLayout(gradepanel,BoxLayout.PAGE_AXIS));
		gradepanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		String[] grade = { "A+", "A-", "B+", "B-", "C+", "C-", "D+", "D-", "F"};
		grades = new JComboBox(grade);
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
		String query2 = "select * from Courses where P_id=" + PID;
		ResultSet rs1 = stmt.executeQuery(query2);
		String[] CName = new String[20];
		
		int i=0;
		while(rs1.next())
		  {
			  CName[i]=(rs1.getString("C_Name"));
			  i++;
		  }
		courses = new JComboBox(CName);
		String selected_CName = courses.getSelectedItem().toString();
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}
		
		viewstudents = new JButton("View Students");
		viewstudents.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewstudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				Connection conn = null;
				Statement stmt = null;
				try {
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					stmt = conn.createStatement();
				String selected_CName = courses.getSelectedItem().toString();
				String query3 = "select s.S_id, s.S_name, c.Grade from Student s, Course_Taken c, Courses co where s.S_id = c.S_id and co.C_Name=\"" + selected_CName + "\"";						  
				  ResultSet rs2 = stmt.executeQuery(query3); int i=0;
				  String[] SName = new String[20];
				  while(rs2.next())
				  {
					  String sid = rs2.getString("S_id");
					  SName[i]= rs2.getString("S_name");
					  String sgrade = rs2.getString("Grade");
					  System.out.println(sid + " " + SName[i] + " " + sgrade);
					  i++;
				  }
				  students = new JComboBox(SName);
					gradepanel.add(students);
				}
				catch(Exception e1)
				{
					System.out.println(e1);
				}
			}
		});
		
		proceedgradeb = new JButton("PROCEED GRADING");
		proceedgradeb.setAlignmentX(Component.CENTER_ALIGNMENT);
		proceedgradeb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gradeframe.setVisible(false);
				//add grade and status of course as completed
				JOptionPane.showMessageDialog(null,"Graded" , "Action Complete", JOptionPane.INFORMATION_MESSAGE);
				Prof_frame();}});
		
		cancel_7 = new JButton("BACK");
		cancel_7.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gradeframe.setVisible(false);
				Prof_frame();}});
		
		//define 3 scrolls
		//ongoingpanel.add(Scroll_name_course);
		//ongoingpanel.add(Scroll_name_student);
		//ongoingpanel.add(Scroll_name_grade);
		gradepanel.add(proceedgradeb);
		gradepanel.add(cancel_7);
		gradepanel.add(grades);
		gradepanel.add(courses);		
		gradepanel.add(viewstudents);
//		gradepanel.add(students);
		gradeframe.add(gradepanel);
		gradeframe.setVisible(true);
	}
	
	public void prof_choose_course_todelete() {
		//one scroll with courses, proceed deleting button and back button
		pviewframe.setVisible(false);
		pdeleteframe = new JFrame();
		pdeleteframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		pdeleteframe.setTitle("DELETE A COURSE");
		pdeleteframe.setForeground(UIManager.getColor("Button.darkShadow"));
		pdeleteframe.setSize(new Dimension(400,350));
		pdeleteframe.setResizable(false);
		pdeleteframe.setLocationRelativeTo(null);
		pdeleteframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pdeletepanel = new JPanel();
		pdeletepanel.setLayout(new BoxLayout(pdeletepanel,BoxLayout.PAGE_AXIS));
		pdeletepanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		pdeleteb = new JButton("PROCEED DELETING");
		pdeleteb.setAlignmentX(Component.CENTER_ALIGNMENT);
		pdeleteb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				//delete the course 
				JOptionPane.showMessageDialog(null,"Course Deleted" , "Action Complete", JOptionPane.INFORMATION_MESSAGE);
				pdeleteframe.setVisible(false);
				p_view_course();}});
		
		cancel_9 = new JButton("BACK");
		cancel_9.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pdeleteframe.setVisible(false);
				p_view_course();}});
		
		//define 1 scroll
		//sdeletepanel.add(Scroll_name_course);
		pdeletepanel.add(sdeleteb);
		pdeletepanel.add(cancel_9);
		
		pdeleteframe.add(pdeletepanel);
		pdeleteframe.setVisible(true);
	}
	
	public void ongoing_courses() {
		//list ongoing courses, button to un-enroll, back button
		studentframe.setVisible(false);
		ongoingframe = new JFrame();
		ongoingframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		ongoingframe.setTitle("YOUR ONGOING COURSES");
		ongoingframe.setForeground(UIManager.getColor("Button.darkShadow"));
		ongoingframe.setSize(new Dimension(400,350));
		ongoingframe.setResizable(false);
		ongoingframe.setLocationRelativeTo(null);
		ongoingframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ongoingpanel = new JPanel();
		ongoingpanel.setLayout(new BoxLayout(ongoingpanel,BoxLayout.PAGE_AXIS));
		ongoingpanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		unenrollb = new JButton("UN-ENROLL");
		unenrollb.setAlignmentX(Component.CENTER_ALIGNMENT);
		unenrollb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ongoingframe.setVisible(false);
				stud_choose_course_todelete();}});
		
		cancel_4 = new JButton("BACK");
		cancel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ongoingframe.setVisible(false);
				Stud_frame();}});
		
		//define a table to display the courses
		//ongoingpanel.add(Table_name);
		ongoingpanel.add(unenrollb);
		ongoingpanel.add(cancel_4);
		
		ongoingframe.add(ongoingpanel);
		ongoingframe.setVisible(true);
	}
	
	public void completed_courses() {
		//list completed courses with grade, button to go back
		studentframe.setVisible(false);
		completedframe = new JFrame();
		completedframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		completedframe.setTitle("YOUR COMPLETED COURSES");
		completedframe.setForeground(UIManager.getColor("Button.darkShadow"));
		completedframe.setSize(new Dimension(400,350));
		completedframe.setResizable(false);
		completedframe.setLocationRelativeTo(null);
		completedframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		completedpanel = new JPanel();
		completedpanel.setLayout(new BoxLayout(completedpanel,BoxLayout.PAGE_AXIS));
		completedpanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		cancel_5 = new JButton("BACK");
		cancel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				completedframe.setVisible(false);
				Stud_frame();}});
		
		//define a table to display the courses
		//ongoingpanel.add(Table_name);
		completedpanel.add(cancel_5);
		
		completedframe.add(completedpanel);
		completedframe.setVisible(true);
	}
	
	public void stud_choose_course_todelete() {
		//one scroll down which displays all the names of the courses student is enrolled for, proceed quitting and back buttons)
		ongoingframe.setVisible(false);
		sdeleteframe = new JFrame();
		sdeleteframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		sdeleteframe.setTitle("UN-ENROLL FROM A COURSE");
		sdeleteframe.setForeground(UIManager.getColor("Button.darkShadow"));
		sdeleteframe.setSize(new Dimension(400,350));
		sdeleteframe.setResizable(false);
		sdeleteframe.setLocationRelativeTo(null);
		sdeleteframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		sdeletepanel = new JPanel();
		sdeletepanel.setLayout(new BoxLayout(sdeletepanel,BoxLayout.PAGE_AXIS));
		sdeletepanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		sdeleteb = new JButton("PROCEED UNENROLLMENT");
		sdeleteb.setAlignmentX(Component.CENTER_ALIGNMENT);
		sdeleteb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				//delete the course 
				JOptionPane.showMessageDialog(null,"Un-enrolled" , "Action Complete", JOptionPane.INFORMATION_MESSAGE);
				sdeleteframe.setVisible(false);
				ongoing_courses();}});
		
		cancel_8 = new JButton("BACK");
		cancel_8.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sdeleteframe.setVisible(false);
				ongoing_courses();}});
		
		//define 1 scroll
		//sdeletepanel.add(Scroll_name_course);
		sdeletepanel.add(sdeleteb);
		sdeletepanel.add(cancel_8);
		
		sdeleteframe.add(sdeletepanel);
		sdeleteframe.setVisible(true);
	}
	
	public void choose_domain() {
		//choose domain (drop down scroll implementation), okay button, back button
	}
	
	public void s_view_course() {
		//view queried courses, enroll button, back button 
	}
	
	public void p_change_password(){
		//ask new password, update in database, successful pop-up, back
		professorframe.setVisible(false);
		pcpframe = new JFrame();
		pcpframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		pcpframe.setTitle("RE-SET PASSWORD");
		pcpframe.setForeground(UIManager.getColor("Button.darkShadow"));
		pcpframe.setSize(new Dimension(400,350));
		pcpframe.setResizable(false);
		pcpframe.setLocationRelativeTo(null);
		pcpframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pcppanel = new JPanel();
		pcppanel.setLayout(new BoxLayout(pcppanel,BoxLayout.PAGE_AXIS));
		pcppanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		JLabel ppass_new = new JLabel("Enter new Password:");
		ppass_new.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel ppass_new_re = new JLabel("Re-enter new Password:");
		ppass_new_re.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tppass_new = new JTextArea();
		tppass_new.setFont(new Font("Serif",Font.BOLD,14));
		tppass_new.setLineWrap(true);
		tppass_new.setWrapStyleWord(true);
		tppass_new.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tppass_new_re = new JTextArea();
		tppass_new_re.setFont(new Font("Serif",Font.BOLD,14));
		tppass_new_re.setLineWrap(true);
		tppass_new_re.setWrapStyleWord(true);
		tppass_new_re.setAlignmentX(Component.CENTER_ALIGNMENT);
		tppass_new_re.setSize(400, 50);
		
		pchangepb = new JButton("CHANGE");
		pchangepb.setAlignmentX(Component.CENTER_ALIGNMENT);
		pchangepb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check if password entered in both slots is the same 
				//update password in database
				String s1 = tppass_new.getText();
				String s2 = tppass_new_re.getText();
				if(s1.equals(s2)) {
					if((s1.length())<3 || (s1.length()>15))
						JOptionPane.showMessageDialog(null,"Password length should be greater than 3 and less than 15" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
					else {
					System.out.println(s1);
					System.out.println(s2);
					Connection conn = null;
					Statement stmt = null;
					try {
						conn = DriverManager.getConnection(DB_URL,USER,PASS);
						stmt = conn.createStatement();
						String query7="update Professor set Password=\"" + s1 + "\" where P_id=" + PID; 
			  			stmt.executeUpdate(query7);
					}
					catch(Exception e1)
					{
						System.out.println(e1);
					}
				JOptionPane.showMessageDialog(null,"Succesful" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
				pcpframe.setVisible(false);
				Prof_frame();
				}}
				else JOptionPane.showMessageDialog(null,"Not Succesful" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
			}});    
		cancel_2 = new JButton("BACK");
		cancel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pcpframe.setVisible(false);
				Prof_frame();}});
		pcppanel.add(Box.createRigidArea(new Dimension(0,10)));
		pcppanel.add(ppass_new);
		pcppanel.add(Box.createRigidArea(new Dimension(0,10)));
		pcppanel.add(tppass_new);
		pcppanel.add(Box.createRigidArea(new Dimension(0,20)));
		pcppanel.add(ppass_new_re);
		pcppanel.add(Box.createRigidArea(new Dimension(0,10)));
		pcppanel.add(tppass_new_re);
		pcppanel.add(Box.createRigidArea(new Dimension(0,10)));
		pcppanel.add(pchangepb);
		pcppanel.add(Box.createRigidArea(new Dimension(0,10)));
		pcppanel.add(cancel_2);
		
		pcpframe.add(pcppanel);
		pcpframe.setVisible(true);
	}
	
	public void s_change_password(){
		//ask new password, update in database, successful pop-up, back
		studentframe.setVisible(false);
		scpframe = new JFrame();
		scpframe.setFont(new Font("SansSerif", Font.BOLD, 35));
		scpframe.setTitle("RE-SET PASSWORD");
		scpframe.setForeground(UIManager.getColor("Button.darkShadow"));
		scpframe.setSize(new Dimension(400,350));
		scpframe.setResizable(false);
		scpframe.setLocationRelativeTo(null);
		scpframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scppanel = new JPanel();
		scppanel.setLayout(new BoxLayout(scppanel,BoxLayout.PAGE_AXIS));
		scppanel.setAlignmentX(Component.TOP_ALIGNMENT);
		
		JLabel spass_new = new JLabel("Enter new Password:");
		spass_new.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel spass_new_re = new JLabel("Re-enter new Password:");
		spass_new_re.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		sppass_new = new JTextArea();
		sppass_new.setFont(new Font("Serif",Font.BOLD,14));
		sppass_new.setLineWrap(true);
		sppass_new.setWrapStyleWord(true);
		sppass_new.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		sppass_new_re = new JTextArea();
		sppass_new_re.setFont(new Font("Serif",Font.BOLD,14));
		sppass_new_re.setLineWrap(true);
		sppass_new_re.setWrapStyleWord(true);
		sppass_new_re.setAlignmentX(Component.CENTER_ALIGNMENT);
		sppass_new_re.setSize(400, 50);
		
		schangepb = new JButton("CHANGE");
		schangepb.setAlignmentX(Component.CENTER_ALIGNMENT);
		schangepb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check if password entered in both slots is the same 
				//update password in database
				String s1 = sppass_new.getText();
				String s2 = sppass_new_re.getText();
				if(s1.equals(s2)) {
					if((s1.length())<3 || (s1.length()>15))	
						JOptionPane.showMessageDialog(null,"Password length should be greater than 3 and less than 15" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
					else	{
					Connection conn = null;
					Statement stmt = null;
					try {
						conn = DriverManager.getConnection(DB_URL,USER,PASS);
						stmt = conn.createStatement();
						String query7="update Student set Password=\"" + s1 + "\" where S_id=" + SID; 
			  			stmt.executeUpdate(query7);
					}
					catch(Exception e1)
					{
						System.out.println(e1);
					}
				JOptionPane.showMessageDialog(null,"Succesful" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
				scpframe.setVisible(false);
				Stud_frame();
				}}
				else JOptionPane.showMessageDialog(null,"Password does not match" , "Authentication", JOptionPane.INFORMATION_MESSAGE);
			}});    
		cancel_3 = new JButton("BACK");
		cancel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scpframe.setVisible(false);
				Stud_frame();}});
		scppanel.add(Box.createRigidArea(new Dimension(0,10)));
		scppanel.add(spass_new);
		scppanel.add(Box.createRigidArea(new Dimension(0,10)));
		scppanel.add(sppass_new);
		scppanel.add(Box.createRigidArea(new Dimension(0,20)));
		scppanel.add(spass_new_re);
		scppanel.add(Box.createRigidArea(new Dimension(0,10)));
		scppanel.add(sppass_new_re);
		scppanel.add(Box.createRigidArea(new Dimension(0,10)));
		scppanel.add(schangepb);
		scppanel.add(Box.createRigidArea(new Dimension(0,10)));
		scppanel.add(cancel_3);
		
		scpframe.add(scppanel);
		scpframe.setVisible(true);
	}
	
}
