package prog03;

public class LinearFib implements Fib{
	public double fib(int n)
	{
		double a=0;
		double b=1;
		double c;
		if(n<=1)
			return n;
	for(int i=0;i<n;i++)
	{	
		c=a+b;
		a=b;
		b=c;	
	}
	return b;
	}

	public double O(int n) {
		return n;
	}
}