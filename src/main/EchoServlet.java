package main;

import java.io.*;
import java.text.ParseException;
import javax.servlet.*;
import javax.servlet.http.*;

 public class EchoServlet extends HttpServlet {
 
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {

      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
 
      try {
         out.println("<!DOCTYPE html>");
         out.println("<html><head>");
         out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
         out.println("<title>Rom<->Dec</title></head>");
         
         String input = request.getParameter("input");
         // Get null if the parameter is missing from query string.
         // Get empty string or string of white spaces if user did not fill in
         if (input == null || (input = htmlFilter(input.trim())).length() == 0) {
            out.println("<p>Input Missing</p>");
         } else {
            out.println("<p>Deine Eingabe " + input + "</p>");
            do_Convert(input, response, out);
         }
 
         // Hyperlink "BACK" to input page
         out.println("<a href='index.html'>BACK</a>");
         out.println("</body></html>");
      } finally {
         out.close();  // close the output writer
      }
   }
 
   // Redirect POST request to GET request.
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      doGet(request, response);
   }
 
   // Filter the string for special HTML characters to prevent
   // command injection attack
   private static String htmlFilter(String message) {
      if (message == null) return null;
      int len = message.length();
      StringBuffer result = new StringBuffer(len + 20);
      char aChar;
 
      for (int i = 0; i < len; ++i) {
         aChar = message.charAt(i);
         switch (aChar) {
             case '<': result.append("&lt;"); break;
             case '>': result.append("&gt;"); break;
             case '&': result.append("&amp;"); break;
             case '"': result.append("&quot;"); break;
             default: result.append(aChar);
         }
      }
      return (result.toString());
   }
   
   private void do_Convert(String input, HttpServletResponse response, PrintWriter out)
   {
	   Converter converter = new Converter();
	   int numberToConvert = -1;

       try {
           numberToConvert = Integer.parseInt(input);
       } catch (NumberFormatException nfx) {
           numberToConvert = -1;
       }

       //Convert into Roman characters
       if (numberToConvert != -1) {
    	   out.println("<p>Deine Eingabe " + input + " entspricht in römischen Ziffern " + converter.toRomanNumerals(numberToConvert) + "</p>");
       } else {
           try {
       //Roman characters given -> Convert into Integer 	   
			out.println("<p>Deine Eingabe " + input + " entspricht in arabischen Ziffern " + converter.toNumber(input) + "</p>");
		} catch (ParseException e) {
			out.println("<p>Leider entspricht deine Eingabe " + input + " keinen bekannten Zahlen </p>");
		}
       }
   }
   
}