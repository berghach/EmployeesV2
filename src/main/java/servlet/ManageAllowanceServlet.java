package servlet;

import entities.Allowance;
import entities.Employee;
import service.AllowanceService;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ManageAllowanceServlet extends HttpServlet {
    private AllowanceService allowanceService;
    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        allowanceService = new AllowanceService();
        employeeService = new EmployeeService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
    private void showAllowanceList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Allowance> allowanceList = allowanceService.getAll();

        req.setAttribute("allowances", allowanceList);
        req.getRequestDispatcher("view/index.jsp").forward(req, resp);
    }
    private void showEmployeeAllowances(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long idEmployeeParam = Long.parseLong(req.getParameter("id"));

            Optional<Employee> optionalEmployee = employeeService.get(idEmployeeParam);

            if (optionalEmployee.isPresent()) {
                List<Allowance> employeeAllowances = optionalEmployee.get().getAllowances();
                req.setAttribute("employeeAllowances", employeeAllowances);
                req.getRequestDispatcher("/employeeAllowances.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Employee not found.");
                req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid employee ID format.");
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        switch (method != null ? method.toUpperCase() : "") {
            case "PUT":
                doPut(req, resp);
                break;
            case "DELETE":
                doDelete(req, resp);
                break;
            case "POST":
                createEmployeeAllowance(req, resp);
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                resp.getWriter().write("Error: Unsupported or invalid method.");
        }
    }
    private void createEmployeeAllowance(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long idEmployeeParam = Long.parseLong(req.getParameter("id"));

            Optional<Employee> optionalEmployee = employeeService.get(idEmployeeParam);

            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();
                Double salary = employee.getSalary();
                int kids = employee.getKids() > 6 ? 6 : employee.getKids();
                double totalAmount = 0.0;

                if (!(salary < 8000 && salary > 6000)) {
                    if (salary <= 6000) {
                        if (kids <= 3) {
                            totalAmount = 300 * kids;
                        } else {
                            totalAmount = (300 * 3) + (150 * (kids - 3));
                        }
                    } else if (salary >= 8000) {
                        if (kids <= 3) {
                            totalAmount = 200 * kids;
                        } else {
                            totalAmount = (200 * 3) + (110 * (kids - 3));
                        }
                    }

                    Allowance allowance = new Allowance(totalAmount, new Date(), employee);
                    allowanceService.save(allowance);

                    resp.sendRedirect("/");
                } else {
                    req.setAttribute("message", "Employee does not qualify for an allowance.");
                    req.getRequestDispatcher("/noAllowance.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("error", "Employee not found.");
                req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid employee ID format.");
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create this employee allowance");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
