package com.game;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuValidator {

    private static final int GRID_SIZE = 9;
    private static final int GRID_INDEX_START = 0;
    private static final int GRID_INDEX_END = 8;
    private static final int NUMBER_RANGE_START = 1;
    private static final int NUMBER_RANGE_END = 9;

    public ValidationResult validateSolution(int[][] inputSolution) {
        Map<ValidationErrorType, List<String>> errorMap = new HashMap<>();
        if (isValidSolution(inputSolution, errorMap)) {
            return new ValidationResult(ValidationResultType.VALID);
        } else {
            return new ValidationResult(ValidationResultType.INVALID, errorMap);
        }
    }

    private boolean isValidSolution(int[][] inputSolution, Map<ValidationErrorType, List<String>> errorMap) {
        return hasValidRows(inputSolution, errorMap)
                && hasValidColumns(inputSolution, errorMap)
                && hasValidThreeByThreeBoxes(inputSolution, errorMap)
                ;
    }

    private boolean hasValidThreeByThreeBoxes(int[][] inputSolution, Map<ValidationErrorType, List<String>> errorMap) {
        int validThreeByThreeBoxCount = 0;
        for (int rowIndex = 0; rowIndex <= GRID_INDEX_END - 2; rowIndex++) {
            if (rowIndex % 3 == 0) {
                for (int columnIndex = 0; columnIndex <= GRID_INDEX_END - 2; columnIndex++) {
                    if (columnIndex % 3 == 0) {
                        validThreeByThreeBoxCount += isValidThreeByThreeBox(inputSolution, rowIndex, columnIndex, errorMap) ? 1 : 0;
                    }
                }
            }
        }
        return validThreeByThreeBoxCount == GRID_SIZE;
    }

    private boolean isValidThreeByThreeBox(int[][] inputSolution, int rowStartIndex, int columnStartIndex, Map<ValidationErrorType, List<String>> errorMap) {
        Set<Integer> threeByThreeBoxData = new HashSet<>();
        for (int row = rowStartIndex; row < rowStartIndex + 3; row++) {
            for (int column = columnStartIndex; column < columnStartIndex + 3; column++) {
                threeByThreeBoxData.add(inputSolution[row][column]);
            }
        }
        if (hasAllNumbersInRange(threeByThreeBoxData)) {
            return true;
        } else {
            addError(errorMap, ValidationErrorType.INVALID_THREE_BY_THREE_VALUES, String.format("Each 3x3 box must contain unique values for box starting at [%d][%d]", rowStartIndex, columnStartIndex));
            return false;
        }

    }

    private void addError(Map<ValidationErrorType, List<String>> errorMap, ValidationErrorType errorType, String errorMsg) {
        List<String> errors = errorMap.get(errorType);
        if(errors == null){
            errors = new ArrayList<>();
            errorMap.put(errorType, errors);
        }
        errors.add(errorMsg);
    }


    private boolean hasValidColumns(int[][] inputSolution, Map<ValidationErrorType, List<String>> errorMap) {
        return GRID_SIZE == IntStream.rangeClosed(GRID_INDEX_START, GRID_INDEX_END).filter(columnIndex -> {
            int[] column = IntStream.rangeClosed(GRID_INDEX_START, GRID_INDEX_END).map(rowIndex -> inputSolution[rowIndex][columnIndex]).toArray();
            return isValidRowOrColumn(column, errorMap, GridDataType.COLUMN);
        }).boxed().count();
    }

    private boolean hasValidRows(int[][] inputSolution, Map<ValidationErrorType, List<String>> errorMap) {
        return GRID_SIZE == IntStream.rangeClosed(GRID_INDEX_START, GRID_INDEX_END)
                .filter(rowIndex -> isValidRowOrColumn(inputSolution[rowIndex], errorMap, GridDataType.ROW))
                .boxed().count();
    }

    private boolean isValidRowOrColumn(int[] data, Map<ValidationErrorType, List<String>> errorMap, GridDataType gridDataType) {
        Set<Integer> gridValues = Arrays.stream(data).boxed().collect(Collectors.toSet());
        boolean hasAllUniqueNumbers = gridValues.size() == GRID_SIZE;
        boolean hasAllNumbersInRange = hasAllNumbersInRange(gridValues);

        boolean isValidRowOrColumn = false;
        if (hasAllUniqueNumbers && hasAllNumbersInRange) {
            isValidRowOrColumn = true;
        } else if (!hasAllUniqueNumbers) {
            addError(errorMap, ValidationErrorType.INVALID_SIZE, String.format("%s must not have duplicates for %s", gridDataType, Arrays.stream(data).boxed().collect(Collectors.toList())));
        } else if (!hasAllNumbersInRange) {
            addError(errorMap, ValidationErrorType.INVALID_VALUES, String.format("%s should have values between [%d-%d] for %s", gridDataType, NUMBER_RANGE_START, NUMBER_RANGE_END, Arrays.stream(data).boxed().collect(Collectors.toList())));
        }
        return isValidRowOrColumn;
    }

    private boolean hasAllNumbersInRange(Set<Integer> gridValues) {
        return GRID_SIZE == IntStream.rangeClosed(NUMBER_RANGE_START, NUMBER_RANGE_END)
                .filter(gridValues::contains)
                .boxed().count();
    }

}
