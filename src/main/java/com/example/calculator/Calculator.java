package com.example.calculator;
import java.util.Stack;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController		
public class Calculator{
	
	@GetMapping("/Value")
	public int evaluate(@RequestBody String statement)
	{
		Stack <Integer> Numbers=new Stack<>();
		Stack <Character>Operations=new Stack<>();
		for(int i=0;i<statement.length();i++)
		{
			int ChangeToNumber=0;
			char c=statement.charAt(i);
			if(Character.isDigit(c))
			{
			while(Character.isDigit(c))
				{
				ChangeToNumber=ChangeToNumber*10+(c-'0');
				i++;
				if(i<statement.length())
				{
					c=statement.charAt(i);
				}
				else {
					break;
				}
				}
			i--;
			Numbers.push(ChangeToNumber);
			}
			
			else if(c=='(') 
			{
				Operations.push(c);
			}
			
			else if(c==')') 
			{
	            while(Operations.peek()!='('){
					int output=performOperations(Numbers,Operations);
					Numbers.push(output);
					
			}
	            Operations.pop();  
			}
			
			
			else if(isoperator(c))
			{
					while(!Operations.isEmpty()&&Precedence(c)<=Precedence(Operations.peek())) 
					{
							int output=performOperations(Numbers,Operations);
							Numbers.push(output);
							
					}
					Operations.push(c);
			}
			
		}
		while(!Operations.isEmpty())
		{
			int output=performOperations(Numbers,Operations);
			Numbers.push(output);
			
		}
		return Numbers.pop();
	   }
	   
		
	 static int Precedence(char c) {
		 switch(c)
		 {
	 		case '+' :
	 		case '-' :
	 				return 1;
	 		case '*' :
	 		case '/' :
	 			    return 2;
		 }
		 return -1;
	 }

	public boolean isoperator(char c) 
	{
		 return (c=='+'||c=='-'||c=='*'||c=='/');
	}

	public int performOperations(Stack<Integer> Numbers, Stack<Character> Operations) 
	 	{
		 	int a=Numbers.pop();
			int b=Numbers.pop();
			char ch=Operations.pop();
			switch(ch){
				case '+': return a+b;
				case '-': return b-a;
				case '*': return a*b;
				case '/':if(a==0)
					 throw new UnsupportedOperationException("Cannot divide by zero");
					return b / a;
			}
			return 0;
	   }
	}


