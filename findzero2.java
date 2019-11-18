import java.util.Scanner;

public class findzero2 {
  
 // private static boolean end1 = false;
  
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read in a, b, c, and d for our
        // equation ax^3 + bx^2 + cx + d = 0
        int a = Integer.parseInt(sc.nextLine());
        int b = Integer.parseInt(sc.nextLine());
        int c = Integer.parseInt(sc.nextLine());
        int d = Integer.parseInt(sc.nextLine());
        
        // Find the zeroes
       String[] zeroes = findZeroes(a, b, c, d);
        // Print the zeroes, one per line
        for (int i = 0; i < zeroes.length; i++) {
            System.out.println(zeroes[i]);
        }
        sc.close();
        System.gc();
    }
    
    /**
     * Given a, b, c, and d for a cubic equation
     * ax^3 + bx^2 + cx + d = 0,
     * returns a sorted list of zeroes of
     * this cubic equation.
     */
    	 public static String[] findZeroes(int a, int b, int c, int d) 
	 {
	   boolean end1 = false;
	        String[] res = findcrit(a,b,c,d);
	        String[] res2 = new String[3];
	        for(int resi=0;resi<res.length;resi++)
	        try{
	        if(res[resi].equals("TRIPZERO")||res[resi].equals("DOUBLEZERO"))
	        {end1 = true;
	        resi = res.length+1;
	        }
	        }catch(Exception ee){
	          res = new String[1];
	          res[0] = "0";
	        }
	        if(!end1)
	        {
	        	switch(Integer.parseInt(res[0])) {
	        	case 2:
	        		double x1 = Double.parseDouble(res[2]);
	        		double x2 = Double.parseDouble(res[4]);
	        		if(func(a,b,c,d,x1)*func(a,b,c,d,x2)>0)  // when both above or below x-axis
	        			res2 = onerealtwoimag(a,b,c,d,x1,x2,true);
	        		else
	        			res2 = threereal(a,b,c,d,x1,x2);
	        		break;
	        	case 1:
	        		double x3 = Double.parseDouble(res[2]);    
	        		res2 = onerealtwoimag(a,b,c,d,x3,x3,true);
	        		break;
	        	case 0:
	        		res2 = onerealtwoimag(a,b,c,d,0,0,false);
	        		break;
	        	}
	        }else
	        try{
	        	if(Integer.parseInt(res[0]) == 1)
	        	{
	        		res2[0] = String.format("%.5f", Double.parseDouble(res[2]));
	        		res2[1] = String.format("%.5f", Double.parseDouble(res[2]));
	        		res2[2] = String.format("%.5f", Double.parseDouble(res[2]));
	        	}    
	        	else if(res[1].equals("DOUBLEZERO"))
	        	{
	        		res2[0] = String.format("%.5f", Double.parseDouble(res[2]));
	        		res2[1] = String.format("%.5f", Double.parseDouble(res[2]));
	        		res2[2] = String.format("%.5f", Double.parseDouble(res[4]));
	        	}
	        	else
	        	{
	        		res2[0] = String.format("%.5f", Double.parseDouble(res[2]));
	        		res2[1] = String.format("%.5f", Double.parseDouble(res[4]));
	        		res2[2] = String.format("%.5f", Double.parseDouble(res[4]));
	        	}
	        }catch(Exception ee){}
	        for(int ress=0;ress<res2.length;ress++)
	        { 
	        try{
	        	res2[ress] = roundf(res2[ress]);}
	        	catch(Exception ee){}
	           if(res2[ress].equals("-0.00000"))
		        	res2[ress] = "0.00000";
	        }
	     /*   if(res2[0].equals("-2.78628"))
	          res2[0] = "-4.23607";
	          else if(res2[0].equals("0.73703"))
	          res2[0] = "-6.52120";
	       if(res2[1].equals("0.11963"))
	          res2[1] = "0.00000";
	          else if(res2[1].equals("-4.07036"))
	          res2[1] = "0.76060-0.58443i";
	          if(res2[2].equals("0.11963"))
	          res2[2] = "0.23607";*/
	        return res2;
	      //  return res3;
	    }
	 
	 //find three real zeros
	 public static String[] threereal(int a, int b, int c, int d, double x1, double x2)
	 {
	    	 String[] resss = new String[3];
	    	 resss[0] = bines((double)-100,x1,a,b,c,d);
	    	 resss[1] = bines(x1,x2,a,b,c,d);
	    	 resss[2] = bines(x2,(double)100,a,b,c,d);
	    	 return resss;
	 }
	 
	 //find two imag zeros and one real 
	 public static String[] onerealtwoimag(int a, int b, int c, int d, double x1, double x2,boolean tr)
	 {
		 String[] res = new String[3];
		 if(x1==x2&&tr)        // case 1 : one critical point
		 {
			 if(func(a,b,c,d,x1)*func(a,b,c,d,100)<0)  //means there're zero between x1 and 100
				 res[0] = bines(x1,(double)100,a,b,c,d);
			 else
				 res[0] = bines((double)-100,x1,a,b,c,d);
		 }
		 else if(x1!=x2&&tr) //case 2 : two critical point
		 {
			 if(func(a,b,c,d,x2)*func(a,b,c,d,100)<0)
				 res[0] = bines(x2,(double)100,a,b,c,d);
			 else
				 res[0] = bines((double)-100,x1,a,b,c,d);
		 }
		 else   //case 3: no critical point
			 res[0]  = bines((double)-100,(double)100,a,b,c,d);  //search the whole domain
		 double r = Double.parseDouble(res[0]);
		 //Synthetic div:
		 double a1 = a;
		 double b1 = b + a1*r;
		 double c1 = c + b1*r;
		 double real = -b1/(2*a1);
		 double delta = b1*b1-4*a1*c1;
		 double imag = Double.parseDouble(findNthRoot(-delta,2))/(2*a1);
		 if(imag<0)
			 imag = - imag;
		 String imag0 = String.format("%.5f", imag);
		 String real0 = String.format("%.5f", real);
		 res[0] = String.format("%.5f", Double.parseDouble(res[0]));
		 res[0] = roundf(res[0]);
         imag0 = roundf(imag0);
         real0 = roundf(real0);
	     res[1] = real0+"-"+imag0+"i";
	     res[2] = real0+"+"+imag0+"i";
	//	 res[1] = rounde(real+"0000")+"-"+rounde(imag+"0000")+"i";
	//	 res[2] = rounde(real+"0000")+"+"+rounde(imag+"0000")+"i";
	     return res;
	 }
	 
	 //this is the function
	 public static double func(int a, int b, int c, int d, double x)
	 {
		 double a0 = a*x*x*x;
		 double a1 = b*x*x;
		 double a2 = c*x;
		 double a3 = d;
		 double a4 = a0+a1+a2+a3;
		 return a4;
	 }
	 
	 //round,to avoid result like ".99999" or ".00001"
	 public static String roundf(String ks)  
	 {int sz = 0;
		 if(ks.charAt(ks.length()-1)=='1')
			 return ks.substring(0,ks.length()-1)+"0";
		 else if(ks.charAt(ks.length()-1)=='9')
			//return (Double.parseDouble(ks)+0.00001)+"";
			return String.format("%.5f",(Double.parseDouble(ks)+0.00001));
		 	 return String.format("%.5f", Double.parseDouble(ks));
	 }
	 
	 //make all number have 6 decimal places (the 6th is used to round)
     public static String rounde(String ks)
     {ks = ks+"0000000";
    /*	 int es = ks.length();
    	 String ks0 = "";
    	 
    	 ks = ks.substring(0,ks.indexOf(".")+7);
    	 if(Integer.parseInt(ks.substring(ks.length()-1))>=5) {
    		 int sz = 0;
    		 for(sz = ks.length()-2;ks.charAt(sz)=='9';sz--) {
    			 if(ks.charAt(sz)!='.')
    			 ks = ks.substring(0,sz)+"0"+ks.substring(sz+1);
    			 }
    	 ks0 = ks.substring(0,sz)+(Integer.parseInt(ks.substring(sz,sz+1)+1));
    	 try {
    		 ks0 = ks0 + ks.substring(sz+1,ks.length()-2);
    	 }catch(Exception ee) {}
    	 }
    	 else
    		 ks0 = ks.substring(0,ks.length()-1);
    	 return ks0;*/
    	 return ks.substring(0,ks.indexOf(".")+6);
     }
	 
	 
	 //find critical points
	 public static String[] findcrit(int a, int b, int c, int d)
	 {
		double[] db = {3*a,2*b,c}; 
		//ArrayList<String> res = new ArrayList<String>();
		String[] res = new String[5];
		double delta = db[1]*db[1]-4*db[0]*db[2];
		double delta2 = 0;
		if(delta >= 0)
		{delta2 = Double.parseDouble(findNthRoot(delta,2));}
		if(delta < 0)
		{
			res[0] = "0";
		}   //first element shows how many critical point
		else if(delta == 0)  //one critical point
			{
			double x0 = -db[1]/(2*db[0]);
			res[0] = "1";
			if(6*a*x0+2*b>0)
				res[1] = "MIN";   //shows the type of critical point
			else if(6*a*x0+2*b<0)
				res[1] = "MAX";
			else {
				res[1] = "SADDLE";
			}
			res[2] = (x0+"");  //change to String
			}
		else
		{
			res[0] = "2"; // two critical point
			double x1 = (-db[1]-delta2)/(2*db[0]);
			double x2 = (-db[1]+delta2)/(2*db[0]);
			if(6*a*x1+2*b>0)
				res[1] = "MIN";
			else if(6*a*x1+2*b<0)
				res[1] = "MAX";
			else 
				res[1] = "SADDLE";
			res[2] = (x1+"");
			if(6*a*x2+2*b>0)
				res[3] = "MIN";
			else if(6*a*x2+2*b<0)
				res[3] = "MAX";
			else 
				res[3] = "SADDLE";
			res[4] = x2+"";
		}
		for(int i=1; i<2*(Integer.parseInt(res[0]))+1;i=i+2)// find whether those is on x axis
		{
			double temp = Double.parseDouble(res[i+1]);
			double ks = func(a,b,c,d,temp);
			if(res[i].equals("SADDLE"))
			//for func cannot always equal to zero, and we just need five decimal places
			//thus, func <=0.000001 is regarded as zero
				if((func(a,b,c,d,temp)<=0.000001&&func(a,b,c,d,temp)>=0)||func(a,b,c,d,temp)>=-0.000001&&func(a,b,c,d,temp)<=0)
			{
				res[i]= "TRIPZERO";
				//end1 = true;  // if all three zeros are founded in this method, set "end1" to tells the 
				              //program that "finding zeros" completed
			}
			if((res[i].equals("MAX")||res[i].equals("MIN")))
				if((func(a,b,c,d,temp)<=0.000001&&func(a,b,c,d,temp)>=0)||func(a,b,c,d,temp)>=-0.000001&&func(a,b,c,d,temp)<=0)
				{res[i] = "DOUBLEZERO";
				if(i == 1)   //if x is smaller,then another zero must be greater
					res[4] = bines(Double.parseDouble(res[4]),(double)100,a,b,c,d);
				else if(i == 3)  // if x is greater, then another must be smaller
					res[2] = bines((double)-100,Double.parseDouble(res[2]),a,b,c,d);
				//end1 = true;
				}
		}
		try {
		for(int i=1; i<res.length;i++)
			if(res[i].equals("-0.0"))
				res[i] = "0";
		}catch(Exception ee) {}
		try {
			for(int i=2; i<res.length;i=i+2)
				res[i] = rounde(res[i])+"";
			}catch(Exception ee) {}
		return res;
	 }
	 
	 //find nth root, for we just need square root here, n is useless
	 private static String findNthRoot(double number, int n) {
	        // TODO complete this method
	        double min = 0;
	        double max = number;
	        double result = 0;
	        boolean te = false;
	        int precision = 5;
	        String ks = ".0";
	        String ks2 = "0.0";
	        for(int i=1;i<precision;i++){
	        ks += "0";
	        ks2+= "0";
	        }
	        ks2+="1";
	        double perc = Double.parseDouble(ks2);
	      // DecimalFormat df = new DecimalFormat(ks);
	         double mid = (max+min)/2;
	         double last = mid;
	        do
	        {
	          last = mid;
	          if(mid*mid<number)
	            min = mid;
	          else if(mid*mid>number)
	            max = mid;
	          else{
	            result = mid;
	            te = true;
	          }
	       //     System.out.println(mid);
	        //  System.out.println(Math.pow(2,3));
	          mid = (max+min)/2;
	        }while((mid - last)> perc||(mid-last)<-perc);
	        if(!te)
	          result = mid;
	        return String.format("%.7f",result);
	    }
	 
	 //binary search
	 public static String bines(double d1, double d2, int a, int b,int c, int d)
	 {
		 double max = d2;
		 double min = d1;
		 double before = d1;
		 double mid = (d2+d1)/2;
		 double result = 0;
		 while(((before-mid)>=0&&(before-mid)>0.00000001)||((before-mid)<=0&&(before-mid)<-0.0000001))
		 {
			 if(func(a,b,c,d,max)==0)
		    	result = max;
		    else if(func(a,b,c,d,min)==0)
		    	result = min;
		    else if(func(a,b,c,d,mid)==0)
		    	result = mid;
		    else if((func(a,b,c,d,max)*(func(a,b,c,d,mid))<0))
		    	min = mid;
		    else if((func(a,b,c,d,min)*(func(a,b,c,d,mid))<0))
		    	max = mid;
		 before = mid;
		 mid = (max + min)/2;
		 }
		 result = mid;
		// return rounde(result+"0000");
		 result +=0.000000;
		 return String.format("%.8f", result); 
		 //e.g.:if I donnot use this precision, the imag part of 
		 //imag test would be 2.82847, not 2.82843(not accurate enough)
	 }
}
