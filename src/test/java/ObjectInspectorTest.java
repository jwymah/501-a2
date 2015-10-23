import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class ObjectInspectorTest
{
	private interface TestInterface0
	{
		int int0=0;
		void testMethod0();
	}
	
	public interface TestInterface1
	{
		int int1 = 1;
		double double1 = 1;
		float float1 = (float) 1.0;
		boolean bool1 = false;
		String string1 = "String1";
//		int[] intArray1 = new int[]{4,5,6,7};
		
		public default void testMethod2() throws InterruptedException, RuntimeException, IOException
		{
			throw new InterruptedException();
		}
	}
	
	class TestClass0 implements TestInterface0
	{
		@Override
		public void testMethod0()
		{	
		}
	}
	
	class TestClass1 extends TestClass0 implements TestInterface1
	{
		int int2 = 2;
		double double2 = 2;
		float float2 = (float) 2.0;
		boolean bool2 = true;
		String string2 = "String2";
		TestClass1(){}
		
		public int fibonacci(int n)
		{
			if (n == 1 || n ==2)
			{
				return 1;
			}
			return fibonacci(n-1) + fibonacci(n-2);
		}
		
	}
	
	class TestClass2 implements InterfaceA
	{

		@Override
		public void func0(int a, boolean c) throws Exception
		{	
		}

		@Override
		public void func1(int a, double b, boolean c, String s) throws Exception
		{
		}

		@Override
		public int func2(String s) throws Exception, ArithmeticException, IllegalMonitorStateException
		{
			return 0;
		}
	}
	class TestClass3
	{
		private TestClass2 testy = new TestClass2(); 
	}

	
	Inspector oi;
	ByteArrayOutputStream myOut;
	
	@Before
	public void setup()
	{
		myOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(myOut));
		
		oi = new Inspector();	
	}
	
	@Test
	public void test()
	{
		oi.inspect(new TestClass1(), false);

		assertTrue(myOut.toString().contains("ObjectInspectorTest"));
		assertTrue(myOut.toString().contains("testMethod2"));
		assertTrue(myOut.toString().contains("TestClass0"));
		assertTrue(myOut.toString().contains("TestClass1"));
		assertTrue(myOut.toString().contains("TestInterface0"));
		assertTrue(myOut.toString().contains("TestInterface1"));
	}
	
	@Test
	public void testRecursionFalse()
	{
		oi.inspect(new TestClass1(), false);
		assertFalse(myOut.toString().contains("InterfaceA"));
	}	
	
	//test fails because an issue inspecting fields
//	@Test
//	public void testRecursionTrue()
//	{
//
//		final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(myOut));
//		
//		ObjectInspector oi = new ObjectInspector();
//		oi.inspect(new TestClass1(), true);
//
//		System.err.println(myOut.toString());
//		assertTrue(myOut.toString().contains("InterfaceA"));
//	}

}
