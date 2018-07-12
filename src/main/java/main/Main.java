package main;

import viz.DirectoryTree;

import java.io.File;

/**
 * @author Kevin Barnard
 * @project bobd-visualize
 * @since 7/12/2018
 */
public class Main {

    public static void main(String[] args) {

        String[] params = parseArgs(args);

        if (params == null || params[0].equals("")) {
            System.err.println("Terminating.");
            return;
        }

        File baseDir = new File(params[0]);
        System.out.println(">>> " + (baseDir.exists() ? "Found " + baseDir.getPath() : "Failed to find " + baseDir.getPath()));
        DirectoryTree tree = new DirectoryTree(baseDir);

        System.out.println("\nDIRECTORY TREE:");
        tree.print("- ", "  |");

        System.out.println("\nSUBLEVEL 2:");
        System.out.println("Total: " + tree.printAllAtSublevel(2, "- "));

        System.out.println("\nSUBLEVEL 3:");
        System.out.println("Total: " + tree.printAllAtSublevel(3, "* "));

    }

    private static String[] parseArgs(String[] args) {
        String[] params = Constants.DEFAULT_PARAMS;
        if (args.length != 1) {
            System.err.println("Error in usage. Please follow:\n\tjava -jar bobd-visualize.jar path/to/root");
            return null;
        }

        String path = args[0];
        params[0] = path;

        return params;
    }

    public static void dprintln(String s) {
        System.out.println("[DEBUG] " + s);
    }

    public static void dprint(String s) {
        System.out.print("[DEBUG] " + s);
    }

}