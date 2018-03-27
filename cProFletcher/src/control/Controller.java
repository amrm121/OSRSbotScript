package control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller { //my round robin solution for using nodes with priority, and see what state shoul
    private List<Node> nodeList;

    public Controller(Node... nodes) {
        this.nodeList = new ArrayList<Node>();
        for (Node n : nodes) {
            nodeList.add(n);
        }
    }

    public void addNodes(Node... nodes) {
        for (Node n : nodes) {
            nodeList.add(n);
        }
    }

    public void clearNodes() {
        nodeList.clear();
    }

    public List<Node> getNodes() {
        return nodeList;
    }
    
    public void removeZeroCount(){
    	for (Iterator<Node> iter = nodeList.iterator(); iter.hasNext();) {
    	      Node s = iter.next();
    	      if (s.getCount() == 0) { //getCount return the amount of times I need to execute that node, for example, if a user has defined in GUI that he wants to make 10 Log Bows, the node responsible for it will run only 10 times.
    	        iter.remove();
    	      }
    	}
   }

    public Node getCurrentNode() {
        List<Node> exeucuteableNodes = new ArrayList<Node>();
        for (Node n : nodeList) {
            if (n.validate()) { //check the state requirements to add it to the executeable nodes
                exeucuteableNodes.add(n);
            }
        }
        for (Node n : exeucuteableNodes) { //return the actual node to be executed by priority after the state req check
            if (getHighest(exeucuteableNodes) == n.priority()) {
                return n;
            }
        }
        return null;
    }

    private int getHighest(List<Node> exeucuteableNodes) { //return the highest priority node
        int highest = exeucuteableNodes.get(0).priority();
        for (Node n : exeucuteableNodes) {
            if (n.priority() > highest) {
                highest = n.priority();
            }
        }
        return highest;
    }

}
