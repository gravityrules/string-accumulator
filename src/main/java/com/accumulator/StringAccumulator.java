package com.accumulator;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringAccumulator {

    private static final String ERROR_MESSAGE = "negatives not allowed:";

    public int add(String numbers) {
        int startIndex = numbers.indexOf("/");
        String inputNum = numbers;
        Splitter customSplitter;
        if (startIndex == 0){   //check if the delimiter was provided as input then apply a different logic.
            int firstNewLineIndex = numbers.indexOf("\n");
            String delimiters = numbers.substring(2,firstNewLineIndex);
            inputNum = numbers.substring(firstNewLineIndex,numbers.length());
            customSplitter = customSplitter(delimiters);
        }else{
            //clients using default delimiters will bypass customSplitter logic.
            customSplitter = defaultSplitter();
        }

        return sum(customSplitter.splitToList(inputNum));
    }

    private int sum(List<String> numList) {
        int total=0;   //program asks to use int add(), int value may overflow if very large string is sent
        StringBuilder errorBuilder = new StringBuilder(ERROR_MESSAGE);
        boolean hasError = false;
        for(String value : numList){
            int num = Integer.parseInt(value);
            if (num <0){
                hasError = true;
                errorBuilder.append(" ").append(num);
            }else if (num <= 1000){
                total +=num;
            }
        }
        if (hasError){
            throw new IllegalArgumentException(errorBuilder.toString());
        }

        return total;
    }

    private Splitter customSplitter(String delimiters){
        //assumption delimiters themselves will be delimited by "|". If the input doesn't follow the contract, this won't work.
        //Input validations can be added here if required.
        String escapedDelimiters = Splitter.on("|").trimResults()
                .omitEmptyStrings()
                .splitToList(delimiters)
                .stream()
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
        return Splitter.onPattern(escapedDelimiters)
                .trimResults()
                .omitEmptyStrings();
    }

    private Splitter defaultSplitter(){
        //if the usage is known, it can profiled and tested if adding this splitter as constant for multiple calls will save cost or not.
        return Splitter.on(CharMatcher.anyOf(",\n")).trimResults().omitEmptyStrings();
    }
}
