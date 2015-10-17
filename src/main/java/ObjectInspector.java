
/*==========================================================================
File: ObjectInspector.java
Purpose:Demo Object inspector for the Asst2TestDriver

Location: University of Calgary, Alberta, Canada
Created By: Jordan Kidney
Created on:  Oct 23, 2005
Last Updated: Oct 23, 2005

***********************************************************************
If you are going to reproduce this code in any way for your asignment 
rember to include my name at the top of the file toindicate where you
got the original code from
***********************************************************************


========================================================================*/

import java.util.*;
import java.lang.reflect.*;

public class ObjectInspector
{
	public ObjectInspector()
	{
	}

	// -----------------------------------------------------------
	public void inspect(Object obj, boolean recursive)
	{
		Vector<Field> objectsToInspect = new Vector<Field>();
		Class<?> objClass = obj.getClass();

		System.out.println("inside inspector: " + obj + " (recursive = " + recursive + ")");

		// inspect the current class
		System.out.println("Declaring class:\t" + objClass.getDeclaringClass()); //TODO make this work
		inspectSuperClass(objClass);
		inspectInterfaces(objClass);
		inspectConstructors(objClass);
		inspectFields(obj, objClass, objectsToInspect);
		inspectMethods(objClass);

		if (recursive)
		{
			inspectFieldClasses(obj, objClass, objectsToInspect, recursive);
		}
	}

	private void inspectInterfaces(Class<?> objClass)
	{
		System.out.print("Implemented Interfaces:\t");
		if (objClass.getInterfaces().length == 0)
		{
			System.out.print("None");
		}
		for (Class<?> intface : objClass.getInterfaces())
		{
			System.out.print(intface.getName() + " ");
		}
		System.out.println();
	}

	private void inspectSuperClass(Class<?> objClass)
	{
		System.out.print("Superclass:\t\t");
		if (objClass.getSuperclass() == null)
		{
			System.out.println("None");
		}
		else
		{
			System.out.println(objClass.getSuperclass().getName());
		}
	}

	private void inspectConstructors(Class<?> objClass)
	{
		System.out.println("Constructors:");
		for (Constructor<?> constructor : objClass.getConstructors())
		{
			System.out.println("\tName:\t" + constructor.getName());
			printParameterTypes(constructor);
			printModifiers(constructor);
		}
	}

	private void inspectMethods(Class<?> objClass)
	{
		System.out.println("Methods:");
		for (Method method : objClass.getDeclaredMethods())
		{
			try
			{
				System.out.println("\tName:\t" + method.getName());
				printThrownExceptions(method);
				printParameterTypes(method);
				printModifiers(method);
				System.out.println("\t  Return Type:\t\t" + method.getReturnType());
				System.out.println();
			}
			catch (Exception e)
			{
			}
		}
	}

	private void printModifiers(Constructor<?> constructor)
	{
		System.out.println("\t  Modifiers:\t\t" + Modifier.toString(constructor.getModifiers()));
	}
	private void printModifiers(Method method)
	{
		System.out.println("\t  Modifiers:\t\t" + Modifier.toString(method.getModifiers()));
	}

	private void printParameterTypes(Constructor<?> constructor)
	{
		System.out.print("\t  Parameter Types:\t");
		if (constructor.getParameterTypes().length == 0)
		{
			System.out.print("None");
		}
		for (Class<?> param : constructor.getParameterTypes())
		{
			System.out.print(param.getTypeName() + " ");
		}
		System.out.println();
	}
	private void printParameterTypes(Method method)
	{
		System.out.print("\t  Parameter Types:\t");
		if (method.getParameterTypes().length == 0)
		{
			System.out.print("None");
		}
		for (Class<?> param : method.getParameterTypes())
		{
			System.out.print(param.getTypeName() + " ");
		}
		System.out.println();
	}

	private void printThrownExceptions(Method method)
	{
		System.out.print("\t  Exceptions thrown:\t");
		if (method.getExceptionTypes().length == 0)
		{
			System.out.print("None");
		}
		for (Class<?> e : method.getExceptionTypes())
		{
			System.out.print(e.getName());
		}
		System.out.println();
	}

	// -----------------------------------------------------------
	private void inspectFieldClasses(Object obj, Class<?> ObjClass, Vector<Field> objectsToInspect, boolean recursive)
	{
		if (objectsToInspect.size() > 0)
			System.out.println("---- Inspecting Field Classes ----");

		Enumeration<Field> e = objectsToInspect.elements();
		while (e.hasMoreElements())
		{
			Field f = (Field) e.nextElement();
			System.out.println("Inspecting Field: " + f.getName());

			try
			{
				System.out.println("******************");
				inspect(f.get(obj), recursive);
				System.out.println("******************");
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
	}

	// -----------------------------------------------------------
	private void inspectFields(Object obj, Class<?> objClass, Vector<Field> objectsToInspect)
	{
		System.out.println("Fields:");
		for (Field f : objClass.getDeclaredFields())
		{
			f.setAccessible(true);

			if (!f.getType().isPrimitive())
			{
				objectsToInspect.addElement(f);
			}
			try
			{
				System.out.println("\tName:\t" + f.getName()
				  				 + "\tType:\t" + f.getType()
								 + "\tValue:\t" + f.get(obj)
								 + "\tModifiers:\t" + Modifier.toString(f.getModifiers()));
			}
			catch (Exception e)
			{
			}
		}

		if ((objClass.getSuperclass() != null) && (objClass.getSuperclass().getDeclaredFields().length > 0))
			inspectFields(obj, objClass.getSuperclass(), objectsToInspect);
	}
}
