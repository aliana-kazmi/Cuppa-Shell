import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.*;
import java.util.*;

public class Main {

    private static String getSystemSeparator(){
        String systemOS = System.getProperty("os.name").toLowerCase();
        return systemOS.contains("win") ? ";" : ":";
    }

    private static String getPath(String input) { 
        String command = input.split(" ")[0].trim();

        String separator = getSystemSeparator();
        String[] paths = System.getenv("PATH").split(separator);

        for(String path : paths){
            Path fullPath = Path.of(path, command);
            if(Files.isRegularFile(fullPath)){
                return fullPath.toString();
            }
        }
        return null;
    }

    public static boolean isBuiltInCommand(String command){
        String commands[] = {"echo","exit","type","pwd","cd"};
        for(int i = 0; i < commands.length; i++) {
            if(command.equals(commands[i]))
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("$ ");
            input = scanner.nextLine();

            //initialization
            List<String> argsList = new ArrayList<>();
            String command = "";
            int i = 0;
            StringBuilder sb = new StringBuilder();

            //parameter processing
            while (i + 1 < input.length()) {
                char ch;
                //command tracking
                if (command.isEmpty()) {
                    ch = input.charAt(i);
                    while (i < input.length() && !Character.isWhitespace(ch)) {
                        sb.append(ch);
                        i++;
                        if(i<input.length())  ch = input.charAt(i);
                        else break;
                }
                command = sb.toString();
            }

            if(i == input.length()) break;
            sb = new StringBuilder();
            ch = input.charAt(i);
            while (Character.isWhitespace(ch)) {
                ch = input.charAt(++i);
            }

            ch = input.charAt(i);
            if(ch=='\'') {
            while(ch == '\'') {
                ch = input.charAt(++i);
            }
                ch = input.charAt(i);
                if(!Character.isWhitespace(ch))
                sb.append(ch);
                else i++;
                while (++i < input.length())  {
                    if(input.charAt(i) == '\'')
                        if(i + 1 < input.length() && input.charAt(i+1)=='\'')
                        {
                            i++;
                            continue;
                        }
                        else break;
                    
                    ch = input.charAt(i);
                    sb.append(ch);
                }
                argsList.add(sb.toString().trim());
            }
            
            if (i < input.length() && !Character.isWhitespace(ch) && input.charAt(i) != '\'') {
                ch = input.charAt(i);
                sb = new StringBuilder();
                sb.append(ch);
                while (++i < input.length() && !Character.isWhitespace(input.charAt(i))) {
                    
                    ch = input.charAt(i);
                    sb.append(ch);
                }
                argsList.add(sb.toString().trim());
                }
            }
            String[] parametersList = argsList.toArray(new String[0]);

            switch(command){
                case "exit": if(parametersList[0].equals("0")) System.exit(0);
                break;

                case "echo": 
                for(int p = 0; p < parametersList.length; p++)
                System.out.print(parametersList[p] + " ");
                System.out.println();
                break;

                case "type":
                for(int p = 0; p < parametersList.length; p++) {
                    if(isBuiltInCommand(parametersList[p])) System.out.println(parametersList[p] + " is a shell builtin");
                    else {
                        String path = getPath(parametersList[p]);
                        if (path != null) {
                            System.out.println(parametersList[p] + " is " + path);
                        } else {
                        System.out.println(parametersList[p] + ": not found");
                        }    
                    }
                }
                break;

                case "pwd": System.out.println(System.getProperty("user.dir"));
                break;

                case "cat": 
                for(int p = 0; p < parametersList.length; p++) {
                    String line;

                    if(new File(parametersList[p]).canRead()) {
                        FileReader in = new FileReader(parametersList[p]);
                        BufferedReader br = new BufferedReader(in);    
                        while ((line = br.readLine()) != null) {
                            System.out.print(line);
                        }
                        in.close();
                    }
                    else
                    System.out.println("file does not exist");
                }
                System.out.println();
                break;

                case "cd": 
                Path newPath = null;
                if(parametersList[0].equals("~"))
                {
                    newPath = Path.of(System.getenv("HOME"));
                }
                else if (parametersList[0].startsWith("/")) {
                    newPath = Path.of(parametersList[0]);
                }
                else {
                    Path currentDirPath = Path.of(System.getProperty("user.dir"));
                    File newFile = Path.of(currentDirPath + "/" + parametersList[0]).toAbsolutePath().toFile();
                    newPath = Path.of(newFile.getCanonicalPath());
                }

                if(!Files.exists(newPath)) {
                System.out.println("cd: " + parametersList[0] + ": No such file or directory");
                } else {
                    System.setProperty("user.dir",newPath.toString());
                }
                break;

                default: 
                String executable_path = getPath(input);
                if(executable_path != null){
                    String[] commandWithArgs = input.split(" ");
                    try{
                        ProcessBuilder builder = new ProcessBuilder(commandWithArgs);
                        Process process = builder.start();
                        process.getInputStream().transferTo(System.out);
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }
                else{
                    System.out.println(command + ": command not found");
                }  
            }
        }while(!input.regionMatches(0,"exit 0",0,6));

        scanner.close();
    }
}
