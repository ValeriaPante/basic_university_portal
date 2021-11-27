package it.polimi.tiw.projects.beans;

public enum ExamStatus {
	EMPTY(0), NOTPUBLISHED(1), PUBLISHED(2), REFUSED(3), VERBALIZED(4);

	private final int value;

	ExamStatus(int value) {
		this.value = value;
	}

	public static ExamStatus getExamStatusFromInt(int value) {
		switch (value) {
		case 0:
			return ExamStatus.EMPTY;
		case 1:
			return ExamStatus.NOTPUBLISHED;
		case 2:
			return ExamStatus.PUBLISHED;
		case 3:
			return ExamStatus.REFUSED;
		case 4:
			return ExamStatus.VERBALIZED;
		}
		return null;
	}

	public int getValue() {
		return value;
	}

}
