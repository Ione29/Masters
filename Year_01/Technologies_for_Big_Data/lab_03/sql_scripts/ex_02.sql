select last_name, hire_date, salary
from employees
where (salary, manager_id)
in 
(select salary, manager_id from employees where last_name like 'Kochhar')