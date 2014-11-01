package usermanagement.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * DatePicker.java
 * Creates a dialog for choosing the day, month and year
 * Similar to DatePickerDialog in Android
 * Copyright Mark Lasby 2010
 *
 * @author Mark Lasby
 * @version 1.0.0
 */

public class DatePicker extends JDialog implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1212711319199795420L;
	private String[] monthNames = {"Jan","Feb","Mar","Apr","May","Jun", "Jul","Aug","Sep","Oct","Nov","Dec"};
    private int[] daysPerMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
    private Calendar dateAndTimeIn = Calendar.getInstance();
    private Calendar dateAndTimeOut = Calendar.getInstance();
    private int year = 2010;
    private int month = 0;
    private int day = 1;
    private String monthName = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy");

    private JButton btnCancel;
    private JButton btnSet;
    private JLabel lblDate;
    private JSpinner spnDay;
    private JSpinner spnMonth;
    private JSpinner spnYear;

    /**
     * Retrieves the results from dialog
     *
     * @return dateAndTimeOut Date as modified by the dialog box
     * Note:                  Time portion of Calendar is not modified
     */
    public Calendar getAnswer() { return dateAndTimeOut; }

    /** 
     * 
     * Creates new form DatePicker
     *
     * @param frame Frame that the dialog is being called from
     * @param modal
     * @param dateAndTime Date used to initialize the dialog
     */
    public DatePicker (JFrame frame, boolean modal, Calendar dateAndTime) {
        super(frame, modal);
        initComponents();

        // Save input in case user clicks Cancel or Close
        year = dateAndTime.get(Calendar.YEAR);
        month = dateAndTime.get(Calendar.MONTH);
        day = dateAndTime.get(Calendar.DAY_OF_MONTH);
        dateAndTimeIn.set(year, month, day);

        // Load output with inital value
        dateAndTimeOut.setTime(dateAndTime.getTime());

        // Set the initial values for the year, month and day spinners
        spnYear.setValue(year);
        spnMonth.setValue(monthNames[month]);
        spnDay.setValue(day);

        // Display the initial value at the top of the dialog
        lblDate.setText(sdf.format(dateAndTime.getTime()));
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    /** This method is called from within the constructor to initialize the form. */
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        lblDate = new JLabel();
        btnSet = new JButton();
        btnCancel = new JButton();
        spnMonth = new JSpinner();
        spnDay = new JSpinner();
        spnYear = new JSpinner();

        setTitle("Date Picker");
        setAlwaysOnTop(true);
        getContentPane().setLayout(new GridBagLayout());

        lblDate.setText("Date:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(lblDate, gridBagConstraints);

        btnSet.setText("Set");
        btnSet.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                btnSetActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(btnSet, gridBagConstraints);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(btnCancel, gridBagConstraints);

        spnMonth.setModel(new SpinnerListModel(monthNames));
        spnMonth.addChangeListener(new ChangeListener() {
            public void stateChanged (ChangeEvent evt) {
                spnMonthStateChanged(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(spnMonth, gridBagConstraints);

        spnDay.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnDay.setEditor(new JSpinner.NumberEditor(spnDay, "##"));
        spnDay.addChangeListener(new ChangeListener() {
            public void stateChanged (ChangeEvent evt) {
                spnDayStateChanged(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(spnDay, gridBagConstraints);

        spnYear.setModel(new SpinnerNumberModel(2010, 2000, 2050, 1));
        spnYear.setEditor(new JSpinner.NumberEditor(spnYear, "####"));
        spnYear.addChangeListener(new ChangeListener() {
            public void stateChanged (ChangeEvent evt) {
                spnYearStateChanged(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        getContentPane().add(spnYear, gridBagConstraints);

        pack();
    }

    /**
     * Event handler for Set button
     * Hide the dialog so the calling routine can retrieve the values using getAnswer()
     * 
     * @param evt Not used
     */
    private void btnSetActionPerformed (ActionEvent evt) {
        setVisible(false);
    }

    /**
     * Event handler for Cancel button
     * Reset the date and time to the values that the class was called with
     *
     * @param evt Not used
     */
    private void btnCancelActionPerformed (ActionEvent evt) {
        dateAndTimeOut.setTime(dateAndTimeIn.getTime());
        setVisible(false);
    }

    /**
     * Event handler for changes to the month spinner
     * Fixes last day of month
     * 
     * @param evt not used
     */
    private void spnMonthStateChanged (ChangeEvent evt) {
        SpinnerModel listModel = spnMonth.getModel();
        if (listModel instanceof SpinnerListModel) {
            monthName = ((SpinnerListModel)listModel).getValue().toString();
            for(int i=0; i<monthNames.length; i++) {
                if (monthName.equals(monthNames[i])) {
                    month = i;
                }
            }
            // Change day if current display is past the end of the new month
            if (day > daysPerMonth[month]) {
                day = daysPerMonth[month];
                spnDay.setValue(day);
                dateAndTimeOut.set(Calendar.DAY_OF_MONTH, day);
            }
            dateAndTimeOut.set(Calendar.MONTH, month);
            lblDate.setText(sdf.format(dateAndTimeOut.getTime()));
        }
    }

    /**
     * Event handler for changes to the day spinner
     * Limits day to the last day of month
     *
     * @param evt Not used
     */
    private void spnDayStateChanged (ChangeEvent evt) {
        SpinnerModel numberModel = spnDay.getModel();
        if (numberModel instanceof SpinnerNumberModel) {
            day = (Integer) ((SpinnerNumberModel)numberModel).getValue();
            // Limit day to end of month
            if (isLeapYear(year)) {
                daysPerMonth[1] = 29;
            } else{
                daysPerMonth[1] = 28;
            }
            if (day > daysPerMonth[month]) {
                day = daysPerMonth[month];
                spnDay.setValue(day);
            }
            dateAndTimeOut.set(Calendar.DAY_OF_MONTH, day);
            lblDate.setText(sdf.format(dateAndTimeOut.getTime()));
        }
    }

    /**
     * Event handler for changes to year spinner
     * 
     * @param evt Not used
     */
    private void spnYearStateChanged (ChangeEvent evt) {
        SpinnerModel numberModel1 = spnYear.getModel();
        if (numberModel1 instanceof SpinnerNumberModel) {
            year = (Integer) ((SpinnerNumberModel)numberModel1).getValue();
            dateAndTimeOut.set(Calendar.YEAR, year);
            lblDate.setText(sdf.format(dateAndTimeOut.getTime()));
        }
    }

    /**
     * Determines if year is a leap year
     * 
     * @param year
     * @return true if year is a leap year
     */
    private boolean isLeapYear (int year) {
        boolean leapYear = false;
        if (year % 400 == 0){
            leapYear = true;
        } else if (year % 100 == 0){
            leapYear = false;
        } else if (year % 4 == 0){
            leapYear = true;
        }
       return leapYear;
    }

    public void actionPerformed (ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}