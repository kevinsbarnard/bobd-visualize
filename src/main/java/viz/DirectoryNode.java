package viz;

import java.io.File;
import java.util.ArrayList;

import static main.Main.dprintln;

/**
 * @author Kevin Barnard
 * @project bobd-visualize
 * @since 7/12/2018
 */
public class DirectoryNode implements Comparable {

    private File directoryFile;
    private String name;
    DirectoryNode parentNode;
    ArrayList<DirectoryNode> children;
    private long numFiles;

    DirectoryNode(File directoryFile, DirectoryNode parentNode) {
        this.directoryFile = directoryFile;
        this.parentNode = parentNode;
        this.children = new ArrayList<DirectoryNode>();
        this.name = directoryFile.getName();
        this.numFiles = 0;
    }

    int map() {
        int numFiles = 0;
        dprintln("Mapping " + this.directoryFile.getAbsolutePath());
        children.clear();
        for (File f : directoryFile.listFiles()) {
            if (f.isDirectory()) {
                DirectoryNode child = new DirectoryNode(f, this);
                children.add(child);
                numFiles += child.map();
            } else numFiles++;
        }
        this.numFiles = numFiles;
        return numFiles;
    }

    int mapImmediate() {
        int numFiles = 0;
        dprintln("Mapping immediate " + this.getName());
        children.clear();
        for (File f : directoryFile.listFiles()) {
            if (f.isDirectory()) {
                DirectoryNode child = new DirectoryNode(f, this);
                children.add(child);
                numFiles += child.getNumFiles();
            } else numFiles++;
        }
        this.numFiles = numFiles;
        return numFiles;
    }

    public void print(String pattern, String add) {
        System.out.print(pattern);
        System.out.print(this.getName());
        System.out.println(" (" + this.numFiles + (this.numFiles!=1?" files, ":" file, ") + children.size() + (children.size()!=1?" children":" child") + ")");
        for (DirectoryNode child : children) {
            child.print(add + pattern, add);
        }
    }

    ArrayList<DirectoryNode> getChildren() { return this.children; }

    ArrayList<DirectoryNode> getAllChildren() {
        ArrayList<DirectoryNode> allChildren = new ArrayList<DirectoryNode>();
        for (DirectoryNode child : children) {
            allChildren.add(child);
            allChildren.addAll(child.getAllChildren());
        }
        return allChildren;
    }

    public int compareTo(Object o) {
        return this.getName().compareTo(((DirectoryNode)o).getName());
    }

    public String getName() { return this.name; }

    public long getNumFiles() { return numFiles; }

    public DirectoryNode getParentNode() { return parentNode; }
}
