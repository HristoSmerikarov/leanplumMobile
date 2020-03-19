package com.leanplum.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {

	public static void main(String[] args) {
	    
	    String line = "https://www.leanplum.com/dashboard#/5474349233864704/messaging/4994758225035264";
	      String pattern = "(\\b[\\d]+$)";

	      // Create a Pattern object
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	         System.out.println("Found value: " + m.group(0) );
	      }else {
	         System.out.println("NO MATCH");
	      }
	}

}
