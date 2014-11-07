import java.util.*;
import java.io.*;
import java.net.*;

/*Copyright 2014 Shantanu Jain
 *Copyright 2014 Samir Aly: showHistory() and listPorts() operations

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/


public class Bash{
	static String pwd = "";
	static List<String> history = new ArrayList<String>();
	public static void main(String[] args){
	
	pwd = System.getProperty("user.dir");
	while(true){
	Scanner sc = new Scanner(System.in);
	System.out.print(pwd+" ~  ");
		String[] input = sc.nextLine().trim().split(" ");
		String cmd = input[0].trim();
		history.add(cmd); // add command to history list
		if(cmd.equals("pwd"))
			pwdfunction(pwd);
		else if(cmd.equals( "ls")){
			String[] files = ls(pwd);
			for(int i=0;i<files.length;i++){
                        	System.out.println(files[i]);
                	}
		}else if(cmd.equals("cat")){
			if(input.length==2){
				cat(input[1].trim());
			}else{
				System.out.println("File Not Found");
			}
		}else if(cmd.equals("find")){
			if(input.length==2){
				find(input[1].trim(),ls(pwd));
			}else{
				System.out.println("File Not Found");
			}
		}else if(cmd.equals("cd")){
			if(input.length==2){
				cd(pwd+"/"+input[1].trim());
			}else{
				System.out.println("Second Argument Required");
			}
		}else if(cmd.equals("history")){
			showHistory();
		}else if(cmd.equals("netstat")){
			listPorts();
		}else if(cmd.equals("exit")){
			System.exit(0);
		}else{
			System.out.println("Command Undefined");
			}
			
	}
   }
	private static void pwdfunction(String pwd){
		System.out.println(pwd);
		return;
	}
	
	private static void cd(String path){
		File f = new File(path);
		if (f.exists()) {
			try{
   			pwd = f.getCanonicalPath();
			return ;
			}catch(Exception e){
				System.out.println("Exception");
			}
		}else{
			System.out.println("Invalid Path");
		}

	}

	private static void cat(String fileName){
	System.out.println("Cat: \n");

        try{
                FileReader fr = new FileReader(pwd+"/"+fileName);
                BufferedReader in  = new BufferedReader(fr);
                String line;
                while((line = in.readLine())!=null){
                        System.out.println(line);
                }
        }catch(Exception e){
                System.out.println("Exception");
        }



	}
	private static String[] ls(String dir){
		System.out.println("LS: \n");
		File file = new File(dir);
		String[] files= file.list();
		return files;
	}


	private static void find(String find,String[] dir){
		System.out.println("Finding :"+find+"\n");
		boolean found = false;
		if(find.substring(0,1).contains("*")){
			find = find.replace("*","").trim();
			for(int i=0;i<dir.length;i++){
				if(dir[i].contains(find)){
					System.out.println(dir[i]);
					found=true;
				}
			}	
		}else{
			for(int i=0;i<dir.length;i++){
				if(dir[i].equals(find)){
					System.out.println(dir[i]);
					found=true;
				}
			}
		}

		if(!found){
			System.out.println("Not found: "+find);	
		}
	}
	
	private static void showHistory(){
		for (int x=1;x<history.size();x++){ 
			System.out.println(x + "\t"+history.get(x-1));
		}
	}
	
	private static void listPorts(){
		int count=0;
		System.out.println("Scanning for open ports...");
		for (int x=0;x<10000;x++){
				
			try{
				Socket s = new Socket("127.0.0.1",x);
				System.out.println(x);
				++count;
			}
			catch(Exception e){ }
		}
		if (count==0){ System.out.println("No ports found. Check your firewall settings."); }
		else{ System.out.println("Found "+ count+" open ports."); }
	}
}
