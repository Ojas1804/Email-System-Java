package application.model;

public class OptimizedSize implements Comparable<OptimizedSize>
{
	private int size;

	
	public OptimizedSize(int size)
	{
		super();
		this.size = size;
	}


	@Override
	public String toString()
	{
		if(size <= 0)
		{
			return "0 B";
		}
		else if(size < 1024)
		{
			return size + " B";
		}
		else if(size < 1048576)
		{
			return size/1024 + " kB";
		}
		else
		{
			return size/1048576 + " MB";
		}
	}


	@Override
	public int compareTo(OptimizedSize o)
	{
		if(size > o.size)
		{
			return 1;
		}
		else if(size < o.size)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
