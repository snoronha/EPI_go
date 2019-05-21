//class that sets the edges for the maze
public class MazeEdge
{
    boolean[] edges;
    public MazeEdge()
    {
        edges = new boolean[2];
        edges[0] = false;
        edges[1] = false;
    }
    public MazeEdge(boolean right, boolean down)
    {
        edges = new boolean[2];
        edges[0] = right;
        edges[1] = down;
    }
    public void setRight(boolean right)
    {
        edges[0] = right;
    }
    public void setDown(boolean down)
    {
        edges[1] = down;
    }
    public void set(boolean right, boolean down){
        edges[0] = right;
        edges[1] = down;
    }
    public boolean getRight()
    {
        return edges[0];
    }
    public boolean getDown()
    {
        return edges[1];
    }
}
