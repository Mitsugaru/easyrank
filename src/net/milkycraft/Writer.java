package net.milkycraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Writer extends EasyRank {

	protected static void writeNames() {
		if (EasyRank.name == null) {
			name = new ArrayList<String>();
		}
		try {
		BufferedWriter writer = null;
		File names = new File(maindirectory, "names.txt");
		writer = new BufferedWriter(new FileWriter(names));
		Iterator<String> itr = name.iterator();
	    while (itr.hasNext()){
	    	writer.newLine();
			writer.write(itr.next());
	    }
	    writer.flush();
	    writer.close();
		} catch (Exception ex) {
			
		}
	}

	protected static void writeRanks() {
		if (EasyRank.rank == null) {
			rank = new ArrayList<String>();
		}
		try {
		BufferedWriter writer = null;
		File names = new File(maindirectory, "ranks.txt");
		writer = new BufferedWriter(new FileWriter(names));
		Iterator<String> itr = rank.iterator();
	    while (itr.hasNext()){
	    	writer.newLine();
			writer.write(itr.next().toString());
	    }
	    writer.flush();
	    writer.close();
		} catch (Exception ex) {
			
		}
	}

	protected static List<String> getNames() throws IOException {
		return Files.readLines(new File(maindirectory, "names.txt"),
				Charsets.UTF_8);
	}

	protected static List<String> getRanks() throws IOException {
		return Files.readLines(new File(maindirectory, "ranks.txt"),
				Charsets.UTF_8);
	}
	
	protected static int getLineNumRank(String rank) throws Exception {
		File ranks = new File(maindirectory, "ranks.txt");
		LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(ranks)));
		if(lnr.readLine().equalsIgnoreCase(rank)) {
			return lnr.getLineNumber();
		}
		return 0;
	}
	
	protected static int getLineNumName(String name) throws Exception {
		File names = new File(maindirectory, "names.txt");
		LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(names)));
		if(lnr.readLine().equalsIgnoreCase(name)) {
			return lnr.getLineNumber();
		}
		return 0;
	}
	protected static void removeIndexName(String index) throws IOException {
		File inputFile = new File(maindirectory, "names.txt");
		File tempFile = new File(maindirectory, "names.txt.tmp");		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String remove = index;
		String currentLine;
	
		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.equals(remove)) continue;
		    writer.write(currentLine);
		    writer.flush();
		    writer.close();
		}

		@SuppressWarnings("unused")
		boolean successful = tempFile.renameTo(inputFile);
	}
	protected static void removeIndexRank(String index) throws IOException {
		File inputFile = new File(maindirectory, "ranks.txt");
		File tempFile = new File(maindirectory, "ranks.txt.tmp");		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String remove = index;
		String currentLine;
	
		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.equals(remove)) continue;
		    writer.write(currentLine);
		    writer.flush();
		    writer.close();
		}

		@SuppressWarnings("unused")
		boolean successful = tempFile.renameTo(inputFile);
	}
}
