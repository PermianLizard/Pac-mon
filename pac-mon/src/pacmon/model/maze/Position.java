package pacmon.model.maze;

public class Position 
{
	public static int distance(Position p1, Position p2)
	{
		return Position.distance(p1.x, p1.y, p2.x, p2.y);
	}
	
	public static int distance(int p1x, int p1y, int p2x, int p2y)
	{
		return (int)Math.sqrt( Math.pow(p2x - p1x, 2) +  Math.pow(p2y - p1y, 2));
	}
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

	public int x;
	public int y;
}
