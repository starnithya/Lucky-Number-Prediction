package lucky;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class game extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        PrintWriter out = res.getWriter();
        
        Integer b = (Integer) session.getAttribute("targetNumber");
        Integer attempts = (Integer) session.getAttribute("attempts");
        
        if (b == null || attempts == null || attempts >= 4) {
            b = (int) (Math.random() * 10);
            attempts = 0;
            session.setAttribute("targetNumber", b);
            session.setAttribute("attempts", attempts);
            out.println("New game started! Guess a number between 0 and 9.");
            return;
        }
        
        int n = Integer.parseInt(req.getParameter("num"));
        attempts++;
        session.setAttribute("attempts", attempts);
        
        if (n == b) {
            out.println("Congratulations! You found it.");
            session.invalidate(); // Reset game after winning
            return;
        } else if (attempts >= 4) {
            out.println("Game over! The correct number was: " + b);
            session.invalidate(); // Reset game after max attempts
            return;
        } else if ((n < b) && ((n + 2) != b) && ((n + 1) != b)) {
            out.println("Too Low");
        } else if ((n > b) && ((n - 2) != b) && ((n - 1) != b)) {
            out.println("Too High");
        } else if (((n + 2) == b) || ((n + 1) == b)) {
            out.println("You are near & low");
        } else if (((n - 2) == b) || ((n - 1) == b)) {
            out.println("You are near & high");
        }
        
        out.println("Attempts left: " + (4 - attempts));
    }
} 
