package facades;

import entities.*;
import dtos.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    private EmployeeFacade(){
    }


    public static EmployeeFacade employeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static DTOEmployee getEmployeeById(Integer id) throws Exception{

        EntityManager em = emf.createEntityManager();
        Employee employee = em.find(Employee.class, id);
        em.close();
        if (employee != null) {
            return new DTOEmployee(employee);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<DTOEmployee> getEmployeeByName(String name) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery <Employee> typedQuery = em.createQuery("SELECT e FROM Employee e WHERE e.name = :name", Employee.class);
            typedQuery.setParameter("name", name);
            List<Employee> result = typedQuery.getResultList();
            em.getTransaction().commit();
            return (List<DTOEmployee>)(List<?>) result;
        }
        finally {
            em.close();

        }
    }

    @SuppressWarnings("unchecked")
    public static List <DTOEmployee> getAllEmployees() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery <Employee> typedQuery = em.createQuery("SELECT e FROM Employee e", Employee.class);
            List<Employee> result = typedQuery.getResultList();
            em.getTransaction().commit();
            return (List<DTOEmployee>)( List<?>) result;
        }
        finally {
            em.close();
            emf.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<DTOEmployee> getEmployeeWithHighestSalary(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery <Employee> typedQuery = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT max(e.salary) FROM Employee e)", Employee.class);
            List<Employee> result = typedQuery.getResultList();
            em.getTransaction().commit();
            return (List<DTOEmployee>)( List<?>) result;
        }
        finally {
            em.close();
            emf.close();
        }
    }

    public void createEmployee(DTOEmployee dtoEmployee){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(new Employee(dtoEmployee.getName(),dtoEmployee.getAddress(),dtoEmployee.getSalary()));
            em.getTransaction().commit();
        }
        finally {
            em.close();
            emf.close();
        }
    }
}
