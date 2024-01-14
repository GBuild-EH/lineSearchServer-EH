package cop2805;
import java.util.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
public class LineServer {	
	public static void main(String[] args) {
		boolean shutdown = false;
		ServerSocket server = null;
		int port = 1236;		
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}		
		LineSearcher script = new LineSearcher("hamlet.txt");
		while (!shutdown) {
			try {
				Socket client = server.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
				OutputStream output = client.getOutputStream();
				
				String clientString = input.readLine();
				if (clientString.equalsIgnoreCase("shutdown")) {
					shutdown = true;
					String response = "It is now safe to close the program.\n";
					output.write(response.getBytes());
					client.close();
				}
				else {					
					int target = Integer.parseInt(clientString);
					List<String> fragment = script.searchDoc(target);
					for (int x = 0; x < fragment.size(); x++) {
						String response = fragment.get(x) + "\n";
						output.write(response.getBytes());
					}
					client.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}

class LineSearcher {
	List<String> document = new ArrayList<String>();
	
	public LineSearcher (String file) {
		Path textFile = Paths.get(file);
		try {
			document = Files.readAllLines(textFile); //extract entire document into list object
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public List<String> searchDoc(int target) { //retrieve requested line and two lines before and after
		int sub2 = target - 2;					//if they're in the document
		int sub1 = target - 1;
		int add1 = target + 1;
		int add2 = target + 2;
		List<String> fragment = new ArrayList<String>();
		if (target >= 0 && target <= document.size()) {
			if (sub2 >= 0)
				fragment.add(document.get(sub2));
			if (sub1 >= 0)
				fragment.add(document.get(sub1));
			fragment.add(document.get(target));
			if (add1 <= document.size());
				fragment.add(document.get(add1));
			if (add2 <= document.size());
				fragment.add(document.get(add2));
			return fragment;
		}
		else {
			fragment.add("Target outside of document. Try again.");
			return fragment;
		}
	}
	
	/* public static void main(String[] args) {
		LineSearcher lSearch = new LineSearcher("hamlet.txt");
		List <String> excerpt = lSearch.searchDoc(2023);
		for (int x = 0; x < excerpt.size(); x++)
			System.out.println(excerpt.get(x));		
	} */
	
}
