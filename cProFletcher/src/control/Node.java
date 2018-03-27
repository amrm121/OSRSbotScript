package control;

public abstract class Node {
	
	public abstract boolean validate();

    public abstract void execute();

    public abstract int priority();

    public abstract String getName();
    
    public abstract void countM(int x);
    
    public abstract int getCount();
    
}
