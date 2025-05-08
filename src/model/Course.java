package model;

import customExceptions.OutOfRangeGradeException;
import customExceptions.QuotaEnrollExceedException;
import customExceptions.alreadyActive;
import customExceptions.lateRegistration;
import customExceptions.notAllowedCancelation;

public class Course {
	private double maxGrade;
	private double minGrade;
	private int currentWeek;
	private int totalGradesAmount;
	private int maxQuota;

	private Student[] studentsEnrolled;

	public Course(int tga, double mx, double mn, int mq) {
		currentWeek = 1;
		maxGrade = mx;
		minGrade = mn;
		totalGradesAmount = tga;
		maxQuota = mq;
		studentsEnrolled = new Student[mq];
	}

	public void enroll(String id) throws QuotaEnrollExceedException, lateRegistration, alreadyActive {
		int posNewStudent = searchFirstAvailable();

		if (searchStudent(id) != -1) {
			throw new alreadyActive();
		}
		if (posNewStudent == -1) {
			throw new QuotaEnrollExceedException(maxQuota);
		} else {
			studentsEnrolled[posNewStudent] = new Student(id, totalGradesAmount);
		}
		if (currentWeek > 2) {
			throw new lateRegistration();
		}

	}

	public void cancelEnrollment(String id) throws notAllowedCancelation {
		int posStudent = searchStudent(id);

		Student student = studentsEnrolled[posStudent];
		int count = 0;

		for (int i = 1; i <= totalGradesAmount; i++) {
			try {
				double grade = student.getGrade(i);
				if (grade != 0.0) {
					count++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}

		if (count > totalGradesAmount / 2) {
			throw new notAllowedCancelation();
		}

		studentsEnrolled[posStudent] = null;
	}

	public void setStudentGrade(String id, int gradeNumber, double grade)
			throws ArrayIndexOutOfBoundsException, OutOfRangeGradeException {
		if (grade < minGrade || grade > maxGrade) {
			throw new OutOfRangeGradeException(grade, maxGrade, minGrade);
		}

		int posStudent = searchStudent(id);
		studentsEnrolled[posStudent].setGrade(gradeNumber, grade);
	}

	public void advanceWeek() {
		currentWeek++;

	}

	private int searchFirstAvailable() {
		int pos = -1;
		for (int i = 0; i < studentsEnrolled.length && pos == -1; i++) {
			Student current = studentsEnrolled[i];
			if (current == null) {
				pos = i;
			}
		}

		return pos;
	}

	private int searchStudent(String id) {
		int pos = -1;

		for (int i = 0; i < studentsEnrolled.length && pos == -1; i++) {
			Student current = studentsEnrolled[i];
			if (current != null) {
				if (id.contentEquals(current.getId())) {
					pos = i;
				}
			}
		}

		return pos;
	}

	public String showEnrolledStudents() {

		String msg = "";

		for (int i = 0; i < studentsEnrolled.length; i++) {

			if (studentsEnrolled[i] != null) {

				msg += studentsEnrolled[i].getId() + "\n";

			}

		}

		return msg;

	}

	public String showStudentGrades(String id) {

		int pos = searchStudent(id);

		if (pos != -1) {

			return studentsEnrolled[pos].showGrades();

		}

		return "Error";

	}
}
