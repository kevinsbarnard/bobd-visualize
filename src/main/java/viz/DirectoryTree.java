package viz;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Barnard
 * @project bobd-visualize
 * @since 7/12/2018
 */
public class DirectoryTree {

    public File baseDir;
    private DirectoryNode baseNode;
    private ArrayList<DirectoryNode> allChildren;
    private Map<DirectoryNode, Integer> levelMap;

    public DirectoryTree(File baseDir) {
        this.baseDir = baseDir;
        this.baseNode = new DirectoryNode(baseDir, null);
        this.levelMap = new HashMap<DirectoryNode, Integer>();
        this.map();
    }
    public DirectoryTree(String baseDirPath) {
        this(new File(baseDirPath));
    }

    private void map() {
        this.baseNode.map();
        this.allChildren = this.baseNode.getAllChildren();
        this.initLevelMap();
    }

    public void print(String pattern, String add) {
        this.baseNode.print(pattern, add);
    }

    public int printAllAtSublevel(int sublevel, String prefix) {
        long totalFiles = 0;
        ArrayList<DirectoryNode> nodes = getNodesAtSublevel(sublevel);
        Collections.sort(nodes);
        for (DirectoryNode child : nodes) totalFiles += child.getNumFiles(); // Count sum total

        int count = 0;
        for (DirectoryNode child : nodes) {
            double percentage = Math.round(((double)child.getNumFiles())/((double)totalFiles)*100 * 100)/100.;
            System.out.println(prefix + child.getName() + " [" + child.parentNode.getName() + "] " + percentage + "%");
            count++;
        }
        return count;
    }

    public int printAllAtSublevel(int sublevel) { return printAllAtSublevel(sublevel, ""); }

    private void initLevelMap() {
        levelMap.put(baseNode, 0);
        levelMapChildren(baseNode, 0);
    }

    private void levelMapChildren(DirectoryNode node, int sublevel) {
        sublevel++;
        for (DirectoryNode child : node.children) {
            levelMap.put(child, sublevel);
            levelMapChildren(child, sublevel);
        }
    }

    public ArrayList<DirectoryNode> getNodesAtSublevel(int sublevel) {
        ArrayList<DirectoryNode> levelNodes = new ArrayList<DirectoryNode>();
        for (DirectoryNode child : allChildren) if (levelMap.get(child) == sublevel) levelNodes.add(child);
        return levelNodes;
    }

}
