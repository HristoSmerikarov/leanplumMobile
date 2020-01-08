package com.leanplum.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.leanplum.tests.enums.OSEnum;
import com.leanplum.tests.helpers.Utils;

public class TestMain {

	public static void main(String[] args) {
		ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "xcrun instruments -s devices");
        List<String> responseLines = new ArrayList<>();
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(":") && !line.contains("-")) {
                	System.out.println(line);
                    responseLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
