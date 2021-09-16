package facades;

import dtos.DTOEmployee;
import entities.Employee;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import entities.RenameMe;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeFacadeTest {
    private static EntityManagerFactory emf;
    private static EmployeeFacade facade;
    private static Employee e1, e2, e3, e4;

    public EmployeeFacadeTest(){

    }
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = EmployeeFacade.employeeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.createNamedQuery("Employee.deleteAllRows").executeUpdate();
            e1 = new Employee("Kurt","Firekløvervej 2", 7322);
            e2 = new Employee("Miriam","Hovedvejen 4",93229);
            e3 = new Employee("Sandra", "Roskildevej 268", 834034);
            e4 = new Employee("Frank", "Fængslet", 83);
            em.persist(e1);
            em.persist(e2);
            em.persist(e3);
            em.persist(e4);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getEmployeeById() throws Exception {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            System.out.println("e1: " + e1.getId());
            DTOEmployee result = EmployeeFacade.getEmployeeById(e1.getId());
            assertEquals(result.getName(),  e1.getName());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    @Test
    void getEmployeeByName() throws Exception {
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            List<DTOEmployee> result = EmployeeFacade.getEmployeeByName(e2.getName());
            DTOEmployee expected = result.get(0);
            assertEquals(expected.getName(),e2.getName());
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    @Disabled
    @Test
    void getAllEmployees() throws Exception {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            String result = EmployeeFacade.getAllEmployees().toString();
            assertEquals(result,"[1, Kurt, 2, Miriam, 3, Sandra, 4, Frank]");
        }
        finally {
            em.close();
        }
    }

    @Test
    void getEmployeeWithHighestSalary() {
    }

    @Disabled
    @Test
    public void createEmployee() {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Employee actual = new Employee("Robin", "Sommervej 2", 34723);
            em.persist(actual);
            TypedQuery<Employee> typedQuery = em.createQuery("SELECT e FROM Employee e WHERE e.name = 'Robin'",Employee.class);
            Employee result = typedQuery.getSingleResult();
            em.getTransaction().commit();
            System.out.println(result);
            assertEquals(result, actual);
            em.getTransaction().commit();

        }
        finally {
            em.close();
        }

    }
}
