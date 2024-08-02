package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TextFileReader {
	public List<String> readFile(String filename) throws IOException {
		FileReader frStream = new FileReader(filename);
		BufferedReader brStream = new BufferedReader(frStream);
		List<String> lines = brStream.lines().collect(Collectors.toList());
		brStream.close();
		return lines;
	}
}
