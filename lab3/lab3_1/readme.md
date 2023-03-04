# Questions

## a) Identify a couple of examples that use AssertJ expressive methods chaining.

1- `givenSetOfEmployees_whenFindAll_thenReturnAllEmployees()` in `A_EmployeeRepositoryTest.java` when it uses `assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());`.

2- `given3Employees_whengetAll_thenReturn3Records` in `B_EmployeeService_UnitTest` when it uses `assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());`.


## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 

In `B_EmployeeService_UnitTest`, `setUp()` is used to mock a repository's behaviour. This mock is then used in the test functions, like in `whenSearchValidName_thenEmployeeShouldBeFound`.


## c) What is the difference between standard @Mock and @MockBean?

Mock is used for normal classes, MockBean is used for SpringBoot components.


## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

It provides the necessary resources, like a name and password, to start the spring components during a test.


## e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

C uses a simplified environment by simulating an application server, while D and E start the full web context, which is slower.
D uses MockMvc to communicate with the repository while E uses a REST template.