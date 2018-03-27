package control;

public abstract class Node {
	
    public abstract boolean validate(); //node is valid to use, like in a FSM I can go to this state by the actions I'm executing in my actual state

    public abstract void execute(); //execute the node

    public abstract int priority(); //define node priority

    public abstract String getName(); //get node name
    
    public abstract void countM(int x); //for nodes which I need an item count 
    
    public abstract int getCount();
    
}
