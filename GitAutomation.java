import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GitAutomation {

    public static void main(String[] args) {
        String commitMessage = "Automated commit";
        String remoteRepository = "origin";
        String branch = "main";

        try {
            // Stage all changes
            executeGitCommand("add .");

            // Commit changes with a message
            executeGitCommand("commit -m \"" + commitMessage + "\"");

            // Push changes to a remote repository
            executeGitCommand("push " + remoteRepository + " " + branch);

            System.out.println("Git tasks completed successfully.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing Git command: " + e.getMessage());
        }
    }

    private static void executeGitCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "git " + command);
        Process process = processBuilder.start();

        BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line;
        while ((line = outputReader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Error executing Git command: " + command);
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
            throw new IOException("Git command failed with exit code " + exitCode);
        }
    }
}