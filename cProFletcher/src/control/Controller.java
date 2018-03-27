package control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {
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

    public void removeNodes(int id) {
        
    }

    public List<Node> getNodes() {
        return nodeList;
    }
    
    public void removeZeroCount(){
    	for (Iterator<Node> iter = nodeList.iterator(); iter.hasNext();) {
    	      Node s = iter.next();
    	      if (s.getCount() == 0) {
    	        iter.remove();
    	      }
    	}
   }

    public Node getCurrentNode() {
        List<Node> exeucuteableNodes = new ArrayList<Node>();
        for (Node n : nodeList) {
            if (n.validate()) {
                exeucuteableNodes.add(n);
            }
        }
        for (Node n : exeucuteableNodes) {
            if (getHighest(exeucuteableNodes) == n.priority()) {
                return n;
            }
        }
        return null;
    }

    private int getHighest(List<Node> exeucuteableNodes) {
        int highest = exeucuteableNodes.get(0).priority();
        for (Node n : exeucuteableNodes) {
            if (n.priority() > highest) {
                highest = n.priority();
            }
        }
        return highest;
    }

}
