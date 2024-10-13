import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GitAutomation {

    public static void main(String[] args) {
        String commitMessage = "Automated commit";
        String branch = "main"; // The branch you want to push to

        try {
            // Stage all changes
            executeCommand("git add .");

            // Commit changes with a message
            executeCommand("git commit -m \"" + commitMessage + "\"");

            // Push changes to the repository
            executeCommand("git push origin " + branch);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void executeCommand(String command) throws IOException, InterruptedException {
        // For Windows use "cmd.exe" with "/c" flag to run the commands
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        processBuilder.redirectErrorStream(true); // Redirect error stream to standard output
        Process process = processBuilder.start();

        // Read the output from the command
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Output the command result
            }
        }

        // Wait for the process to finish and check the exit code
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Command failed: " + command + " (Exit code: " + exitCode + ")");
            throw new IOException("Command failed with exit code " + exitCode);
        }
    }
}
